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
@RequestMapping("/api/v0/estudios") // Esta será la ruta principal: localhost:8087/api/v0/estudios
public class EstudioController {

    @Autowired
    private EstudioService estudioService;

    // Endpoint para registrar un estudio: http://localhost:8087/api/v0/estudios/crear
    @PostMapping("/crear")
    public ResponseEntity<EstudioResponseDTO> registrarEstudio(@Valid @RequestBody EstudioRequestDTO requestDTO) {
        log.info("Petición REST recibida para registrar estudio: {}", requestDTO.getNombre());
        
        EstudioResponseDTO nuevoEstudio = estudioService.registrarEstudio(requestDTO);
        
        // Retornamos código 201 (CREATED)
        return new ResponseEntity<>(nuevoEstudio, HttpStatus.CREATED); 
    }

    // Endpoint para obtener todos los estudios: http://localhost:8087/api/v0/estudios/listar
    @GetMapping("/listar")
    public ResponseEntity<List<EstudioResponseDTO>> obtenerTodos() {
        log.info("Petición REST recibida para listar estudios");
        
        List<EstudioResponseDTO> estudios = estudioService.obtenerTodos();
        return ResponseEntity.ok(estudios); // Retornamos código 200 (OK)
    }

    // Endpoint para obtener un estudio específico por su ID: http://localhost:8087/api/v0/estudios/buscar/1
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EstudioResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Petición REST recibida para buscar estudio ID: {}", id);
        
        // Retornamos código 200 (OK)
        EstudioResponseDTO estudio = estudioService.obtenerPorId(id);
        return ResponseEntity.ok(estudio); 
    }

    // Endpoint para actualizar un estudio: http://localhost:8087/api/v0/estudios/actualizar/1
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EstudioResponseDTO> actualizarEstudio(
            @PathVariable Long id, 
            @Valid @RequestBody EstudioRequestDTO requestDTO) {
        
        log.info("Petición REST recibida para actualizar el estudio ID: {}", id);
        EstudioResponseDTO estudioActualizado = estudioService.actualizarEstudio(id, requestDTO);
        
        // Retornamos el estudio actualizado con código 200 (OK)
        return ResponseEntity.ok(estudioActualizado);
    }

    // Endpoint para eliminar un estudio: http://localhost:8087/api/v0/estudios/eliminar/1
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        log.info("Petición REST recibida para eliminar el estudio ID: {}", id);
        estudioService.eliminarEstudio(id);
        
        // Retornamos código 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}