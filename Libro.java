package gestion_biblioteca;

import java.util.Objects;

public class Libro {

    // --- Enum Anidado ---
     // Define los estados posibles de un libro en la biblioteca.
    public enum EstadoLibro {
        DISPONIBLE,
        PRESTADO
    }

    // Atributos privados (encapsulamiento)
    private String titulo;
    private String isbn; // Usamos ISBN como identificador único
    private String autor; 
    private EstadoLibro estado; 

    // Constructor
    public Libro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        // Por defecto, un libro nuevo siempre está disponible
        this.estado = EstadoLibro.DISPONIBLE; 
    }

    // --- Getters y Setters ---
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    // --- Métodos obligatorios (toString, equals, hashCode) ---

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", estado=" + estado + // Mostrará DISPONIBLE o PRESTADO
                '}';
    }

    // Compara dos libros basado únicamente en su ISBN.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        // Dos libros son iguales si su ISBN es igual
        return Objects.equals(isbn, libro.isbn);
    }

    // Genera un hashCode basado en el ISBN.
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
