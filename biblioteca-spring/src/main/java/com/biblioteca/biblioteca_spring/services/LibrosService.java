package com.biblioteca.biblioteca_spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_spring.entities.Estado;
import com.biblioteca.biblioteca_spring.entities.Libros;
import com.biblioteca.biblioteca_spring.repositories.LibrosRepository;

@Service
public class LibrosService {
    @Autowired
    private LibrosRepository librosRepository;

    //Listar libros
    public List<Libros> obtenerTodosLibros(){
        return librosRepository.findAll();
    }

    // Buscar libros por su título
    public List<Libros> buscarLibrosPorTitulo(String titulo) {
        return librosRepository.findByTituloContaining(titulo);
    }

    // Buscar libros por autor
    public List<Libros> buscarLibrosPorAutor(String autor) {
        return librosRepository.findByAutorContaining(autor);
    }

    // Buscar libros por categoría
    public List<Libros> buscarLibrosPorCategoria(String categoria) {
        return librosRepository.findByCategoria_NombreContaining(categoria);
    }

    // Buscar libros por editorial
    public List<Libros> buscarLibrosPorEditorial(String editorial) {
        return librosRepository.findByEditorial_NombreContaining(editorial);
    }

    // Buscar libros por estado
    public List<Libros> buscarLibrosPorEstado(Estado estado) {
        return librosRepository.findByEstado(estado);
    }

    // Buscar libros por editorial y autor
    public List<Libros> buscarLibrosPorEditorialYAutor(String editorial, String autor) {
        return librosRepository.findByEditorial_NombreAndAutor(editorial, autor);
    }

    // Buscar libros por categoría y título
    public List<Libros> buscarLibrosPorCategoriaYTitulo(String categoria, String titulo) {
        return librosRepository.findByCategoria_NombreAndTitulo(categoria, titulo); 
    }

    //Validar que el libro no exista ya con el mismo ISBN
    public boolean validarIsbn(Libros libro) {
        return !librosRepository.findByIsbn(libro.getIsbn()).isPresent();
    }

    // Guardar un nuevo libro
    public Libros guardar(Libros libro) {
        if (libro.getId() == null && !validarIsbn(libro)) {
            throw new IllegalStateException("Ya existe un libro con el ISBN " + libro.getIsbn());
        }
        return librosRepository.save(libro);
    }

    //Eliminar un libro por ID
    public void eliminar(Integer id) {
        if (!librosRepository.existsById(id)) {
            throw new IllegalStateException("El libro no existe.");
        }
        librosRepository.deleteById(id);
    }

    public Optional<Libros> obtenerLibroPorId(Integer id) {
        return librosRepository.findById(id);
    }
}
