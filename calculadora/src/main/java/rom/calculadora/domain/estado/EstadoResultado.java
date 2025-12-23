package rom.calculadora.domain.estado;

import rom.calculadora.domain.Calculadora;
import rom.calculadora.domain.operacion.Operacion;

public class EstadoResultado implements EstadoCalculadora {

    public EstadoResultado(Calculadora calc) {
        // Constructor para compatibilidad
    }

    @Override
    public void ingresarNumero(Calculadora calc, double numero) {
        calc.setAcumulado(numero);
        calc.setOperacion(null);
        calc.setEstado(new EstadoIngresandoNumero(calc));
    }

    @Override
    public void seleccionarOperacion(Calculadora calc, Operacion operacion) {
        calc.setOperacion(operacion);
        calc.setEstado(new EstadoOperacionSeleccionada(calc));
    }

    @Override
    public double calcular(Calculadora calc, double numero) {
        // Cuando se presiona = en estado resultado, el número pasado es el nuevo acumulado
        calc.setAcumulado(numero);
        calc.setEstado(new EstadoIngresandoNumero(calc));
        return numero;
    }

    @Override
    public void limpiar(Calculadora calc) {
        calc.setAcumulado(0);
        calc.setEstado(new EstadoInicial(calc));
    }
}
