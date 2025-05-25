package com.biblioteca.biblioteca_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_spring.entities.Libros;
import com.biblioteca.biblioteca_spring.services.LibrosService;


@Controller
public class InicioController {

    @Autowired
    private LibrosService librosService;

    // Método para mostrar los libros.
    //Si se introduce un libro en la barra de búsqueda lo busca y devuelve el listado filtrado.
    @GetMapping("/")
    public String mostrarCatalogo(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Libros> libros = (search != null && !search.isEmpty()) 
            ? librosService.buscarLibrosPorTitulo(search) 
            : librosService.obtenerTodosLibros();

        model.addAttribute("libros", libros);
        model.addAttribute("search", search);
        return "inicio"; 
    }
    
}
