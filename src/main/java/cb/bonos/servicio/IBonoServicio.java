package cb.bonos.servicio;

import cb.bonos.modelo.Bono;

import java.util.List;

public interface IBonoServicio {
    public Bono getBonoById(int id);
    public List<Bono> listarBonos();
    public void guardarBono(Bono bono);
    public void eliminarBono(Bono bono);
}
