package gestion_biblioteca;

public class Profesor extends Usuario {

    // Límite definido por la regla de negocio
    private static final int LIMITE_PRESTAMOS_PROFESOR = 5;

    // Constructor
    public Profesor(String nombre, String id) {
        super(nombre, id);
    }

    @Override
    public int getLimitePrestamos() {
        return LIMITE_PRESTAMOS_PROFESOR; // Límite para profesores
    }

    @Override
    public String getTipoUsuario() {
        return "Profesor";
    }
}
