# Arquitectura - Decisiones Clave

## Visión General: Capas Limpias

```
┌─────────────────────────────────────────────┐
│ web/                                        │
│ CalculadoraPageController (HTML)            │
│ CalculadoraApiController (JSON REST)        │
└─────────────┬───────────────────────────────┘
              │ orquesta
┌─────────────▼───────────────────────────────┐
│ application/                                │
│ CalculadoraSession (@SessionScope)          │
│ EditorDeEntrada (Pure Fabrication)          │
│ CreadorOperaciones (Factories)              │
└─────────────┬───────────────────────────────┘
              │ delega
┌─────────────▼───────────────────────────────┐
│ domain/                                     │
│ Calculadora (máquina de estados)            │
│ EstadoCalculadora (patrón State)            │
│ Operacion / OperacionUnaria (patrón Strategy)│
└─────────────────────────────────────────────┘
```

**Decisión clave:** `domain/` es **puro** (sin Spring). `application/` orquesta. `web/` presenta.

---

## ¿Por qué 4 estados?

Cada estado encapsula: **"¿Qué input es válido ahora?"**

| Estado | Acumulado | Operación | Acepta |
|--------|-----------|-----------|--------|
| Inicial | 0 | null | dígito (ignora operador) |
| Ingresando | 3.0 | null | operador o = |
| OpSeleccionada | 3.0 | Suma | número (cambio op permitido) |
| Resultado | 7.0 | anterior | número nuevo u operador |

**Problema que resuelve:**
- Presionar "+" inicial → Ignorado automáticamente (no-op válido en EstadoInicial)
- Presionar "=" sin operación → Retorna acumulado (no-op válido en EstadoIngresando)
- Post-resultado "3" → Transiciona automáticamente para acepta nuevo operador

**Sin estados:** Necesitarías validar en cada método → código disperso, frágil a cambios.

---

## ¿Por qué Strategy para operaciones?

Sin conocer si es Suma o División, Calculadora ejecuta `operacion.ejecutar(a, b)`.

**Ventaja:** Agregar √ = 1 clase nueva
```java
public class RaizCuadrada implements OperacionUnaria {
    @Override
    public double ejecutar(double n) {
        if (n < 0) throw new ArithmeticException("Raíz de negativo");
        return Math.sqrt(n);
    }
}
```

**Sin Strategy:** Calculadora necesitaría:
```java
if (operacion == "RAIZ") {
    if (n < 0) throw...
    return Math.sqrt(n);
}
```

Esto escala exponencialmente. Con Strategy, agregar operación es agregar 1 clase, registrar en factory (1 línea), listo.

---

## ¿Por qué EditorDeEntrada?

Antes: `CalculadoraSession` manejaba tanto **edición de entrada** como **orquestación de casos de uso**.

```java
// ANTES: Cohesión baja
class CalculadoraSession {
    private StringBuilder numeroActual;
    private boolean iniciarNuevoNumero;
    
    void ingresarNumero(String numero) {
        if (iniciarNuevoNumero) {
            numeroActual = new StringBuilder();
            iniciarNuevoNumero = false;
        }
        numeroActual.append(numero);
    }
    void borrarUltimo() {
        numeroActual.deleteCharAt(...);
    }
    // ... + métodos de orquestación
}

// DESPUÉS: Cohesión alta
class EditorDeEntrada {
    void agregarDigito(String d) { ... }
    void borrarUltimo() { ... }
    Double obtenerNumero() { ... }
}

class CalculadoraSession {
    private EditorDeEntrada editor;
    
    void ingresarNumero(String numero) {
        editor.agregarDigito(numero);
    }
    void borrarUltimo() {
        editor.borrarUltimo();
    }
}
```

**Por qué importa:** EditorDeEntrada es **Pure Fabrication (GRASP)**. No representa entidad de dominio; es un helper puro. Separarlo:
- Testeable en aislamiento (sin Calculadora).
- Reutilizable (podrías usarlo en CLI).
- CalculadoraSession enfocada en orquestación.

---

