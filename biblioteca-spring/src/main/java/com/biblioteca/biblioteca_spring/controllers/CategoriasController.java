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

import com.biblioteca.biblioteca_spring.entities.Categorias;
import com.biblioteca.biblioteca_spring.services.CategoriasService;

import jakarta.validation.Valid;

@Controller
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    // Listar todas las categorías
    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<Categorias> categorias = categoriasService.obtenerTodasLasCategorias();
        model.addAttribute("categorias", categorias);
        return "categorias";
    }

    // Mostrar formulario para crear nueva categoría
    @GetMapping("/categorias/nueva")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categorias());
        return "nuevaCategoria";
    }

    // Guardar una nueva categoría
    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categorias categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevaCategoria";
        }
        categoriasService.save(categoria);
        return "redirect:/categorias";
    }

    // Eliminar una categoría
    @DeleteMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        categoriasService.delete(id);
        return "redirect:/categorias";
    }
}
