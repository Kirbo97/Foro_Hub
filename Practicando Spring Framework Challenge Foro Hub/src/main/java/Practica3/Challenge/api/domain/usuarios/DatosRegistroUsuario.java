package Practica3.Challenge.api.domain.usuarios;

import jakarta.validation.constraints.Email;

public record DatosRegistroUsuario(
        String nombre,
        @Email
        String correo,
        String contraseña
) {
}