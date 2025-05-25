package com.biblioteca.biblioteca_spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_spring.entities.Usuarios;
import com.biblioteca.biblioteca_spring.repositories.UsuariosRepository;

@Service
public class UsuariosService {
    
    @Autowired
    private UsuariosRepository usuariosRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuariosService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Método que busca por nombre de usuario.
    public Optional <Usuarios> findByNombUsuario(String nombUsuario){
        return usuariosRepository.findByNombUsuario(nombUsuario);
    }

    //Método que almacena la  contraseña cifrada al guardar al usuario.
    public Usuarios save(Usuarios usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuariosRepository.save(usuario);
    }

    //Método para obtener todos los usuarios
    public List<Usuarios> obtenerTodosUsuarios(){
        return usuariosRepository.findAll();
    }
    

}
