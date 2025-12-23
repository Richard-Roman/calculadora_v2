package rom.calculadora.domain.operacion;

public class Modulo implements Operacion {

    @Override
    public double ejecutar(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede calcular módulo con divisor cero");
        }
        return a % b;
    }
}