## ¿Dónde se validan errores?

**Cada operación valida su restricción:**
```java
class Division implements Operacion {
    @Override
    public double ejecutar(double a, double b) {
        if (b == 0) throw new ArithmeticException("División por cero");
        return a / b;
    }
}
```

**Controller captura:**
```java
try {
    calculadora.calcular(numero);
} catch (ArithmeticException e) {
    return new CalculadoraResponse(display.getText(), "Error", "error");
}
```

**Por qué:** Si agregas Logaritmo con su propia validación, Controller no cambia. La excepción simplemente burbujea.

---

## Flujo de una Operación: De la UI al Dominio

```
User: "3 + 4 ="

1. CalculadoraPageController.numero(3)
   ↓
2. CalculadoraSession.ingresarNumero("3")
   ├─ editor.agregarDigito("3")
   ├─ calculadora.ingresarNumero(3.0)
   ↓
3. Calculadora (Estado: INGRESANDO)
   └─ acumulado = 3.0
   └─ display = "3"
   
4. CalculadoraPageController.operacion("+")
   ↓
5. CalculadoraSession.seleccionarOperacion("+")
   ├─ calculadora.seleccionarOperacion(Suma)
   ├─ editor.iniciarNuevo()
   ↓
6. Calculadora (Estado: OP_SELECCIONADA)
   └─ acumulado = 3.0
   └─ operacion = Suma
   
[Repite con "4"]

7. CalculadoraPageController.calcular()
   ↓
8. CalculadoraSession.calcular()
   ├─ numero = editor.obtenerNumero()  // 4.0
   ├─ calculadora.calcular(4.0)
   ├─ resultado = Suma.ejecutar(3.0, 4.0)  // 7.0
   ├─ calculadora.setAcumulado(7.0)
   ├─ editor.limpiar()
   ↓
9. Calculadora (Estado: RESULTADO)
   └─ acumulado = 7.0
   └─ display = "7"
```

**Punto clave:** El servidor mantiene el estado. La UI siempre pregunta al servidor qué mostrar.

---

## ¿Por qué BigDecimal?

```java
// double: impreciso
double x = 0.1 + 0.2;  // 0.30000000000000004

// BigDecimal: preciso
BigDecimal x = new BigDecimal("0.1").add(new BigDecimal("0.2"));  // 0.3
```

Operaciones viven en `domain/` y usan `double`. Formateo (strip zeros, límite 10 dígitos) vive en `Calculadora.formatearNumero()`.

**Por qué formateo en dominio:** El display del dominio debe ser consistente. Si application/ o web/ lo formatea, pueden divergir.

---

## ¿Cómo extender sin romper nada?

### Agregar nueva operación binaria

```java
// 1. Nueva clase en domain/operacion/
public class Logaritmo implements Operacion {
    @Override
    public double ejecutar(double a, double b) {
        if (b <= 0 || a <= 0) throw new ArithmeticException("Log indefinido");
        return Math.log(b) / Math.log(a);
    }
}

// 2. Registrar en CreadorOperaciones (1 línea, en application/)
operaciones.put("LOG", Logaritmo::new);

// 3. Agregar botón en index.html (1 botón)
<button onclick="enviarOperacion('LOG')">LOG</button>
```

**Clases que NO cambian:**
- `Calculadora`
- Otros `Operacion`
- `CalculadoraSession`
- Controllers
- Estados

**Esto es Open/Closed Principle:** abierto a extensión, cerrado a modificación.

### Agregar nueva interfaz (CLI, gRPC, etc.)

```
Proyecto actual:
├── domain/       (puro, sin dependencias)
├── application/  (casos de uso, sin Spring)
└── web/          (Spring Controllers)

Futuro:
├── domain/       (sin cambios)
├── application/  (sin cambios)
├── web/
│   ├── web.spring/  (actual)
│   ├── web.cli/     (nueva)
│   └── web.grpc/    (nueva)
```

Reutas `domain/` + `application/` tal cual. Solo escribe nuevos controllers en el paquete `web` correspondiente.

