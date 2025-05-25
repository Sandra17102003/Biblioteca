package com.biblioteca.biblioteca_spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_spring.entities.Editoriales;
import com.biblioteca.biblioteca_spring.entities.Libros;
import com.biblioteca.biblioteca_spring.services.CategoriasService;
import com.biblioteca.biblioteca_spring.services.EditorialesService;
import com.biblioteca.biblioteca_spring.services.LibrosService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/libros")
public class LibrosController {

    @Autowired
    private LibrosService librosService;

    @Autowired
    private EditorialesService editorialesService;
    
    @Autowired
    private CategoriasService categoriasService;

    //Listar libros
    @GetMapping("")
    public String listarLibros(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Libros> libros = (search != null && !search.isEmpty()) 
            ? librosService.buscarLibrosPorTitulo(search) 
            : librosService.obtenerTodosLibros();

        model.addAttribute("libros", libros);
        model.addAttribute("search", search);
        return "libros";
    }
    
    // Guardar un nuevo libro
    @PostMapping("/guardar")
    public String guardarLibro(@Valid @ModelAttribute Libros libro, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevoLibro";
        }
        try {
            Editoriales editorial = editorialesService.obtenerPorNombre(libro.getEditorial().getNombre());
            if (editorial != null) {
                libro.setEditorial(editorial);
            } else {
                model.addAttribute("error", "La editorial no existe.");
                return "nuevoLibro";
            }

            librosService.guardar(libro);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "nuevoLibro";
        }
        return "redirect:/libros";
    }

    //Eliminar libro
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Integer id) {
        librosService.eliminar(id);
        return "redirect:/libros";
    }

    /// Mostrar formulario para crear un nuevo libro
    @GetMapping("/nuevo")
    public String mostrarFormularioLibro(Model model) {
        model.addAttribute("libro", new Libros());
        model.addAttribute("editoriales", editorialesService.obtenerTodasLasEditoriales());  
        model.addAttribute("categorias", categoriasService.obtenerTodasLasCategorias());
        
        return "nuevoLibro";
    }

    // Método para mostrar los libros.
    //Si se introduce un libro en la barra de búsqueda lo busca y devuelve el listado filtrado.
    @GetMapping("/inicio")
    public String mostrarCatalogo(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Libros> libros;
        if (search != null && !search.isEmpty()) {
            libros = librosService.buscarLibrosPorTitulo(search); 
        } else {
            libros = librosService.obtenerTodosLibros();
        }
        model.addAttribute("libros", libros);
        model.addAttribute("search", search);
        return "inicio";
    }

    
    // Método para actualizar un libro.
    @PutMapping("/actualizar/{id}")
    public String actualizarLibro(@PathVariable Integer id, @Valid @ModelAttribute Libros libro, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editarLibro";
        }
        try {
            //Se busca el libro en la BD por su ID
            Optional<Libros> libroExistenteOpt = librosService.obtenerLibroPorId(id);
            
            //Se comprueba si el libro existe en la BD
            if (libroExistenteOpt.isPresent()) {
                Libros libroExistente = libroExistenteOpt.get();
                
                // Se actualizan los datos del libro con los valores del formulario
                libroExistente.setTitulo(libro.getTitulo());
                libroExistente.setAutor(libro.getAutor());
                libroExistente.setIsbn(libro.getIsbn());
                
                //Se obtiene la editorial y se asigna al libro
                Editoriales editorial = editorialesService.obtenerPorNombre(libro.getEditorial().getNombre());
                if (editorial != null) {
                    libroExistente.setEditorial(editorial);
                }
                
                //Se actualiza la categoría del libro
                if (libro.getCategoria() != null) {
                    libroExistente.setCategoria(libro.getCategoria());
                }

                //Se actualiza el estado del libro
                libroExistente.setEstado(libro.getEstado());
                
                //Se guarda los cambios en la BD
                librosService.guardar(libroExistente);
            } else {
                model.addAttribute("error", "El libro con ID " + id + " no existe.");
                return "editarlibro";
            }
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "editarlibro";
        }
        return "redirect:/libros";
    }

    //Método para mostrar el formulario de edición de un libro
    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable Integer id, Model model) {
        // Se busca el libro por ID
        Optional<Libros> libro = librosService.obtenerLibroPorId(id);
        //Se comprueba si el libro existe
        if (libro.isPresent()) {
            model.addAttribute("libro", libro.get());
            model.addAttribute("editoriales", editorialesService.obtenerTodasLasEditoriales());
            model.addAttribute("categorias", categoriasService.obtenerTodasLasCategorias());
            return "editarlibro";
        } else {
            model.addAttribute("error", "Libro no encontrado");
            return "error";
        }
    }
}


