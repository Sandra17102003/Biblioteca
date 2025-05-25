package com.biblioteca.biblioteca_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_spring.entities.Estado;
import com.biblioteca.biblioteca_spring.entities.Libros;

@Repository
public interface LibrosRepository extends JpaRepository<Libros, Integer> {
    // Buscar libro por su ISBN
    Optional<Libros> findByIsbn(String isbn);

    //Buscar libros por título
    List<Libros> findByTituloContaining(String titulo);

    //Buscar libros por autor
    List<Libros> findByAutorContaining(String autor);

    //Buscar libros por su categoría
    List<Libros> findByCategoria_NombreContaining(String nombreCategoria);

    //Buscar libros por nombre de la editorial
    List<Libros> findByEditorial_NombreContaining(String nombreEditorial);

    //Buscar libros por estado
    List<Libros> findByEstado(Estado estado);

    // Buscar por editorial y autor
    List<Libros> findByEditorial_NombreAndAutor(String editorial, String autor);

    // Buscar libros por categoría y título
    List<Libros> findByCategoria_NombreAndTitulo(String categoria, String titulo);
}
