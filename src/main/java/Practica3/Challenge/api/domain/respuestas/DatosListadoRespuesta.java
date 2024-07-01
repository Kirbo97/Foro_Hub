package Practica3.Challenge.api.domain.respuestas;

import Practica3.Challenge.api.domain.topicos.Topico;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(Long id, String tema, String respuesta,String autor) {

    public DatosListadoRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getTopico().getTitulo(),
                respuesta.getMensaje(),
                respuesta.getAutor().getNombre()
        );
    }
}