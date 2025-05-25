package com.biblioteca.biblioteca_spring.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name= "usuario")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_usuario", unique = true)
    @NotNull(message = "El nombre de usuario es obligatorio")
    @Size(min = 5, max = 12, message ="El nombre de usuario debe tener entre 5 y 12 caracteres.")
    private String nombUsuario;

    
    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 5, message = "La contraseña debe tener un mínimo de 5 caracteres")
    private String password;


    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "email", unique = true)
    @Email(message = "Email no válido.")
    @NotNull(message = "El E-mail de usuario es obligatorio")
    private String email;

    @OneToMany(mappedBy = "usuario")
    private List<Prestamos> prestamos;


    //Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


