package Practica3.Challenge.api.controller;

import Practica3.Challenge.api.domain.respuestas.*;
import Practica3.Challenge.api.domain.topicos.*;
import Practica3.Challenge.api.domain.usuarios.*;
import Practica3.Challenge.api.infra.security.DatosJWTToken;
import Practica3.Challenge.api.infra.security.DatosMensaje;
import Practica3.Challenge.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/respuestas") // Esta anotacion le indica que el conenido sea proyectado o este ubicado en una direccion
public class RespuestaController {
    private List<Topico> topicoList;
    private List<Usuario> usuarioList;
    private List<Respuesta> respuestaList;
    String frase = "", res="";

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
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosRegistroRespuesta datosRespuesta) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosRespuesta.autor().correo(),
                datosRespuesta.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        var nuevocode = ResponseEntity.ok(new DatosJWTToken(JWTtoken));

        res = validarrespuesta(datosRespuesta);
        return ResponseEntity.ok(new DatosMensaje(res));
    }

    public String validarrespuesta(@Valid DatosRegistroRespuesta datosRespuesta){
        usuarioList=usuarioRepository.findByCorreoLike(datosRespuesta.autor().correo());
        if(usuarioList.size()==1){
            topicoList=topicoRepository.findByTituloLike(datosRespuesta.topico().titulo());
            Usuario usuarioViejo= usuarioList.get(0);
            if(topicoList.size()==1){
                respuestaList=respuestaRepository.findByMensajeLike(datosRespuesta.mensaje());

                if (respuestaList.size() >= 1){
                    frase="La respuesta que intenta ingresar ya existe, porfavor ingrese otro.";
                } else {
                    Respuesta respuestaActual= new Respuesta(datosRespuesta);
                    Topico topicoViejo= topicoList.get(0);
                    respuestaActual.setAutor(usuarioList.get(0));
                    topicoViejo.setNumresp(String.valueOf((Integer.parseInt(topicoList.get(0).getNumresp())) + 1));
                    topicoRepository.save(topicoViejo);
                    respuestaActual.setTopico(topicoList.get(0));
                    respuestaRepository.save(respuestaActual);
                    frase="Respuesta Creado";
                }

            }else if (topicoList.size()==0){
                frase="Eliga un topico para responder";
            }
        } else if (usuarioList.size()==0){
            frase="El usuario o la contraseña son incorrecta, por favor ingrese los datos correctamente para registrar un topico";
        }
        return frase;
    }

    // Listar datos
    @GetMapping
    @Operation(summary = "Lista los topicos que estan guardado en la BD")
    public ResponseEntity<Page<DatosListadoRespuesta>> listadoRespuesta(Pageable paginacion) {
        //return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosListadoRespuesta::new));
        return ResponseEntity.ok(respuestaRepository.findByEstadoTrue(paginacion).map(DatosListadoRespuesta::new));
    }

    // Actualiza datos
    @PutMapping
    @Operation(summary = "Edita los datos que estan guardado en el Topico")
    public ResponseEntity autenticarUsuarioParaActualizar(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        String res;
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosActualizarRespuesta.autor().correo(),
                datosActualizarRespuesta.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        if(Objects.equals(respuesta.getAutor().getCorreo(), datosActualizarRespuesta.autor().correo())){
            respuesta.setMensaje(datosActualizarRespuesta.mensaje());
            respuestaRepository.save(respuesta);
            res = "Datos actualizado";
        } else {
            res ="Usuario denegado, este usuario no tiene acceso a esta respuesta.";
        }
        return ResponseEntity.ok(new DatosMensaje(res));
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una Respuesta que esta guardado en laa BD")
    public ResponseEntity autenticarUsuarioParaEliminar(@RequestBody @Valid DatosEliminarRespuesta datosEliminarRespuesta,@PathVariable Long id) {
        String res;
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosEliminarRespuesta.autor().correo(),
                datosEliminarRespuesta.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        if (respuesta.getEstado()){
            if(Objects.equals(respuesta.getAutor().getCorreo(), datosEliminarRespuesta.autor().correo())){

                respuesta.setEstado(false);
                respuestaRepository.save(respuesta);

                Topico topico = topicoRepository.getReferenceById(respuesta.getTopico().getId());
                int n= Integer.parseInt(topico.getNumresp()) - 1;
                topico.setNumresp(String.valueOf(n));
                topicoRepository.save(topico);

                res = "Respuesta eliminado";
            } else {
                res ="Usuario denegado, este usuario no tiene acceso a este topico.";
            }
        } else {
            res = "La Respuesta que quiere eliminar ya no existe.";
        }
        return ResponseEntity.ok(new DatosMensaje(res));
    }

}