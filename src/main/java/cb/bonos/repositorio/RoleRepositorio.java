package cb.bonos.repositorio;

import cb.bonos.modelo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositorio  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String username);
}
