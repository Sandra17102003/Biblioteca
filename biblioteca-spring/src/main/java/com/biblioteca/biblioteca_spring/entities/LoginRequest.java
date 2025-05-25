package com.biblioteca.biblioteca_spring.entities;
/**
 * DTO para recibir los datos de Login via WEB
 */
public class LoginRequest {
    private String nombUsuario;
    private String password;
    
    //Getters y Setters
    public String getNombUsuario() {
        return nombUsuario;
    }
    public void setNombUsuario(String nombUsuario) {
        this.nombUsuario = nombUsuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
