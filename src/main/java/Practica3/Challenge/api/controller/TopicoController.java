package Practica3.Challenge.api.controller;

import Practica3.Challenge.api.domain.respuestas.Respuesta;
import Practica3.Challenge.api.domain.respuestas.RespuestaRepository;
import Practica3.Challenge.api.domain.topicos.*;
import Practica3.Challenge.api.domain.usuarios.*;
import Practica3.Challenge.api.infra.security.DatosJWTToken;
import Practica3.Challenge.api.infra.security.DatosMensaje;
import Practica3.Challenge.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
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
    private List<Respuesta> respuestaList;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    // Registrar datos
    @PostMapping
    @Operation(summary = "Crear Topico y lo guarda en la BD")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        String res = "";
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosRegistroTopico.autor().correo(),
                datosRegistroTopico.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        var nuevocode = ResponseEntity.ok(new DatosJWTToken(JWTtoken));

        //String res= validartopico(datosRegistroTopico);

        usuarioList=usuarioRepository.findByCorreoLike(datosRegistroTopico.autor().correo());
        if (usuarioList.size()==1){
            topicoList=topicoRepository.findByTituloLike(datosRegistroTopico.titulo());
            if (topicoList.size() >= 1){
                res="El Topico que intenta ingresar ya existe, porfavor ingrese otro.";
            } else {
                Topico topicoActual=new Topico(datosRegistroTopico);
                topicoActual.setAutor(usuarioList.get(0));
                topicoRepository.save(topicoActual);
                res="Topico Creado";
            }
        } else if (usuarioList.size()==0){
            res="El usuario o la contraseña son incorrecta, por favor ingrese los datos correctamente para registrar un topico";
        }

        return ResponseEntity.ok(new DatosMensaje(res));
    }
    /*
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
    */

    // Listar datos
    @GetMapping
    @Operation(summary = "Lista los topicos que estan guardado en la BD")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByEstadoTrue(paginacion).map(DatosListadoTopico::new));
    }

    // Actualiza datos
    @PutMapping
    @Operation(summary = "Edita los datos que estan guardado en el Topico")
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
    /*
    @Transactional
    public String actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        //topico.actualizarDatos(datosActualizarTopico);
    }
    */

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Topico que esta guardado en laa BD")
    public ResponseEntity autenticarUsuarioParaEliminar(@RequestBody @Valid DatosEliminarTopico datosEliminarTopico,@PathVariable Long id) {
        String res;
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosEliminarTopico.autor().correo(),
                datosEliminarTopico.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        Topico topico = topicoRepository.getReferenceById(id);

        if (topico.getEstado()){
            if(Objects.equals(topico.getAutor().getCorreo(), datosEliminarTopico.autor().correo())){
                respuestaList=respuestaRepository.findByTopico_id(topico.getId());

                if (respuestaList.size() >= 1){
                    for (int x=0; respuestaList.size() > x; x++){
                        Respuesta respuesta = respuestaRepository.getReferenceById(respuestaList.get(x).getId());
                        respuesta.setEstado(false);
                        respuestaRepository.save(respuesta);
                    }
                }

                topico.setEstado(false);
                topicoRepository.save(topico);
                res = "Topico eliminado";
            } else {
                res ="Usuario denegado, este usuario no tiene acceso a este topico.";
            }
        } else {
            res = "El Topico que quiere eliminar ya no existe.";
        }

        return ResponseEntity.ok(new DatosMensaje(res));
    }
    /*
    @Transactional
    public void eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
    }
    */
}