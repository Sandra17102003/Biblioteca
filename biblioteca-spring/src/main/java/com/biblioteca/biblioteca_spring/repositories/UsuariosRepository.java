package com.biblioteca.biblioteca_spring.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_spring.entities.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    //Métddo para buscar por nombre de usuario
    Optional<Usuarios> findByNombUsuario(String nombUsuario);

}
