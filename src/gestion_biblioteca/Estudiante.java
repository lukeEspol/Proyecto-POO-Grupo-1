package gestion_biblioteca;

public class Estudiante extends Usuario {

    static int numEstudiantes;

    int prestamosDisponibles;

    Estudiante(String nombre) {
        super(nombre);
        this.prestamosDisponibles = 3;

    }
}
