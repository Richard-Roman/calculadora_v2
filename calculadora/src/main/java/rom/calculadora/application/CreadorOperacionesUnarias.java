package rom.calculadora.application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import rom.calculadora.domain.operacion.OperacionUnaria;
import rom.calculadora.domain.operacion.RaizCuadrada;
import rom.calculadora.domain.operacion.Cuadrado;
import rom.calculadora.domain.operacion.Inverso;
import rom.calculadora.domain.operacion.CambioSigno;

public class CreadorOperacionesUnarias {

    private final Map<String, Supplier<OperacionUnaria>> operaciones = new HashMap<>();

    public CreadorOperacionesUnarias() {
        operaciones.put("RAIZ_CUADRADA", RaizCuadrada::new);
        operaciones.put("CUADRADO", Cuadrado::new);
        operaciones.put("INVERSO", Inverso::new);
        operaciones.put("CAMBIO_SIGNO", CambioSigno::new);
    }

    public OperacionUnaria crear(String tipo) {
        Supplier<OperacionUnaria> supplier = operaciones.get(tipo.toUpperCase());
        if (supplier == null) {
            throw new IllegalArgumentException("Operación unaria no soportada: " + tipo);
        }
        return supplier.get();
    }
}
