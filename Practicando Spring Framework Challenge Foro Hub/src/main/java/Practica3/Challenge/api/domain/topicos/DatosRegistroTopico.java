package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DatosRegistroTopico(

        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        LocalDateTime fechacreacion,
        @NotNull
        String respuesta,
        @Valid
        DatosRegistroUsuario autor
    ) {
}
