package cb.bonos.servicio;

import cb.bonos.modelo.Bono;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBonoServicio {
    public Bono getBonoById(int id);
    public List<Bono> listarBonos();
    public List<Bono> listarBonoPorStatus(Bono.Estatus status);
    public void guardarBono(Bono bono);
    public void eliminarBono(Bono bono);
    Page<Bono> listarBonosPaginados(Pageable pageable);
}
