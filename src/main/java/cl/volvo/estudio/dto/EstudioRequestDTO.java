package cl.volvo.estudio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EstudioRequestDTO {

    @NotBlank(message = "El nombre del estudio no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El país de origen no puede estar vacío.")
    @Size(max = 50, message = "El país no puede superar los 50 caracteres.")
    private String paisOrigen;

    @NotNull(message = "El año de fundación es obligatorio.")
    @Min(value = 1950, message = "El año de fundación debe ser válido (mayor a 1950).")
    private Integer anoFundacion;

    @Size(max = 255, message = "La URL del sitio web es demasiado larga.")
    private String sitioWeb;
}