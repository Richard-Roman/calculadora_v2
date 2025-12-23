package rom.calculadora.domain.operacion;

public class Division implements Operacion {

    @Override
    public double ejecutar(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }
        return a / b;
    }
}
