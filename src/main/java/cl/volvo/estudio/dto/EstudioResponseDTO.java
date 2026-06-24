package cl.volvo.estudio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Objeto de respuesta con la información pública del estudio de desarrollo")
public class EstudioResponseDTO {

    @Schema(description = "Identificador único del estudio en la base de datos", example = "1")
    private Long id;

    @Schema(description = "Nombre oficial del estudio", example = "Volvo Games Studio")
    private String nombre;

    @Schema(description = "País de origen", example = "Chile")
    private String paisOrigen;

    @Schema(description = "Año de fundación", example = "2015")
    private Integer anoFundacion;

    @Schema(description = "Enlace al sitio web oficial", example = "https://www.volvogames.cl")
    private String sitioWeb;
}