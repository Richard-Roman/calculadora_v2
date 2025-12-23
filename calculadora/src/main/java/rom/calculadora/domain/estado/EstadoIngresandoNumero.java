package rom.calculadora.domain.estado;

import rom.calculadora.domain.Calculadora;
import rom.calculadora.domain.operacion.Operacion;

public class EstadoIngresandoNumero implements EstadoCalculadora {

    public EstadoIngresandoNumero(Calculadora calc) {
        // Constructor para compatibilidad
    }

    @Override
    public void ingresarNumero(Calculadora calc, double numero) {
        calc.setAcumulado(numero);
    }

    @Override
    public void seleccionarOperacion(Calculadora calc, Operacion operacion) {
        calc.setOperacion(operacion);
        calc.setEstado(new EstadoOperacionSeleccionada(calc));
    }

    @Override
    public double calcular(Calculadora calc, double numero) {
        return calc.getAcumulado();
    }

    @Override
    public void limpiar(Calculadora calc) {
        calc.setAcumulado(0);
        calc.setEstado(new EstadoInicial(calc));
    }
}
