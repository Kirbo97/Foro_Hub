package Practica3.Challenge.api.domain.topicos;

import Practica3.Challenge.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "Topicos")
@Entity(name = "Topico")
@Getter
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

    private String respuesta;

    public Topico(@Valid DatosRegistroTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechacreacion = datosRegistroTopico.fechacreacion();
        this.estado =  true;
        this.autor = new Usuario(datosRegistroTopico.autor());
        this.respuesta = datosRegistroTopico.respuesta();
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) { this.titulo = datosActualizarTopico.titulo(); }
        if (datosActualizarTopico.mensaje() != null) { this.mensaje = datosActualizarTopico.mensaje(); }
        if (datosActualizarTopico.respuesta() != null) { this.respuesta = datosActualizarTopico.respuesta(); }
    }

    public void desactivarTopico() {
        this.estado = false;
    }

    // Metodo Getter
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Long getId() { return id; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechacreacion() { return fechacreacion; }
}
