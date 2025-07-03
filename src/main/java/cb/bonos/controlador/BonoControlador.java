package cb.bonos.controlador;

import cb.bonos.dto.BonoDTO;
import cb.bonos.modelo.Bono;
import cb.bonos.servicio.BonoServicio;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BonoControlador {

    private static final Logger logger = LoggerFactory.getLogger(BonoControlador.class);

    @Autowired
    BonoServicio bonoServicio;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/")
    public String iniciar(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          ModelMap modelo) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCompra").descending());
        Page<Bono> paginaBonos = bonoServicio.listarBonosPaginados(pageable);
        modelo.addAttribute("bonos", paginaBonos.getContent());
        modelo.addAttribute("pagina", paginaBonos);
        return "index";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/agregar")
    public String mostrarAgregar(ModelMap modelo) {
        BonoDTO bonoDTO = new BonoDTO();
        bonoDTO.setFechaCompra(LocalDate.now());
        bonoDTO.setFechaVencimiento(LocalDate.now().plusMonths(6));
        modelo.addAttribute("bonoDTO", bonoDTO);
        return "agregar";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/agregar")
    public String agregar(@Valid @ModelAttribute("bonoDTO") BonoDTO bonoDTO, BindingResult result, ModelMap modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("bonoDTO", bonoDTO);
            return "agregar"; // vuelve al formulario con los errores
        }
        Bono bono = new Bono();
        bono.setFechaCompra(bonoDTO.getFechaCompra());
        bono.setFechaVencimiento(bonoDTO.getFechaVencimiento());
        Bono.Estatus statusEnum = Bono.Estatus.valueOf(bonoDTO.getEstatus());
        bono.setEstatus(statusEnum);
        bono.setMonto(bonoDTO.getMonto());
        bono.setComprador(bonoDTO.getComprador());
        bono.setBeneficiario(bonoDTO.getBeneficiario());
        bono.setServicio(bonoDTO.getServicio());
        logger.info("Bono a agregar: {}", bono);
        bonoServicio.guardarBono(bono);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable(value = "id") int idBono, ModelMap modelo) {
        Bono bono = bonoServicio.getBonoById(idBono);
        logger.info("Bono a editar: {}", bono);

        BonoDTO bonoDTO = new BonoDTO(
                bono.getServicio(),
                bono.getFechaCompra(),
                bono.getFechaVencimiento(),
                bono.getComprador(),
                bono.getBeneficiario(),
                bono.getMonto(),
                bono.getEstatus().name()
        );

        modelo.addAttribute("bono", bonoDTO);
        modelo.addAttribute("idBono", bono.getIdBono()); // lo necesitás para guardar después
        return "editar";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/editar")
    public String editar(@RequestParam("idBono") int idBono,
                         @Valid @ModelAttribute("bonoDTO") BonoDTO bonoDTO,
                         BindingResult result,
                         ModelMap modelo) {

        if (result.hasErrors()) {
            modelo.addAttribute("idBono", idBono);
            return "editar";
        }

        Bono bono = bonoServicio.getBonoById(idBono);
        bono.setServicio(bonoDTO.getServicio());
        bono.setFechaCompra(bonoDTO.getFechaCompra());
        bono.setFechaVencimiento(bonoDTO.getFechaVencimiento());
        bono.setComprador(bonoDTO.getComprador());
        bono.setBeneficiario(bonoDTO.getBeneficiario());
        bono.setMonto(bonoDTO.getMonto());
        bono.setEstatus(Bono.Estatus.valueOf(bonoDTO.getEstatus()));

        bonoServicio.guardarBono(bono);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int idBono) {
        Bono bono = bonoServicio.getBonoById(idBono);
        bonoServicio.eliminarBono(bono);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/buscar")
    public String buscarPorId(@RequestParam("id") int id, ModelMap modelo) {
        Bono bono = bonoServicio.getBonoById(id);
        if (bono != null) {
            modelo.addAttribute("bonos", List.of(bono));
        } else {
            modelo.addAttribute("mensajeError", "No se encontró ningún bono con ID " + id);
        }
        return "index";
    }
}
