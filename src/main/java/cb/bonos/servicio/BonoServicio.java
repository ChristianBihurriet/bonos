package cb.bonos.servicio;
import cb.bonos.modelo.Bono;
import cb.bonos.repositorio.BonoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BonoServicio implements IBonoServicio{

    @Autowired
    BonoRepositorio bonoRepositorio;

    @Override
    public Bono getBonoById(int id) {
        return bonoRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<Bono> listarBonos() {
        return bonoRepositorio.findAll();
    }

    @Override
    public void guardarBono(Bono bono) {
        bonoRepositorio.save(bono);
    }

    @Override
    public void eliminarBono(Bono bono) {
        bonoRepositorio.delete(bono);
    }

    @Override
    public Page<Bono> listarBonosPaginados(Pageable pageable) {
        return bonoRepositorio.findAll(pageable);
    }

    @Override
    public List<Bono> listarBonoPorStatus(Bono.Estatus status) {
        return bonoRepositorio.findByEstatus(status);
    }
}
