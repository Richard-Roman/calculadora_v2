package rom.calculadora.domain.estado;

import rom.calculadora.domain.Calculadora;
import rom.calculadora.domain.operacion.Operacion;

public class EstadoInicial implements EstadoCalculadora {

    public EstadoInicial(Calculadora calc) {
        // Constructor para compatibilidad
    }

    @Override
    public void ingresarNumero(Calculadora calc, double numero) {
        calc.setAcumulado(numero);
        calc.setEstado(new EstadoIngresandoNumero(calc));
    }

    @Override
    public void seleccionarOperacion(Calculadora calc, Operacion operacion) {
        // inválido: no hace nada
    }

    @Override
    public double calcular(Calculadora calc, double numero) {
        // inválido: retorna el acumulado actual
        return calc.getAcumulado();
    }

    @Override
    public void limpiar(Calculadora calc) {
        calc.setAcumulado(0);
    }
}
