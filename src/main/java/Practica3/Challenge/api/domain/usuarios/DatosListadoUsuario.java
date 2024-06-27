package Practica3.Challenge.api.domain.usuarios;

import Practica3.Challenge.api.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosListadoUsuario(Long id, String nombre, String correo, String contraseña) {
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getId(),usuario.getNombre(), usuario.getCorreo(), usuario.getCorreo());
    }
}