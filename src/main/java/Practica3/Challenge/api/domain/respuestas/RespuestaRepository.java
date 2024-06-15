package Practica3.Challenge.api.domain.respuestas;

import Practica3.Challenge.api.domain.respuestas.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    //Page<Topico> findByActivoTrue(Pageable paginacion);
}
