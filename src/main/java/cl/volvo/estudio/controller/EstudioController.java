package cl.volvo.estudio.controller;

import cl.volvo.estudio.dto.EstudioRequestDTO;
import cl.volvo.estudio.dto.EstudioResponseDTO;
import cl.volvo.estudio.service.EstudioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/estudios") 
public class EstudioController {

    @Autowired
    private EstudioService estudioService;

    // POST http://localhost:8084/api/v1/estudios
    @PostMapping
    public ResponseEntity<EstudioResponseDTO> registrarEstudio(@Valid @RequestBody EstudioRequestDTO requestDTO) {
        log.info("Petición REST recibida para registrar estudio: {}", requestDTO.getNombre());
        EstudioResponseDTO nuevoEstudio = estudioService.registrarEstudio(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstudio); 
    }

    // GET http://localhost:8084/api/v1/estudios
    @GetMapping
    public ResponseEntity<List<EstudioResponseDTO>> obtenerTodos() {
        log.info("Petición REST recibida para listar estudios");
        List<EstudioResponseDTO> estudios = estudioService.obtenerTodos();
        return ResponseEntity.ok(estudios);
    }

    // GET http://localhost:8084/api/v1/estudios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EstudioResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Petición REST recibida para buscar estudio ID: {}", id);
        EstudioResponseDTO estudio = estudioService.obtenerPorId(id);
        return ResponseEntity.ok(estudio);
    }

    // PUT http://localhost:8084/api/v1/estudios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EstudioResponseDTO> actualizarEstudio(
            @PathVariable Long id, 
            @Valid @RequestBody EstudioRequestDTO requestDTO) {
        log.info("Petición REST recibida para actualizar el estudio ID: {}", id);
        EstudioResponseDTO estudioActualizado = estudioService.actualizarEstudio(id, requestDTO);
        return ResponseEntity.ok(estudioActualizado);
    }

    // DELETE http://localhost:8084/api/v1/estudios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudio(@PathVariable Long id) {
        log.info("Petición REST recibida para eliminar el estudio ID: {}", id);
        estudioService.eliminarEstudio(id);
        return ResponseEntity.noContent().build();
    }
}