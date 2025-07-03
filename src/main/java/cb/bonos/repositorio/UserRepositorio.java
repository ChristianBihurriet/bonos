package cb.bonos.repositorio;

import cb.bonos.modelo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositorio extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
