package Practica3.Challenge.api.domain.respuestas;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record DatosActualizarRespuesta(
        @NotNull
        Long id,
        String mensaje,
        @Valid
        DatosRegistroUsuario autor) {
}