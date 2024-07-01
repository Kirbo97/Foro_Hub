package Practica3.Challenge.api.domain.respuestas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findByEstadoTrue(Pageable paginacion);
    List<Respuesta> findByMensajeLike(String mensaje);
    List<Respuesta> findByTopico_id(long id);
    List<Respuesta> findById(long id);
    List<Respuesta> findByTopico_idAndEstadoTrue(long id);
}
