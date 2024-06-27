package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.respuestas.Respuesta;
import Practica3.Challenge.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechacreacion;
    private Boolean estado;

    @ManyToOne
    private Usuario autor;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // esta anotacion convierte esta variable en un atributo con relacion
    private List<Respuesta> respuestas;

    private String numresp;

    public Topico(@Valid DatosRegistroTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechacreacion = datosRegistroTopico.fechacreacion();
        this.estado =  true;
        this.autor = new Usuario(datosRegistroTopico.autor());
        this.numresp = datosRegistroTopico.numresp();
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) { this.titulo = datosActualizarTopico.titulo(); }
        if (datosActualizarTopico.mensaje() != null) { this.mensaje = datosActualizarTopico.mensaje(); }
    }

    public void desactivarTopico() { this.estado = false; }
}
