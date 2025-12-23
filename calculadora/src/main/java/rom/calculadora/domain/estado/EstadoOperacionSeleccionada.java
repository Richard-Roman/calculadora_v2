package rom.calculadora.domain.estado;

import rom.calculadora.domain.Calculadora;
import rom.calculadora.domain.operacion.Operacion;

public class EstadoOperacionSeleccionada implements EstadoCalculadora {

    public EstadoOperacionSeleccionada(Calculadora calc) {
        // Constructor para compatibilidad
    }

    @Override
    public void ingresarNumero(Calculadora calc, double numero) {
        // No cambiamos de estado todavía, solo guardamos el segundo operando
        // El cambio ocurre cuando se llama a calcular()
    }

    @Override
    public void seleccionarOperacion(Calculadora calc, Operacion operacion) {
        calc.setOperacion(operacion);
    }

    @Override
    public double calcular(Calculadora calc, double numero) {
        double resultado = calc.getOperacion().ejecutar(calc.getAcumulado(), numero);
        calc.setAcumulado(resultado);
        calc.setEstado(new EstadoResultado(calc));
        return resultado;
    }

    @Override
    public void limpiar(Calculadora calc) {
        calc.setAcumulado(0);
        calc.setEstado(new EstadoInicial(calc));
    }
}
