package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechacreacion,
        String numresp,
        DatosRegistroUsuario autor
    ) {
}
