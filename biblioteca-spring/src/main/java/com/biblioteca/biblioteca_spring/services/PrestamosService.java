package com.biblioteca.biblioteca_spring.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.biblioteca_spring.entities.Prestamos;
import com.biblioteca.biblioteca_spring.repositories.PrestamosRepository;



@Service
public class PrestamosService {
    
    @Autowired
    private PrestamosRepository prestamosRepository;

    //Se obtienen todos los préstamos
    public List<Prestamos> obtenerTodosPrestamos(){
        return prestamosRepository.findAll();
    }

    //Buscar un préstamo por ID
    public Optional<Prestamos> obtenerPrestamoPorId(Integer id){
        return prestamosRepository.findById(id);
    }

    //Buscar un préstamo por ID (son optional)
    public List<Prestamos> obtenerPrestamosPorUsuario(Integer usuarioId) {
        return prestamosRepository.findByUsuarioId(usuarioId);
    }

    //Guardar un nuevo préstamo
    public Prestamos guardar (Prestamos prestamo){        
        /*
        Se ha tratado de cambiar el estado del préstamo del libro al guardar un préstamo, pero no se ha conseguido.
        Se deja comentado para futuras mejoras:

        Libros libro = prestamo.getLibro();
        Se comprueba si el libro ya está prestado
        if (libro.getEstado() == Estado.prestado) {
            throw new IllegalStateException("El libro no está disponible para préstamo.");
        }

        // Se actualizam el estado del libro a "prestado"
        libro.setEstado(Estado.prestado);
        librosService.guardar(libro);
        */

        // Se guarda el préstamo en la base de datos
        prestamo.setFechaDevolucion(null);  // La fecha de devolución es NULL al prestar el libro
        return prestamosRepository.save(prestamo);
    }

    // Método para eliminar un préstamo por ID
    public void eliminar(Integer id){
        if (!prestamosRepository.existsById(id)){
            throw new IllegalStateException("El préstamo no existe.");
        }
        prestamosRepository.deleteById(id);
    }

    // Método para devolver un libro
    public void devolverLibro(Integer id){
        Optional<Prestamos> prestamoOptional = prestamosRepository.findById(id);
        if (prestamoOptional.isEmpty()){
            throw new IllegalArgumentException("No se ha encontrado el préstamo.");
        }
        Prestamos prestamo = prestamoOptional.get();

        //Se establece la fecha de devolución a la actual
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamosRepository.save(prestamo);

        /*
        Se ha tratado de cambiar el estado del préstamo del libro al devolver un préstamo, pero no se ha conseguido.
        Se deja comentado para futuras mejoras:
        Se cambia el estado del libro a "disponible"
        Libros libro = prestamo.getLibro();
        libro.setEstado(Estado.disponible);
        librosService.guardar(libro);
        */
    }

    //Método para validar que la fecha de devolución no sea anterior a la fecha de préstamo (finalmente no se utiliza)
    public boolean validarFechas (Prestamos prestamo){
        if(prestamo.getFechaDevolucion() != null){
            return !prestamo.getFechaDevolucion().isBefore(prestamo.getFechaPrestamo());
        }
        return true; //Si no hay fecha de devolución, es válido
    }

    
}
