package cl.volvo.estudio.controller;

import cl.volvo.estudio.dto.EstudioRequestDTO;
import cl.volvo.estudio.dto.EstudioResponseDTO;
import cl.volvo.estudio.service.EstudioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstudioController.class)
public class EstudioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstudioService estudioService;

    private EstudioRequestDTO requestDTO;
    private EstudioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new EstudioRequestDTO();
        requestDTO.setNombre("Naughty Dog");
        requestDTO.setPaisOrigen("Estados Unidos");
        requestDTO.setAnoFundacion(1984);

        responseDTO = new EstudioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Naughty Dog");
        responseDTO.setPaisOrigen("Estados Unidos");
        responseDTO.setAnoFundacion(1984);
    }

    @Test
    void registrarEstudio_DebeRetornar201_CuandoDatosSonValidos() throws Exception {
        // Given
        when(estudioService.registrarEstudio(any(EstudioRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/estudios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Naughty Dog"));
    }

    @Test
    void obtenerTodos_DebeRetornar200YLista_CuandoHayDatos() throws Exception {
        // Given
        when(estudioService.obtenerTodos()).thenReturn(List.of(responseDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/estudios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Naughty Dog"));
    }

    @Test
    void obtenerPorId_DebeRetornar200_CuandoEstudioExiste() throws Exception {
        // Given
        when(estudioService.obtenerPorId(1L)).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/estudios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Naughty Dog"));
    }

    @Test
    void registrarEstudio_DebeRetornar400_CuandoFaltanDatos() throws Exception {
        // Given (Un DTO inválido sin nombre)
        EstudioRequestDTO requestInvalido = new EstudioRequestDTO();
        requestInvalido.setAnoFundacion(1984);

        // When & Then
        mockMvc.perform(post("/api/v1/estudios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }
}