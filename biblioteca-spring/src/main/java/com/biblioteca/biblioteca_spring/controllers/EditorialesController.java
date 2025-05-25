package com.biblioteca.biblioteca_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.biblioteca.biblioteca_spring.entities.Editoriales;
import com.biblioteca.biblioteca_spring.services.EditorialesService;

import jakarta.validation.Valid;

@Controller
public class EditorialesController {

    @Autowired
    private EditorialesService editorialesService;

    // Listar todas las editoriales
    @GetMapping("/editoriales")
    public String listarEditoriales(Model model) {
        List<Editoriales> editoriales = editorialesService.obtenerTodasLasEditoriales();
        model.addAttribute("editoriales", editoriales);
        return "editoriales";
    }

    // Mostrar formulario para crear nueva editorial
    @GetMapping("/editoriales/nueva")
    public String nuevaEditorial(Model model) {
        model.addAttribute("editorial", new Editoriales());
        return "nuevaEditorial";
    }
    // Guardar una nueva editorial
    @PostMapping("/editoriales/guardar")
    public String guardarEditorial(@Valid @ModelAttribute Editoriales editorial, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevaEditorial";
        }
        editorialesService.save(editorial);
        return "redirect:/editoriales";
    }

    // Eliminar una editorial
    @DeleteMapping("/editoriales/eliminar/{id}")
    public String eliminarEditorial(@PathVariable Integer id) {
        editorialesService.delete(id);
        return "redirect:/editoriales";
    }
}
