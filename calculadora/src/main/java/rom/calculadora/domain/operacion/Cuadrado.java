package rom.calculadora.domain.operacion;

public class Cuadrado implements OperacionUnaria {

    @Override
    public double ejecutar(double operando) {
        return operando * operando;
    }
}
