package cl.volvo.estudio.service;

import cl.volvo.estudio.dto.EstudioRequestDTO;
import cl.volvo.estudio.dto.EstudioResponseDTO;
import cl.volvo.estudio.model.Estudio;
import cl.volvo.estudio.repository.EstudioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudioServiceTest {

    @Mock
    private EstudioRepository estudioRepository;

    @InjectMocks
    private EstudioService estudioService;

    private Estudio estudioMock;
    private EstudioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Datos de prueba iniciales para usar en los tests
        estudioMock = new Estudio();
        estudioMock.setId(1L);
        estudioMock.setNombre("Naughty Dog");
        estudioMock.setPaisOrigen("Estados Unidos");
        estudioMock.setAnoFundacion(1984);

        requestDTO = new EstudioRequestDTO();
        requestDTO.setNombre("Naughty Dog");
        requestDTO.setPaisOrigen("Estados Unidos");
        requestDTO.setAnoFundacion(1984);
    }

    @Test
    void registrarEstudio_Exitoso() {
        // Given (Dado)
        when(estudioRepository.existsByNombre(requestDTO.getNombre())).thenReturn(false);
        when(estudioRepository.save(any(Estudio.class))).thenReturn(estudioMock);

        // When (Cuando)
        EstudioResponseDTO response = estudioService.registrarEstudio(requestDTO);

        // Then (Entonces)
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Naughty Dog", response.getNombre());
        verify(estudioRepository, times(1)).save(any(Estudio.class));
    }

    @Test
    void registrarEstudio_LanzaExcepcion_SiNombreYaExiste() {
        // Given (Dado)
        when(estudioRepository.existsByNombre(requestDTO.getNombre())).thenReturn(true);

        // When & Then (Cuando y Entonces)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            estudioService.registrarEstudio(requestDTO);
        });
        
        assertEquals("El nombre del estudio ya está en uso", exception.getMessage());
        verify(estudioRepository, never()).save(any(Estudio.class));
    }

    @Test
    void obtenerPorId_Exitoso() {
        // Given (Dado)
        when(estudioRepository.findById(1L)).thenReturn(Optional.of(estudioMock));

        // When (Cuando)
        EstudioResponseDTO response = estudioService.obtenerPorId(1L);

        // Then (Entonces)
        assertNotNull(response);
        assertEquals("Naughty Dog", response.getNombre());
    }

    @Test
    void eliminarEstudio_Exitoso() {
        // Given (Dado)
        when(estudioRepository.existsById(1L)).thenReturn(true);

        // When (Cuando)
        estudioService.eliminarEstudio(1L);

        // Then (Entonces)
        verify(estudioRepository, times(1)).deleteById(1L);
    }
}