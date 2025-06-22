package cb.bonos.controlador;

import cb.bonos.modelo.Bono;
import cb.bonos.servicio.BonoServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/agregar")
    public String mostrarAgregar(ModelMap modelo) {
        Bono bono = new Bono();
        bono.setFechaCompra(LocalDate.now());
        bono.setFechaVencimiento(LocalDate.now().plusMonths(6));
        modelo.addAttribute("bono", bono);
        return "agregar";
    }

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute("bonoForma") Bono bono) {
        logger.info("Bono a agregar: {}", bono);
        bonoServicio.guardarBono(bono);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable(value = "id") int idBono, ModelMap modelo) {
        Bono bono = bonoServicio.getBonoById(idBono);
        logger.info("Bono a editar: {}", bono);
        modelo.addAttribute("bono", bono);
        return "editar";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute("bono") Bono bono) {
        logger.info("Bono a editar: {}", bono);
        bonoServicio.guardarBono(bono);
        return "redirect:/";
    }

    @GetMapping("/cambiar-estatus/{id}")
    public String cambiarEstatus(@PathVariable(value = "id") int idBono, @RequestParam("valor") String nuevoEstatus) {
        Bono bono = bonoServicio.getBonoById(idBono);
        if (bono != null) {
            try{
                Bono.Estatus status = Bono.Estatus.valueOf(nuevoEstatus.toUpperCase());
                bono.setEstatus(status);
                bonoServicio.guardarBono(bono);
            } catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int idBono) {
        Bono bono = bonoServicio.getBonoById(idBono);
        bonoServicio.eliminarBono(bono);
        return "redirect:/";
    }
}
