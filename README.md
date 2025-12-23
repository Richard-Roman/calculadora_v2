# Calculadora Web v2.0

Calculadora con **arquitectura limpia** y **capas bien definidas**. Con Spring Boot Web. Multi-usuario con sesiones independientes.

**Dominio puro y reutilizable:** podrías empaquetar `domain/` + `application/` para usar en CLI, gRPC, o móvil.

## Características

- Operaciones: +, −, ×, ÷, %, ^, √, x², 1/x, ±
- **Web:** Spring Boot + Thymeleaf (servidor; no necesita frontend framework)
- **Precisión:** BigDecimal + límite 10 dígitos (notación científica si excede)
- **Multi-usuario:** Sesiones @SessionScope independientes
- **UX:** Resaltado visual de operación, sincronización servidor en Backspace
- **Errores:** 1/0, √(-4), log(negativo) capturados y mostrados

## Inicio Rápido

```bash
cd calculadora
mvn clean package
java -jar target/calculadora-1.0-SNAPSHOT.jar
# Navegar a http://localhost:8080
```

## Documentación Esencial

| Documento | Contiene |
|-----------|----------|
| **[ARQUITECTURA.md](./ARQUITECTURA.md)** | Decisiones clave: ¿por qué 4 estados?, Strategy, capas, extender |


**Diagramas** (archivos `.puml`; abre en [PlantUML Editor](http://www.plantuml.com/plantuml/uml/)):
- `DIAGRAMA_ESTADOS.puml` - Máquina de 4 estados
- `DIAGRAMA_EXTENSIBLE.puml` - Extender operaciones
- `DIAGRAMA_CLASES.puml` y `DIAGRAMA_RESPONSABILIDADES.puml` - Conceptos

## Cómo Extender

**Nueva operación:** Ver [ARQUITECTURA.md](./ARQUITECTURA.md) sección "¿Cómo extender sin romper nada?"

**Nueva interfaz (CLI, gRPC, etc.):** Reusar `domain/` + `application/` sin cambios. Solo crear nuevo paquete `web.cli/` o `web.grpc/`.

## Estructura del Código

```
src/main/java/rom/calculadora/
├── CalculadoraApplication.java    (entry point)
├── domain/                        (lógica pura)
│   ├── Calculadora.java
│   ├── estado/
│   └── operacion/
├── application/                   (casos de uso)
│   ├── CalculadoraSession.java
│   ├── EditorDeEntrada.java
│   └── CreadorOperaciones*.java
└── web/                           (presentación Spring)
    ├── CalculadoraPageController.java
    └── CalculadoraApiController.java
```
