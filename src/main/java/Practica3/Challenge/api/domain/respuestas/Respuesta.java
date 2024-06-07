package Practica3.Challenge.api.domain.respuestas;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import Practica3.Challenge.api.domain.usuarios.Usuario;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private String respuesta;

    public Respuesta(DatosRespuesta respuesta) {
        this.mensaje = respuesta.mensaje();
        this.estado = respuesta.estado();
        this.fechaCreacion=respuesta.fechaCreacion();
        this.respuesta = respuesta.respuesta();

    }
}
