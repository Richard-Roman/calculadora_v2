package rom.calculadora.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rom.calculadora.application.CalculadoraSession;

@Controller
@RequestMapping("/api")
public class CalculadoraApiController {

    private final CalculadoraSession session;

    public CalculadoraApiController(CalculadoraSession session) {
        this.session = session;
    }

    @PostMapping("/numero")
    @ResponseBody
    public CalculadoraResponse ingresarNumeroAPI(@RequestParam String numero) {
        try {
            session.ingresarNumero(numero);
            return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
        } catch (Exception e) {
            return new CalculadoraResponse("Error", session.getOperacionSeleccionada(), "error");
        }
    }

    @PostMapping("/operacion")
    @ResponseBody
    public CalculadoraResponse seleccionarOperacionAPI(@RequestParam String operacion) {
        try {
            session.seleccionarOperacion(operacion);
            return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
        } catch (Exception e) {
            return new CalculadoraResponse("Error", session.getOperacionSeleccionada(), "error");
        }
    }

    @PostMapping("/calcular")
    @ResponseBody
    public CalculadoraResponse calcularAPI() {
        try {
            session.calcular();
            return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
        } catch (Exception e) {
            return new CalculadoraResponse("Error", session.getOperacionSeleccionada(), "error");
        }
    }

    @PostMapping("/limpiar")
    @ResponseBody
    public CalculadoraResponse limpiarAPI() {
        try {
            session.limpiar();
            return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
        } catch (Exception e) {
            return new CalculadoraResponse("Error", session.getOperacionSeleccionada(), "error");
        }
    }

    @PostMapping("/eliminar")
    @ResponseBody
    public CalculadoraResponse eliminarAPI() {
        try {
            session.borrarUltimo();
            return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
        } catch (Exception e) {
            return new CalculadoraResponse("Error", session.getOperacionSeleccionada(), "error");
        }
    }

    @PostMapping("/unaria")
    @ResponseBody
    public CalculadoraResponse operacionUnariaAPI(@RequestParam String operacion) {
        try {
            session.aplicarOperacionUnaria(operacion);
            return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
        } catch (Exception e) {
            return new CalculadoraResponse("Error", session.getOperacionSeleccionada(), "error");
        }
    }

    @GetMapping("/display")
    @ResponseBody
    public CalculadoraResponse getDisplay() {
        return new CalculadoraResponse(session.getDisplay(), session.getOperacionSeleccionada(), "success");
    }

    public static class CalculadoraResponse {
        public String display;
        public String operacionSeleccionada;
        public String status;

        public CalculadoraResponse(String display, String operacionSeleccionada, String status) {
            this.display = display;
            this.operacionSeleccionada = operacionSeleccionada;
            this.status = status;
        }

        public String getDisplay() {
            return display;
        }

        public String getOperacionSeleccionada() {
            return operacionSeleccionada;
        }

        public String getStatus() {
            return status;
        }
    }
}
