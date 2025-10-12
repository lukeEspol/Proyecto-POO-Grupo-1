package gestion_biblioteca;

public class Profesor extends Usuario {

    static int numProfesores;

    int prestamosDisponibles;

    Profesor(String nombre) {
        super(nombre);
        this.prestamosDisponibles = 5;
    }
}
