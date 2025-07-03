package cb.bonos.servicio;

import cb.bonos.modelo.User;
import cb.bonos.repositorio.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicio implements IUserServicio{

    private final UserRepositorio userRepositorio;

    @Autowired
    public UserServicio(UserRepositorio userRepositorio) {
        this.userRepositorio = userRepositorio;
    }

    @Override
    public List<User> listarUsers() {
        return userRepositorio.findAll();
    }

    @Override
    public void guardarUsuario(User user) {
        userRepositorio.save(user);
    }

    @Override
    public void eliminarUsuario(User user) {
        User userBD = userRepositorio.findById(user.getId()).orElseThrow();
        userRepositorio.delete(userBD);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepositorio.findById(id);
    }
}
