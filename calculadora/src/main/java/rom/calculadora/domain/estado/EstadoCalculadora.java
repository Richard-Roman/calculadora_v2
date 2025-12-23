package rom.calculadora.domain.estado;

import rom.calculadora.domain.Calculadora;
import rom.calculadora.domain.operacion.Operacion;

public interface EstadoCalculadora {
    void ingresarNumero(Calculadora calc, double numero);
    void seleccionarOperacion(Calculadora calc, Operacion operacion);
    double calcular(Calculadora calc, double numero);
    void limpiar(Calculadora calc);
}
