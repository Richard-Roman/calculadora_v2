package rom.calculadora.domain.operacion;

public class Potencia implements Operacion {

    @Override
    public double ejecutar(double a, double b) {
        return Math.pow(a, b);
    }
}
