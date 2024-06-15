package Practica3.Challenge.api.domain.respuestas;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import Practica3.Challenge.api.domain.topicos.DatosRegistroTopico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        String mensaje,
        @NotNull
        LocalDateTime fechacreacion,
        @Valid
        DatosRegistroTopico topico,
        @Valid
        DatosRegistroUsuario autor
){
}
