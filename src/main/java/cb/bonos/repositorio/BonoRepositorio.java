package cb.bonos.repositorio;

import cb.bonos.modelo.Bono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonoRepositorio extends JpaRepository<Bono,Integer> {
}
