package cb.bonos.servicio;

import cb.bonos.modelo.User;

import java.util.List;
import java.util.Optional;

public interface IUserServicio {
    public List<User> listarUsers();
    public void guardarUsuario(User user   );
    public void eliminarUsuario(User user);
    public Optional<User> getUserById(Long id);
}
