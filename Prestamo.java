package gestion_biblioteca;

import java.time.LocalDate;
import java.util.Objects;

public class Prestamo {

    // Atributos privados 
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion; // Será null si el préstamo está activo

    // Constructor
    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now(); // Fecha actual 
        this.fechaDevolucion = null; // Aún no se ha devuelto
    }

    // Constructor para cargar desde CSV (incluye fechas)
    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // --- Getters y Setters ---

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    // Marca el préstamo como devuelto estableciendo la fecha de hoy.
    public void registrarDevolucion() {
        this.fechaDevolucion = LocalDate.now();
    }

    // Verifica si el préstamo sigue activo (aún no se ha devuelto).
    public boolean estaActivo() {
        return this.fechaDevolucion == null;
    }

    // --- Métodos obligatorios (toString, equals, hashCode)  ---

    @Override
    public String toString() {
        String estado = estaActivo() ? "ACTIVO" : "DEVUELTO (" + fechaDevolucion + ")";
        return "Prestamo{" +
                "libro=" + libro.getTitulo() + " (ISBN: " + libro.getIsbn() + ")" +
                ", usuario=" + usuario.getNombre() + " (ID: " + usuario.getId() + ")" +
                ", fechaPrestamo=" + fechaPrestamo +
                ", estado=" + estado +
                '}';
    }

    // Un préstamo es único por la combinación de libro y usuario en un momento dado.
    // Definimos un préstamo activo duplicado si el mismo libro y usuario tienen un préstamo con fechaDevolucion = null
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(libro, prestamo.libro) &&
                Objects.equals(usuario, prestamo.usuario) &&
                Objects.equals(fechaPrestamo, prestamo.fechaPrestamo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libro, usuario, fechaPrestamo);
    }
}
