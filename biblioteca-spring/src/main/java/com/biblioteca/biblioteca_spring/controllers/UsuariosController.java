package com.biblioteca.biblioteca_spring.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.biblioteca.biblioteca_spring.entities.Libros;
import com.biblioteca.biblioteca_spring.entities.LoginRequest;
import com.biblioteca.biblioteca_spring.entities.Prestamos;
import com.biblioteca.biblioteca_spring.entities.Usuarios;
import com.biblioteca.biblioteca_spring.services.LibrosService;
import com.biblioteca.biblioteca_spring.services.PrestamosService;
import com.biblioteca.biblioteca_spring.services.UsuariosService;

import jakarta.validation.Valid;


@Controller
public class UsuariosController {

    @Autowired 
    private UsuariosService usuariosService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private PrestamosService prestamosService;

    @Autowired 
    private LibrosService librosService;

    @GetMapping("/login")
    public String loginFormulario(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }
    
    @GetMapping("/usuariolibro")
    public String mostrarLibros(Model model) {
        List<Libros> libros = librosService.obtenerTodosLibros();
        model.addAttribute("libros", libros);
        return "usuariolibro";
    }
    
    //Método para buscar en la base de datos si existe un usuario con ese nombre
    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRequest loginRequest, Model model) {
        Optional<Usuarios> usuarioOpt = usuariosService.findByNombUsuario(loginRequest.getNombUsuario());

        if (usuarioOpt.isPresent()){
            Usuarios usuario = usuarioOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())){
                //Se verifica si el nombre de usuario es "admin" para redirigirlo a préstamos
                if ("Admin".equals(usuario.getNombUsuario())){
                    model.addAttribute("usuario", usuario);
                    return "redirect:/prestamos";
                }
                
                //Si no es admin se redirige a libros
                else{
                    model.addAttribute("usuario", usuario);
                    return "redirect:/usuariolibro";
                }
            }
            model.addAttribute("error", "La contraseña es incorrecta");
            return "login";
        }
        
        model.addAttribute("error", "Nombre de usuario no encontrado");
            return "login";
    }

   
    // Métodopara mostrar formulario para crear un nuevo usuario
    @GetMapping("/nuevoUsuario")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "nuevousuario";
    }

    // Método para guardar nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuarios usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "inicio";
        }

        usuariosService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/misPrestamos")
    public String mostrarMisPrestamos(Model model, Principal principal) {
        // Se obtiene el usuario logueado
        String nombUsuario = principal.getName();
        Optional<Usuarios> usuarioOpt = usuariosService.findByNombUsuario(nombUsuario);

        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            // Se obtienen los prestamos del usuario
            List<Prestamos> prestamos = prestamosService.obtenerPrestamosPorUsuario(usuario.getId());
            model.addAttribute("prestamos", prestamos);
            return "misprestamos";
        }

        model.addAttribute("error", "Usuario no encontrado");
        return "redirect:/login";
    }

    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        // Se obtienen todos los usuarios del servicio
        List<Usuarios> usuarios = usuariosService.obtenerTodosUsuarios(); 
        // Se añaden los usuarios al modelo
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    
}
