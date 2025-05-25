package com.biblioteca.biblioteca_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.biblioteca.biblioteca_spring.entities.Prestamos;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamos, Integer>{
    //Método para buscar préstamos por el nombre del usuario
    List<Prestamos> findByUsuarioId(Integer usuarioId);

    //Método para contar los préstamos activos de un usuario (de momento  no se utiliza este método)
    long countByUsuarioIdAndFechaDevolucionIsNull(Integer usuarioId);

    // Método para buscar préstamos activos por libro (sin fecha de devolución)
    List<Prestamos> findByLibroIdAndFechaDevolucionIsNull(Integer libroId);
}
