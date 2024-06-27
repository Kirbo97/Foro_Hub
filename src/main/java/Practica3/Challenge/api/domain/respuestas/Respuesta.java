package Practica3.Challenge.api.domain.respuestas;


import Practica3.Challenge.api.domain.topicos.DatosListadoTopico;
import Practica3.Challenge.api.domain.topicos.Topico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import Practica3.Challenge.api.domain.usuarios.Usuario;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fechacreacion;

    @ManyToOne
    private Usuario autor;

    @ManyToOne
    private Topico topico;

    public Respuesta(@Valid DatosRegistroRespuesta datosRegistroRespuesta) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.fechacreacion= datosRegistroRespuesta.fechacreacion();
        this.autor = new Usuario(datosRegistroRespuesta.autor());
        //this.topico = new Topico(datosRegistroRespuesta.topico());
    }
    @Override//aparece cuando esta sobre-escribiendo un metodo y en este caso es el "toString"
    public String toString() {
        List<String> respuesta = Collections.singletonList(" Respuesta = " + mensaje + " ");
        return respuesta.toString();
    }
}
