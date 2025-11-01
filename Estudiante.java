package gestion_biblioteca;

public class Estudiante extends Usuario {

    // Límite definido por la regla de negocio
    private static final int LIMITE_PRESTAMOS_ESTUDIANTE = 3;

    // Constructor
    public Estudiante(String nombre, String id) {
        super(nombre, id);
    }

    @Override
    public int getLimitePrestamos() {
        return LIMITE_PRESTAMOS_ESTUDIANTE; // Límite para estudiantes
    }

    @Override
    public String getTipoUsuario() {
        return "Estudiante";
    }
}
