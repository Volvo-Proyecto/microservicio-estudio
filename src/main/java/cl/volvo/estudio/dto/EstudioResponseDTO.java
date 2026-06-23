package cl.volvo.estudio.dto;


import lombok.Data;

@Data
public class EstudioResponseDTO {
    private Long id;
    private String nombre;
    private String paisOrigen;
    private Integer anoFundacion;
    private String sitioWeb;
}