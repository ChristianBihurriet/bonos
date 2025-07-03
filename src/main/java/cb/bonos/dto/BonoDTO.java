package cb.bonos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonoDTO {

    @NotBlank(message = "El servicio no puede estar vacío")
    private String servicio;

    @NotNull(message = "Debe ingresar una fecha de compra")
    private LocalDate fechaCompra;

    @NotNull(message = "Debe ingresar una fecha de vencimiento")
    private LocalDate fechaVencimiento;

    @NotBlank(message = "El comprador no puede estar vacío")
    private String comprador;

    @NotBlank(message = "El beneficiario no puede estar vacío")
    private String beneficiario;

    @NotNull(message = "Debe ingresar un monto")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private Double monto;

    @Pattern(regexp = "ACTIVO|USADO|VENCIDO", message = "Estatus inválido")
    private String estatus;
}
