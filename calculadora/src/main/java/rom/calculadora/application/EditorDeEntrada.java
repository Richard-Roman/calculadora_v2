package rom.calculadora.application;

class EditorDeEntrada {

    private final StringBuilder buffer = new StringBuilder();
    private boolean nuevoInicio = false;

    void iniciarNuevo() {
        nuevoInicio = true;
    }

    void limpiar() {
        buffer.setLength(0);
        nuevoInicio = false;
    }

    void agregarDigito(String d) {
        if (d == null || !d.matches("[0-9]")) return;
        if (nuevoInicio) {
            buffer.setLength(0);
            nuevoInicio = false;
        }
        buffer.append(d);
    }

    void agregarPunto() {
        if (nuevoInicio) {
            buffer.setLength(0);
            buffer.append("0.");
            nuevoInicio = false;
            return;
        }
        if (buffer.length() == 0) {
            buffer.append("0.");
        } else if (buffer.indexOf(".") == -1) {
            buffer.append('.') ;
        }
    }

    void borrarUltimo() {
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
    }

    boolean tieneValor() {
        return buffer.length() > 0;
    }

    Double obtenerNumeroOrNull() {
        if (!tieneValor()) return null;
        try {
            return Double.parseDouble(buffer.toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    String texto() {
        return buffer.toString();
    }
}
