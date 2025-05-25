package com.biblioteca.biblioteca_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_spring.entities.Editoriales;

public interface EditorialesRepository extends JpaRepository<Editoriales, Integer>{
    // Método para buscar editoriales por nombre
    Editoriales findByNombre(String nombre);
}
