package com.biblioteca.biblioteca_spring.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Clase para manejar excepciones
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        // Mapa para almacenar los errores de validación
        Map<String,String> errores= new HashMap<>();
        // Se recorren los errores de validación y se agregan al mapa
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            errores.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        // Se agregan atributos al modelo para mostrar el mensaje de error en la vista
        model.addAttribute("error", true);
        model.addAttribute("errorMessage", "Error: El ISBN ya está registrado.");
        return "editarlibro";
    }
}
