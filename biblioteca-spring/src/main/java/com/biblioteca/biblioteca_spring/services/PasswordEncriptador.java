package com.biblioteca.biblioteca_spring.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_spring.entities.Usuarios;
import com.biblioteca.biblioteca_spring.repositories.UsuariosRepository;

@Service
public class PasswordEncriptador implements CommandLineRunner{

@Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Se obtienen todos los usuarios
        List<Usuarios> usuarios = usuariosRepository.findAll();

        for (Usuarios usuario : usuarios) {
            // Se verifica si la contraseña ya está cifrada
            if (!usuario.getPassword().startsWith("$2a$")) { // Prefijo estándar de BCrypt
                // Si la contraseña no está cifrada, se encripta y se guarda el usuario con la contraseña cifrada
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuariosRepository.save(usuario);
            }
        }
    }
    

}
