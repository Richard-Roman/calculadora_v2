package rom.calculadora.domain.operacion;

public class RaizCuadrada implements OperacionUnaria {

    @Override
    public double ejecutar(double operando) {
        if (operando < 0) {
            throw new ArithmeticException("No se puede calcular raíz cuadrada de número negativo");
        }
        return Math.sqrt(operando);
    }
}
