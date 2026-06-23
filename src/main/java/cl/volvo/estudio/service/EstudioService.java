package cl.volvo.estudio.service;

import cl.volvo.estudio.dto.EstudioRequestDTO;
import cl.volvo.estudio.dto.EstudioResponseDTO;
import cl.volvo.estudio.model.Estudio;
import cl.volvo.estudio.repository.EstudioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EstudioService {

    @Autowired
    private EstudioRepository estudioRepository;

    // 1. Crear / Registrar un estudio (CREATE)
    public EstudioResponseDTO registrarEstudio(EstudioRequestDTO requestDTO) {
        log.info("Iniciando registro de estudio con nombre: {}", requestDTO.getNombre());

        // Validamos si el nombre ya existe en Laragon usando exists
        if (estudioRepository.existsByNombre(requestDTO.getNombre())) {
            log.warn("El estudio {} ya se encuentra registrado", requestDTO.getNombre());
            throw new RuntimeException("El nombre del estudio ya está en uso");
        }

        // Convertimos el DTO en la Entidad para guardarla en la Base de Datos
        Estudio estudio = new Estudio();
        estudio.setNombre(requestDTO.getNombre());
        estudio.setPaisOrigen(requestDTO.getPaisOrigen());
        estudio.setAnoFundacion(requestDTO.getAnoFundacion());
        estudio.setSitioWeb(requestDTO.getSitioWeb());

        Estudio estudioGuardado = estudioRepository.save(estudio);
        log.info("Estudio guardado con éxito con ID: {}", estudioGuardado.getId());

        // Convertimos la Entidad guardada en el DTO de respuesta
        return mapearADTO(estudioGuardado);
    }

    // 2. Obtener todos los estudios (READ)
    public List<EstudioResponseDTO> obtenerTodos() {
        log.info("Obteniendo listado de todos los estudios");
        return estudioRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    // 3. Obtener un estudio por su ID
    public EstudioResponseDTO obtenerPorId(Long id) {
        log.info("Buscando estudio con ID: {}", id);
        Estudio estudio = estudioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se encontró el estudio con ID: {}", id);
                    return new RuntimeException("Estudio no encontrado con el ID: " + id);
                });
        return mapearADTO(estudio);
    }

    // 4. Actualizar un estudio existente (UPDATE)
    public EstudioResponseDTO actualizarEstudio(Long id, EstudioRequestDTO requestDTO) {
        log.info("Actualizando estudio con ID: {}", id);

        // 4.1. Buscamos si el estudio existe en la BD
        Estudio estudioExistente = estudioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se pudo actualizar. El estudio con ID {} no existe.", id);
                    return new RuntimeException("Estudio no encontrado para actualizar");
                });

        // 4.2. Si existe, le reemplazamos los datos antiguos con los nuevos que vienen en el DTO
        estudioExistente.setNombre(requestDTO.getNombre());
        estudioExistente.setPaisOrigen(requestDTO.getPaisOrigen());
        estudioExistente.setAnoFundacion(requestDTO.getAnoFundacion());
        estudioExistente.setSitioWeb(requestDTO.getSitioWeb());

        // 4.3. Guardamos los cambios
        Estudio estudioGuardado = estudioRepository.save(estudioExistente);
        log.info("Estudio ID: {} actualizado correctamente", id);

        // 4.4. Retornamos el DTO limpio
        return mapearADTO(estudioGuardado);
    }

    // 5. Eliminar un estudio (DELETE)
    public void eliminarEstudio(Long id) {
        log.info("Iniciando eliminación de estudio con ID: {}", id);

        // Verificamos si existe antes de intentar borrarlo
        if (!estudioRepository.existsById(id)) {
            log.error("No se puede eliminar. El estudio con ID {} no existe.", id);
            throw new RuntimeException("Estudio no encontrado para eliminar");
        }

        estudioRepository.deleteById(id);
        log.info("Estudio con ID: {} eliminado de la base de datos", id);
    }

    // Método auxiliar para transformar Entidad -> DTO de salida
    private EstudioResponseDTO mapearADTO(Estudio estudio) {
        EstudioResponseDTO response = new EstudioResponseDTO();
        response.setId(estudio.getId());
        response.setNombre(estudio.getNombre());
        response.setPaisOrigen(estudio.getPaisOrigen());
        response.setAnoFundacion(estudio.getAnoFundacion());
        response.setSitioWeb(estudio.getSitioWeb());
        return response;
    }
}