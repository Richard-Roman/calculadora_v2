package rom.calculadora.domain;

import java.math.BigDecimal;
import rom.calculadora.domain.estado.EstadoCalculadora;
import rom.calculadora.domain.estado.EstadoInicial;
import rom.calculadora.domain.operacion.Operacion;
import rom.calculadora.domain.operacion.OperacionUnaria;

public class Calculadora {

    private EstadoCalculadora estado;
    private double acumulado;
    private Operacion operacion;
    private String displayBuffer;

    public Calculadora() {
        this.estado = new EstadoInicial(this);
        this.acumulado = 0;
        this.displayBuffer = "0";

    }

    public void setEstado(EstadoCalculadora estado) {
        this.estado = estado;
    }

    // delegación al estado
    public void ingresarNumero(double numero) {
        estado.ingresarNumero(this, numero);
        actualizarDisplay(numero);
    }

    public void seleccionarOperacion(Operacion operacion) {
        estado.seleccionarOperacion(this, operacion);
    }

    public double calcular(double numero) {
        double resultado = estado.calcular(this, numero);
        actualizarDisplay(resultado);
        return resultado;
    }

    public double aplicarOperacionUnaria(OperacionUnaria operacion) {
        double resultado = operacion.ejecutar(acumulado);
        acumulado = resultado;
        actualizarDisplay(resultado);
        return resultado;
    }

    public void limpiar() {
        estado.limpiar(this);
        displayBuffer = "0";
    }

    public void setAcumulado(double n) {
        this.acumulado = n;
    }

    public double getAcumulado() {
        return acumulado;
    }

    public void setOperacion(Operacion op) {
        this.operacion = op;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public String getDisplay() {
        return displayBuffer;
    }

    public void setDisplay(String texto) {
        this.displayBuffer = texto;
    }

    private String formatearNumero(double numero) {
        // Limitar a 10 dígitos significativos
        BigDecimal bd = BigDecimal.valueOf(numero);
        
        // Si el número tiene más de 10 dígitos, usar notación con precisión limitada
        if (bd.toPlainString().replaceAll("[^0-9]", "").length() > 10) {
            return String.format("%.8g", numero);
        }
        
        // Si es un número entero o tiene decimales, mostrar normalmente
        return bd.stripTrailingZeros().toPlainString();
    }

    void actualizarDisplay(double numero) {
        displayBuffer = formatearNumero(numero);
    }
}
