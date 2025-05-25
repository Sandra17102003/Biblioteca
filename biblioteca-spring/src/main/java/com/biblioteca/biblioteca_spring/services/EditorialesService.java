package com.biblioteca.biblioteca_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_spring.entities.Editoriales;
import com.biblioteca.biblioteca_spring.repositories.EditorialesRepository;

@Service
public class EditorialesService {
 @Autowired
    private EditorialesRepository editorialesRepository;

    // Obtener todas las editoriales
    public List<Editoriales> obtenerTodasLasEditoriales() {
        return editorialesRepository.findAll();
    }

    //Método para buscar una editorial por nombre
    public Editoriales obtenerPorNombre(String nombre) {
        return editorialesRepository.findByNombre(nombre);
    }

    //Método para crear una editorial
    public void save(Editoriales editorial) {
        editorialesRepository.save(editorial);
    }

    //Método para borrar
    public void delete(Integer id) {
        editorialesRepository.deleteById(id);
    }
}
