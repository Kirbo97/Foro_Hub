package Practica3.Challenge.api.domain.respuestas;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosRespuesta (
        @NotBlank
        String mensaje,
        @NotBlank
        LocalDateTime fechaCreacion,
        @NotBlank
        String estado,
        @NotBlank
        String respuesta
){
}
