package Practica3.Challenge.api.controller;

import Practica3.Challenge.api.domain.topicos.*;
import Practica3.Challenge.api.domain.usuarios.*;
import Practica3.Challenge.api.infra.security.DatosJWTToken;
import Practica3.Challenge.api.infra.security.DatosMensaje;
import Practica3.Challenge.api.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController // Esta anotacion le indica que trate el contenido como un archivo de tipo control
@RequestMapping("/topicos") // Esta anotacion le indica que el conenido sea proyectado o este ubicado en una direccion
public class TopicoController {
    private List<Usuario> usuarioList;
    private List<Topico> topicoList;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    // Registrar datos
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosRegistroTopico.autor().correo(),
                datosRegistroTopico.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        var nuevocode = ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        String res= validartopico(datosRegistroTopico);

        return ResponseEntity.ok(new DatosMensaje(res));
    }

    public String validartopico(@Valid DatosRegistroTopico datosRegistroTopico){
        String frase = "";
        usuarioList=usuarioRepository.findByCorreoLike(datosRegistroTopico.autor().correo());
        if (usuarioList.size()==1){
            Topico topicoActual=new Topico(datosRegistroTopico);
            topicoActual.setAutor(usuarioList.get(0));
            topicoRepository.save(topicoActual);
            frase="Topico Creado";
        } else if (usuarioList.size()==0){
            frase="El usuario o la contraseña son incorrecta, por favor ingrese los datos correctamente para registrar un topico";
        }
        return frase;
    }

    // Listar datos
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByEstadoTrue(paginacion).map(DatosListadoTopico::new));
    }

    // Actualiza datos
    @PutMapping
    public ResponseEntity autenticarUsuarioParaActualizar(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        String res;
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosActualizarTopico.autor().correo(),
                datosActualizarTopico.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        if(Objects.equals(topico.getAutor().getCorreo(), datosActualizarTopico.autor().correo())){
            topico.setTitulo(datosActualizarTopico.titulo());
            topico.setMensaje(datosActualizarTopico.mensaje());
            topicoRepository.save(topico);
            res = "Datos actualizado";
        } else {
            res ="Usuario denegado, este usuario no tiene acceso a este topico.";
        }
        return ResponseEntity.ok(new DatosMensaje(res));
    }

    /*@Transactional
    public String actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        //topico.actualizarDatos(datosActualizarTopico);
    }
*/

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    public ResponseEntity autenticarUsuarioParaEliminar(@RequestBody @Valid DatosEliminarTopico datosEliminarTopico,@PathVariable Long id) {
        String res;
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosEliminarTopico.autor().correo(),
                datosEliminarTopico.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        Topico topico = topicoRepository.getReferenceById(id);

        if(Objects.equals(topico.getAutor().getCorreo(), datosEliminarTopico.autor().correo())){
            topico.setEstado(false);
            topicoRepository.save(topico);
            res = "Topico eliminado";
        } else {
            res ="Usuario denegado, este usuario no tiene acceso a este topico.";
        }
        return ResponseEntity.ok(new DatosMensaje(res));
    }
    /*
    @Transactional
    public void eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
    }*/
}