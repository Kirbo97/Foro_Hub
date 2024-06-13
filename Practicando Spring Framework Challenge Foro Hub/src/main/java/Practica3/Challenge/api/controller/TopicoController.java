package Practica3.Challenge.api.controller;

import Practica3.Challenge.api.domain.topicos.*;
import Practica3.Challenge.api.domain.usuarios.*;
import Practica3.Challenge.api.infra.security.DatosJWTToken;
import Practica3.Challenge.api.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// importo las herramientas para usarlos


@RestController // Esta anotacion le indica que trate el contenido como un archivo de tipo control
@RequestMapping("/topicos") // Esta anotacion le indica que el conenido sea proyectado o este ubicado en una direccion
public class TopicoController {
    //private List<Topico> topicos;
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

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosRegistroTopico.autor().correo(),
                datosRegistroTopico.autor().contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        var nuevocode = ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        validartopico(datosRegistroTopico);
        return nuevocode;
    }

    public void validartopico(@Valid DatosRegistroTopico datosRegistroTopico){
        usuarioList=usuarioRepository.findByCorreoLike(datosRegistroTopico.autor().correo());
        if (usuarioList.size()==1){
            Topico topicoActual=new Topico(datosRegistroTopico);
            topicoActual.setAutor(usuarioList.get(0));
            topicoRepository.save(topicoActual);
            System.out.println("Topico Creado");
        } else if (usuarioList.size()==0){
            System.out.println("El usuario o la contraseña son incorrecta, por favor ingrese los datos correctamente para registrar un topico");
        }
    }

    // Listar datos
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByEstadoTrue(paginacion).map(DatosListadoTopico::new));
    }

    // Actualiza datos
    @PutMapping
    @Transactional
    public void actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
    }
}