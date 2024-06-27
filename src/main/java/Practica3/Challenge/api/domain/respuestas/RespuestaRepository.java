package Practica3.Challenge.api.domain.respuestas;

import Practica3.Challenge.api.domain.respuestas.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    //Page<Topico> findByActivoTrue(Pageable paginacion);
    List<Respuesta> findByMensajeLike(String mensaje);
}
