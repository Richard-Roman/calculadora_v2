package rom.calculadora.domain.operacion;

public class Multiplicacion implements Operacion {

    @Override
    public double ejecutar(double a, double b) {
        return a * b;
    }
}
