package rom.calculadora.domain.operacion;

public class Resta implements Operacion {

    @Override
    public double ejecutar(double a, double b) {
        return a - b;
    }
}
