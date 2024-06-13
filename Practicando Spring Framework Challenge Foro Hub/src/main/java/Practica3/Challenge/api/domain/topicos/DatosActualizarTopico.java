package Practica3.Challenge.api.domain.topicos;

import jakarta.validation.constraints.NotNull;


public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String respuesta) {
}