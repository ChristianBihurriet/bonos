package cb.bonos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "bonos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Bono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBono;

    @Column(nullable = false, length = 100)
    private String servicio;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 100)
    private String comprador;

    @Column(nullable = false, length = 100)
    private String beneficiario;

    @Column(nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estatus estatus;

    public enum Estatus {
        ACTIVO, VENCIDO, USADO
    }
}
