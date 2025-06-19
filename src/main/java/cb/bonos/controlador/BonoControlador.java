package cb.bonos.controlador;

import cb.bonos.modelo.Bono;
import cb.bonos.servicio.BonoServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BonoControlador {

    private static final Logger logger = LoggerFactory.getLogger(BonoControlador.class);

    @Autowired
    BonoServicio bonoServicio;

    @GetMapping("/")
    public String iniciar(ModelMap modelo) {
        List<Bono> bonos = bonoServicio.listarBonos();
        bonos.forEach(bono -> logger.info(bono.toString()));
        modelo.put("bonos", bonos);
        return "index";
    }

}
