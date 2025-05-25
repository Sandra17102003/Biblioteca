package com.biblioteca.biblioteca_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_spring.entities.Categorias;

public interface CategoriasRepository extends JpaRepository<Categorias, Integer>{
    // Método para buscar categorías por nombre
    List<Categorias> findByNombre(String nombre);
}
