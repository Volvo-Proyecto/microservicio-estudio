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

// IMPORTACIONES DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@Slf4j
@RestController
@RequestMapping("/api/v1/estudios") 
@Tag(name = "Controlador de Estudios", description = "Endpoints para la gestión y administración de los estudios de desarrollo de videojuegos")
public class EstudioController {

    @Autowired
    private EstudioService estudioService;

    // POST http://localhost:8084/api/v1/estudios
    @Operation(summary = "Registrar un nuevo estudio", description = "Crea un nuevo estudio de desarrollo en el sistema tras validar sus datos de entrada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Estudio creado exitosamente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"nombre\": \"Naughty Dog\",\n  \"paisOrigen\": \"Estados Unidos\",\n  \"anoFundacion\": 1984\n}"))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o petición incorrecta", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EstudioResponseDTO> registrarEstudio(@Valid @RequestBody EstudioRequestDTO requestDTO) {
        log.info("Petición REST recibida para registrar estudio: {}", requestDTO.getNombre());
        EstudioResponseDTO nuevoEstudio = estudioService.registrarEstudio(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstudio); 
    }

    // GET http://localhost:8084/api/v1/estudios
    @Operation(summary = "Obtener todos los estudios", description = "Retorna una lista completa de todos los estudios registrados en la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estudios obtenida con éxito",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "[\n  {\n    \"id\": 1,\n    \"nombre\": \"Naughty Dog\",\n    \"paisOrigen\": \"Estados Unidos\"\n  }\n]")))
    })
    @GetMapping
    public ResponseEntity<List<EstudioResponseDTO>> obtenerTodos() {
        log.info("Petición REST recibida para listar estudios");
        List<EstudioResponseDTO> estudios = estudioService.obtenerTodos();
        return ResponseEntity.ok(estudios);
    }

    // GET http://localhost:8084/api/v1/estudios/{id}
    @Operation(summary = "Obtener estudio por ID", description = "Busca y retorna un único estudio de desarrollo utilizando su identificador numérico (ID).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudio encontrado exitosamente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"nombre\": \"Naughty Dog\",\n  \"paisOrigen\": \"Estados Unidos\"\n}"))),
        @ApiResponse(responseCode = "404", description = "El estudio con el ID proporcionado no existe", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudioResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Petición REST recibida para buscar estudio ID: {}", id);
        EstudioResponseDTO estudio = estudioService.obtenerPorId(id);
        return ResponseEntity.ok(estudio);
    }

    // PUT http://localhost:8084/api/v1/estudios/{id}
    @Operation(summary = "Actualizar un estudio existente", description = "Modifica las propiedades de un estudio de desarrollo buscando por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudio actualizado de forma exitosa",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"nombre\": \"Naughty Dog Actualizado\",\n  \"paisOrigen\": \"Estados Unidos\"\n}"))),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque el estudio no existe", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudioResponseDTO> actualizarEstudio(
            @PathVariable Long id, 
            @Valid @RequestBody EstudioRequestDTO requestDTO) {
        log.info("Petición REST recibida para actualizar el estudio ID: {}", id);
        EstudioResponseDTO estudioActualizado = estudioService.actualizarEstudio(id, requestDTO);
        return ResponseEntity.ok(estudioActualizado);
    }

    // DELETE http://localhost:8084/api/v1/estudios/{id}
    @Operation(summary = "Eliminar un estudio", description = "Elimina físicamente un estudio de la base de datos utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estudio eliminado correctamente (Sin contenido de respuesta)", content = @Content),
        @ApiResponse(responseCode = "404", description = "No se pudo eliminar porque el estudio no existe", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudio(@PathVariable Long id) {
        log.info("Petición REST recibida para eliminar el estudio ID: {}", id);
        estudioService.eliminarEstudio(id);
        return ResponseEntity.noContent().build();
    }
}