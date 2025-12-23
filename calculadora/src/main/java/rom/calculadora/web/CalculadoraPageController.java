package rom.calculadora.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rom.calculadora.application.CalculadoraSession;

@Controller
@RequestMapping("/")
public class CalculadoraPageController {

    private final CalculadoraSession session;

    public CalculadoraPageController(CalculadoraSession session) {
        this.session = session;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("display", session.getDisplay());
        model.addAttribute("operacionSeleccionada", session.getOperacionSeleccionada());
        return "index";
    }

    @PostMapping("/numero")
    public String ingresarNumero(@RequestParam String numero, Model model) {
        session.ingresarNumero(numero);
        model.addAttribute("display", session.getDisplay());
        model.addAttribute("operacionSeleccionada", session.getOperacionSeleccionada());
        return "index";
    }

    @PostMapping("/operacion")
    public String seleccionarOperacion(@RequestParam String operacion, Model model) {
        session.seleccionarOperacion(operacion);
        model.addAttribute("display", session.getDisplay());
        model.addAttribute("operacionSeleccionada", session.getOperacionSeleccionada());
        return "index";
    }

    @PostMapping("/calcular")
    public String calcular(Model model) {
        session.calcular();
        model.addAttribute("display", session.getDisplay());
        model.addAttribute("operacionSeleccionada", session.getOperacionSeleccionada());
        return "index";
    }

    @PostMapping("/limpiar")
    public String limpiar(Model model) {
        session.limpiar();
        model.addAttribute("display", session.getDisplay());
        model.addAttribute("operacionSeleccionada", session.getOperacionSeleccionada());
        return "index";
    }

    @PostMapping("/unaria")
    public String operacionUnaria(@RequestParam String operacion, Model model) {
        session.aplicarOperacionUnaria(operacion);
        model.addAttribute("display", session.getDisplay());
        model.addAttribute("operacionSeleccionada", session.getOperacionSeleccionada());
        return "index";
    }
}
