package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        @Valid
        DatosRegistroUsuario autor) {
}