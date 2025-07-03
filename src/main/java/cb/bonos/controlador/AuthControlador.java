package cb.bonos.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthControlador {

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }
}