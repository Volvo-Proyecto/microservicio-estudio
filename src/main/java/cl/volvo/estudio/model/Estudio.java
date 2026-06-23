package cl.volvo.estudio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "estudios")
@Data
public class Estudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    @Column(nullable = false, length = 50)
    private String paisOrigen;
    @Column(nullable = false)
    private Integer anoFundacion;
    @Column(length = 255)
    private String sitioWeb;

}