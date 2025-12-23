package rom.calculadora.application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import rom.calculadora.domain.operacion.Operacion;
import rom.calculadora.domain.operacion.Suma;
import rom.calculadora.domain.operacion.Resta;
import rom.calculadora.domain.operacion.Multiplicacion;
import rom.calculadora.domain.operacion.Division;
import rom.calculadora.domain.operacion.Potencia;
import rom.calculadora.domain.operacion.Modulo;

public class CreadorOperaciones {

    private final Map<String, Supplier<Operacion>> operaciones = new HashMap<>();

    public CreadorOperaciones() {
        operaciones.put("SUMA", Suma::new);
        operaciones.put("RESTA", Resta::new);
        operaciones.put("MULTIPLICACION", Multiplicacion::new);
        operaciones.put("DIVISION", Division::new);
        operaciones.put("POTENCIA", Potencia::new);
        operaciones.put("MODULO", Modulo::new);
    }

    public Operacion crear(String tipo) {
        Supplier<Operacion> supplier = operaciones.get(tipo.toUpperCase());
        if (supplier == null) {
            throw new IllegalArgumentException("Operación no soportada: " + tipo);
        }
        return supplier.get();
    }
}
