package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record DatosEliminarTopico( @Valid DatosRegistroUsuario autor) {
}