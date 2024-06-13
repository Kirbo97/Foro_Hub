package Practica3.Challenge.api.controller;

import Practica3.Challenge.api.domain.usuarios.DatosRegistroUsuario;
import Practica3.Challenge.api.domain.usuarios.Usuario;
import Practica3.Challenge.api.domain.usuarios.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Esta anotacion le indica que trate el contenido como un archivo de tipo control
@RequestMapping("/cuenta") // Esta anotacion le indica que el conenido sea proyectado o este ubicado en una direccion
public class CuentaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping // Esta anotacion le indica que llame esta funcion a la direccion creada con @RequestMapping
    public void registrarTopico(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {
        usuarioRepository.save(new Usuario(datosRegistroUsuario));
        System.out.println("Usuario registrado");
    }
}