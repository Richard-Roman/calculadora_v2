package rom.calculadora.domain.operacion;

public class CambioSigno implements OperacionUnaria {

    @Override
    public double ejecutar(double operando) {
        return -operando;
    }
}
