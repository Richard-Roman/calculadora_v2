package rom.calculadora.application;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import rom.calculadora.domain.Calculadora;
import rom.calculadora.domain.operacion.Operacion;
import rom.calculadora.domain.operacion.OperacionUnaria;

@Component
@SessionScope
public class CalculadoraSession {

    private final Calculadora calculadora;
    private final CreadorOperaciones creadorOp;
    private final CreadorOperacionesUnarias creadorOpUnarias;
    private EditorDeEntrada editor;
    private double ultimoNumero;
    private String operacionSeleccionada;

    public CalculadoraSession() {
        this.calculadora = new Calculadora();
        this.creadorOp = new CreadorOperaciones();
        this.creadorOpUnarias = new CreadorOperacionesUnarias();
        this.editor = new EditorDeEntrada();
        this.ultimoNumero = 0;
        this.operacionSeleccionada = "";
    }

    public void ingresarNumero(String numero) {
        if (".".equals(numero)) {
            editor.agregarPunto();
        } else if (numero != null && numero.matches("[0-9]")) {
            editor.agregarDigito(numero);
        }
    }

    public void seleccionarOperacion(String operacion) {
        Double valor = editor.obtenerNumeroOrNull();
        if (valor != null) {
            ultimoNumero = valor;
            calculadora.ingresarNumero(ultimoNumero);
        }

        try {
            Operacion op = creadorOp.crear(operacion);
            calculadora.seleccionarOperacion(op);
            this.operacionSeleccionada = operacion;
        } catch (IllegalArgumentException e) {
            // Operación no soportada, ignorar
        }

        editor.limpiar();
    }

    public void calcular() {
        Double valor = editor.obtenerNumeroOrNull();
        if (valor != null) {
            ultimoNumero = valor;
            calculadora.calcular(ultimoNumero);
            editor.limpiar();
            editor.iniciarNuevo();
            this.operacionSeleccionada = "";
        }
    }

    public void limpiar() {
        calculadora.limpiar();
        editor.limpiar();
        ultimoNumero = 0;
        this.operacionSeleccionada = "";
    }

    public String getOperacionSeleccionada() {
        return operacionSeleccionada;
    }

    public void aplicarOperacionUnaria(String operacion) {
        try {
            Double valor = editor.obtenerNumeroOrNull();
            if (valor != null) {
                calculadora.setAcumulado(valor);
                ultimoNumero = valor;
            }

            OperacionUnaria op = creadorOpUnarias.crear(operacion);
            calculadora.aplicarOperacionUnaria(op);
            editor.limpiar();
            editor.iniciarNuevo();
        } catch (IllegalArgumentException | ArithmeticException e) {
            // Error en operación unaria
        }
    }

    public void borrarUltimo() {
        editor.borrarUltimo();
    }

    public String getDisplay() {
        if (editor.tieneValor()) {
            return editor.texto();
        }
        return calculadora.getDisplay();
    }
}
