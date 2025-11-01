package gestion_biblioteca;

import java.util.ArrayList;
import java.util.Objects;

// Clase abstracta base para Estudiantes y Profesores.
public abstract class Usuario {

    // Atributos privados 
    private String nombre;
    private String id; // Identificador único (cédula o matrícula)
    
    // Cada usuario mantiene una lista de sus préstamos activos
    private ArrayList<Prestamo> prestamosActivos;

    // Constructor
    public Usuario(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.prestamosActivos = new ArrayList<>(); // Inicializamos la lista
    }

    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Prestamo> getPrestamosActivos() {
        return prestamosActivos;
    }

    // --- Métodos para gestionar préstamos del usuario ---

    // Añade un préstamo a la lista de activos del usuario.
    public void agregarPrestamo(Prestamo p) {
        this.prestamosActivos.add(p);
    }

    // Remueve un préstamo de la lista de activos (cuando se devuelve).
    public void removerPrestamo(Prestamo p) {
        this.prestamosActivos.remove(p);
    }

    // Verifica si el usuario ha alcanzado su límite de préstamos
    // true si alcanzó el límite, false en caso contrario.
    public boolean alcanzoLimitePrestamos() {
        return this.prestamosActivos.size() >= getLimitePrestamos();
    }


    // --- Métodos Abstractos (Polimorfismo)  ---
    
    // Retorna el límite de préstamos simultáneos.
    public abstract int getLimitePrestamos();

    // Retorna el tipo de usuario (Estudiante o Profesor).
    public abstract String getTipoUsuario();


    // --- Métodos obligatorios (toString, equals, hashCode) ---

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", tipo=" + getTipoUsuario() +
                ", prestamosActivos=" + prestamosActivos.size() + "/" + getLimitePrestamos() +
                '}';
    }

    // Compara dos usuarios basado en su ID.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
