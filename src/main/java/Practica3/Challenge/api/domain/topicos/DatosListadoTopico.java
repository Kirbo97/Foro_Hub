package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.usuarios.DatosListadoUsuario;
import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import Practica3.Challenge.api.domain.usuarios.Usuario;
import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import java.time.LocalDateTime;
import java.util.List;

public record DatosListadoTopico(Long id, String titulo, String mensaje, LocalDateTime fechacreacion, String numresp, String autor) {

    public DatosListadoTopico(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensaje(), topico.getFechacreacion(), topico.getNumresp(),topico.getAutor().getNombre());
    }
}