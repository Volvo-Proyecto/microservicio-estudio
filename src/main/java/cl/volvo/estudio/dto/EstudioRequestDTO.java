package cl.volvo.estudio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Objeto que representa los datos requeridos para registrar o actualizar un estudio de desarrollo")
public class EstudioRequestDTO {

    @Schema(description = "Nombre oficial del estudio de desarrollo", example = "Volvo Games Studio", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre del estudio no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String nombre;

    @Schema(description = "País donde se encuentra la sede principal", example = "Chile", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El país de origen no puede estar vacío.")
    @Size(max = 50, message = "El país no puede superar los 50 caracteres.")
    private String paisOrigen;

    @Schema(description = "Año en el que fue fundado el estudio (Mínimo año 1950)", example = "2015", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El año de fundación es obligatorio.")
    @Min(value = 1950, message = "El año de fundación debe ser válido (mayor a 1950).")
    private Integer anoFundacion;

    @Schema(description = "URL oficial del sitio web del estudio", example = "https://www.volvogames.cl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 255, message = "La URL del sitio web es demasiado larga.")
    private String sitioWeb;
}