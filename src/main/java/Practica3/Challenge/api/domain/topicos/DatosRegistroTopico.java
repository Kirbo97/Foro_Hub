package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.respuestas.DatosRespuesta;
import Practica3.Challenge.api.domain.usuarios.DatosUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DatosRegistroTopico(

        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        LocalDateTime fechaCreacion,
        @NotBlank
        String estado,
        @NotNull
        @Valid
        DatosRespuesta respuesta,
        @NotNull
        @Valid DatosUsuario usuario) {
}
