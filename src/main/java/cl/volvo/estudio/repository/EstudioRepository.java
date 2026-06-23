package cl.volvo.estudio.repository;

import cl.volvo.estudio.model.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudioRepository extends JpaRepository<Estudio, Long> {
    // Método para buscar un estudio por su nombre exacto
    boolean existsByNombre(String nombre);
}