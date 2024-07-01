package Practica3.Challenge.api.domain.respuestas;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import jakarta.validation.Valid;

public record DatosEliminarRespuesta(@Valid DatosRegistroUsuario autor) {
}