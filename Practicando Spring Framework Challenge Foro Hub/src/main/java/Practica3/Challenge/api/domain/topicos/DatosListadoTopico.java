package Practica3.Challenge.api.domain.topicos;

import java.time.LocalDateTime;

public record DatosListadoTopico(Long id, String titulo, String mensaje, LocalDateTime fechacreacion, String estado) {
    public DatosListadoTopico(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensaje(), topico.getFechacreacion(), topico.getRespuesta());
    }

}