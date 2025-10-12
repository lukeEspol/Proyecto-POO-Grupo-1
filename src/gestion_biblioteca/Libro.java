package gestion_biblioteca;

public class Libro {

    static int librosTotal;

    String nombre;
    String codigo;
    boolean disponible;

    Libro(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.disponible = true;
    }

    Libro(String nombre) {
        this.nombre = nombre;
        this.codigo = "Codigo no proporcionado";
        this.disponible = true;
    }

    void buscar() {
        // Buscar libro por titulo o ISBN
    }
}
