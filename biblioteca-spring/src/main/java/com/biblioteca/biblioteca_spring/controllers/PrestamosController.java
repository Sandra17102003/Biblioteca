
package com.biblioteca.biblioteca_spring.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.biblioteca.biblioteca_spring.entities.Prestamos;
import com.biblioteca.biblioteca_spring.services.LibrosService;
import com.biblioteca.biblioteca_spring.services.PrestamosService;
import com.biblioteca.biblioteca_spring.services.UsuariosService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/prestamos")
public class PrestamosController {
    @Autowired
    private PrestamosService prestamosService;

    @Autowired
    private LibrosService librosService;

    @Autowired
    private UsuariosService usuariosService;

    //Método para listar todos los préstamos
    @GetMapping
    public String listarPrestamos(Model model) {
        List<Prestamos> prestamos = prestamosService.obtenerTodosPrestamos();
        model.addAttribute("prestamos", prestamos);
        return "prestamos";
    }

    //Método para mostar formulario para nuevo préstamo
    @GetMapping("/nuevo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamos());
        model.addAttribute("libros", librosService.obtenerTodosLibros());
        model.addAttribute("usuarios", usuariosService.obtenerTodosUsuarios());
        return "nuevoprestamo";
    }
    

    //Método para guardar préstamo
    @PostMapping("/guardar")
    public String guardarPrestamo(@Valid @ModelAttribute Prestamos prestamos, BindingResult result, Model model){
        // Si hay errores de validación, se recargan los libros y usuarios antes de volver al formulario
        if (result.hasErrors()) {
            model.addAttribute("libros", librosService.obtenerTodosLibros());
            model.addAttribute("usuarios", usuariosService.obtenerTodosUsuarios());
            return "nuevoprestamo";
        }

        // Se pone la la fecha de devolución a Null al crear un préstamo
        prestamos.setFechaDevolucion(null);

        try {
            prestamosService.guardar(prestamos);
        } catch (IllegalStateException e) {
            // Si el libro no está disponible, se recargan los datos y mostramos el error
            model.addAttribute("error", e.getMessage());
            model.addAttribute("libros", librosService.obtenerTodosLibros());
            model.addAttribute("usuarios", usuariosService.obtenerTodosUsuarios());
            return "nuevoprestamo";
        }
        return "redirect:/prestamos";
    }

    //Método para eliminar préstamo
    @GetMapping("/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable Integer id) {
        prestamosService.eliminar(id);
        return "redirect:/prestamos";
    }

    //Método para devolver un libro
    @GetMapping("/devolver/{id}")
    public String devolverLibro(@PathVariable Integer id) {
        try{
            prestamosService.devolverLibro(id);
        }
        catch(IllegalArgumentException e){
            return "redirect:/prestamos";
        }
        return "redirect:/prestamos";
    }  
}
