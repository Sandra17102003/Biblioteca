package com.biblioteca.biblioteca_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_spring.entities.Categorias;
import com.biblioteca.biblioteca_spring.repositories.CategoriasRepository;

import java.util.List;

@Service
public class CategoriasService {
    @Autowired
    private CategoriasRepository categoriaRepository;

    // Método para obtener todas las categorías
    public List<Categorias> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    // Método para buscar categoría por nombre
    public List<Categorias> buscarCategoriasPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    //Método para guardar una categoría
    public void save(Categorias categoria) {
        categoriaRepository.save(categoria);
    }

    //Método para borrar una categoría
    public void delete(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
