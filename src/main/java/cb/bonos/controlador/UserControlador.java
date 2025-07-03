package cb.bonos.controlador;


import cb.bonos.dto.UserDTO;
import cb.bonos.modelo.Role;
import cb.bonos.modelo.User;
import cb.bonos.repositorio.RoleRepositorio;
import cb.bonos.repositorio.UserRepositorio;
import cb.bonos.servicio.IUserServicio;
import cb.bonos.servicio.UserServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UserControlador {

    private final IUserServicio usuarioServicio;
    private final RoleRepositorio roleRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserControlador(UserServicio usuarioServicio, RoleRepositorio roleRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioServicio = usuarioServicio;
        this.roleRepositorio = roleRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarUsuarios(ModelMap modelo) {
        List<User> users = usuarioServicio.listarUsers();
        modelo.addAttribute("usuarios", users);
        modelo.addAttribute("usuarioForma", new UserDTO());
        return "usuarios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable (value = "id") Long id) {
        User user = usuarioServicio.getUserById(id).orElseThrow();
        usuarioServicio.eliminarUsuario(user);
        return "redirect:/usuarios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregar")
    public String agregarUsuario(@Valid @ModelAttribute("usuarioForma") UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        for(String role : userDTO.getRoles()){
            Role rol = roleRepositorio.findByName(role).orElseThrow();
            roles.add(rol);
        }
        user.setRoles(roles);
        usuarioServicio.guardarUsuario(user);
        return "redirect:/usuarios";
    }
}
