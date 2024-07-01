package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.respuestas.DatosListadoRespuesta;
import Practica3.Challenge.api.domain.respuestas.Respuesta;
import Practica3.Challenge.api.domain.respuestas.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public record DatosListadoTopico(Long id, String titulo, String mensaje, LocalDateTime fechacreacion, String numresp, String autor /*, String respuesta*/) {

    @Autowired
    private static RespuestaRepository respuestaRepository;

    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechacreacion(),
                topico.getNumresp(),
                topico.getAutor().getNombre()/*,
                topico.getRespuestas().toString()*/
        );
    }
}