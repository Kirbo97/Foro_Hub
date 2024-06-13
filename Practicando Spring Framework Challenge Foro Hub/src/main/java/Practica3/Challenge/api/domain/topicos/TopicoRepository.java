package Practica3.Challenge.api.domain.topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.stream.DoubleStream;
import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByTituloLike(String titulo);
    Page<Topico> findByEstadoTrue(Pageable paginacion);
    //Page<Topico> findByActivoTrue(Pageable paginacion);
    //List<Topico> findByCorreo(String nomuser);
    //Page<Topico> findByActivoTrue(Pageable paginacion);
}
