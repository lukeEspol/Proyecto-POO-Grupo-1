package gestion_biblioteca;

import gestion_biblioteca.Libro.EstadoLibro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Para el extra
import java.util.Comparator;

// Clase central que gestiona todas las colecciones y la lógica de negocio.
// Contiene las excepciones personalizadas como clases anidadas estáticas.

public class SistemaBiblioteca {
    
    // --- EXCEPCIONES PERSONALIZADAS ANIDADAS ---

    // Excepción lanzada cuando se intenta prestar un libro que no está DISPONIBLE.
    public static class ExcepcionLibroNoDisponible extends Exception {
        public ExcepcionLibroNoDisponible(String isbn) {
            super("ERROR: El libro con ISBN " + isbn + " no se encuentra disponible para préstamo.");
        }
    }

    // Excepción lanzada cuando un usuario excede su límite de préstamos activos.
    public static class ExcepcionLimitePrestamosExcedido extends Exception {
        public ExcepcionLimitePrestamosExcedido(String nombreUsuario, int limite) {
            super("ERROR: El usuario " + nombreUsuario + " ha excedido su límite de " + limite + 
                  " préstamos activos.");
        }
    }

    // Excepción genérica lanzada cuando un libro o usuario no se encuentra.
    public static class ExcepcionNoEncontrado extends Exception {
        public ExcepcionNoEncontrado(String tipoObjeto, String identificador) {
            super("ERROR: " + tipoObjeto + " con identificador '" + identificador + "' no encontrado.");
        }
    }
    // --- FIN DE EXCEPCIONES ANIDADAS ---

    // Colecciones que almacenan los objetos
    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamosHistorico; 

    // Constructor que inicializa las colecciones
    public SistemaBiblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prestamosHistorico = new ArrayList<>();
        
        cargarDatosIniciales();
    }
    
    // Carga los datos de prueba mínimos requeridos.
    private void cargarDatosIniciales() {
        System.out.println("Cargando datos iniciales en memoria...");
        
        // Libros
        this.libros.add(new Libro("El Principito", "Antoine de Saint-Exupéry", "001"));
        this.libros.add(new Libro("Cien años de soledad", "Gabriel García Márquez", "002"));
        this.libros.add(new Libro("Introducción a la Programación", "John Doe", "003"));

        // Usuarios
        this.usuarios.add(new Estudiante("Ana Gómez", "E001"));
        this.usuarios.add(new Estudiante("Luis Paz", "E002"));
        this.usuarios.add(new Profesor("Dr. Carlos Vera", "P001"));
        
        System.out.println("Datos iniciales cargados: " + 
                           libros.size() + " libros, " + 
                           usuarios.size() + " usuarios.");
    }
    

    // --- MÉTODOS DE BÚSQUEDA ---
    

    // Busca un libro por su ISBN (código único).
    public Libro buscarLibro(String isbn) throws ExcepcionNoEncontrado {
        for (Libro libro : libros) {
            if (libro.getIsbn().equalsIgnoreCase(isbn)) {
                return libro;
            }
        }
        throw new ExcepcionNoEncontrado("Libro", isbn);
    }

    // Busca un usuario por su ID.
    public Usuario buscarUsuario(String id) throws ExcepcionNoEncontrado {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equalsIgnoreCase(id)) {
                return usuario;
            }
        }
        throw new ExcepcionNoEncontrado("Usuario", id);
    }

    
    // --- MÉTODOS DE REGISTRO ---
    
    public void registrarLibro(Libro libro) {
        if (this.libros.contains(libro)) {
             System.out.println("ADVERTENCIA: El libro con ISBN " + libro.getIsbn() + " ya está registrado.");
        } else {
            this.libros.add(libro);
            System.out.println("Libro registrado exitosamente: " + libro.getTitulo());
        }
    }

    public void registrarUsuario(Usuario usuario) {
        if (this.usuarios.contains(usuario)) {
             System.out.println("ADVERTENCIA: El usuario con ID " + usuario.getId() + " ya está registrado.");
        } else {
            this.usuarios.add(usuario);
            System.out.println("Usuario registrado exitosamente: " + usuario.getNombre());
        }
    }
    
    // --- MÉTODOS DE PRÉSTAMO Y DEVOLUCIÓN ---
    
    // Realiza un nuevo préstamo, aplicando todas las reglas de negocio.
    public void registrarPrestamo(String isbn, String idUsuario) 
            throws ExcepcionNoEncontrado, ExcepcionLibroNoDisponible, ExcepcionLimitePrestamosExcedido {
        
        // 1. Buscar y validar existencia
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);
        
        // 2. Libro disponible
        if (libro.getEstado() == EstadoLibro.PRESTADO) {
            throw new ExcepcionLibroNoDisponible(isbn);
        }
        
        // 3. Límite de préstamos
        if (usuario.alcanzoLimitePrestamos()) {
            throw new ExcepcionLimitePrestamosExcedido(usuario.getNombre(), usuario.getLimitePrestamos());
        }
        
        // 4. Procesar el préstamo
        Prestamo nuevoPrestamo = new Prestamo(libro, usuario);
        
        // 5. Actualizar estados y colecciones
        libro.setEstado(EstadoLibro.PRESTADO);
        usuario.agregarPrestamo(nuevoPrestamo);
        this.prestamosHistorico.add(nuevoPrestamo);
        
        // 6. Mensaje
        System.out.println("\nPRÉSTAMO REGISTRADO CON ÉXITO:");
        System.out.println("  Libro: " + libro.getTitulo());
    }
    
    // Registra la devolución de un libro.
    public void registrarDevolucion(String isbn, String idUsuario) throws ExcepcionNoEncontrado {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);
        
        Prestamo prestamoActivo = null;
        for (Prestamo p : usuario.getPrestamosActivos()) {
            if (p.getLibro().equals(libro)) {
                prestamoActivo = p;
                break;
            }
        }
        
        if (prestamoActivo == null) {
            System.out.println("ADVERTENCIA: No se encontró un préstamo ACTIVO del libro " + libro.getTitulo() + 
                               " a nombre del usuario " + usuario.getNombre() + ".");
            return;
        }
        
        // Procesar la devolución
        prestamoActivo.registrarDevolucion();
        libro.setEstado(EstadoLibro.DISPONIBLE);
        usuario.removerPrestamo(prestamoActivo);
        
        System.out.println("\nDEVOLUCIÓN REGISTRADA CON ÉXITO:");
        System.out.println("  Libro: " + libro.getTitulo());
    }

    // --- MÉTODOS DE LISTADO ---
    
    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Prestamo> getPrestamosHistorico() {
        return prestamosHistorico;
    }

    // Extra 
    // Retorna una lista de todos los usuarios, ordenados de mayor a menor
    // según la cantidad de préstamos activos que tienen actualmente.
    public List<Usuario> getUsuariosOrdenadosPorPrestamosActivos() {
        // Creamos una copia de la lista de usuarios para no alterar el original
        List<Usuario> usuariosOrdenados = new ArrayList<>(this.usuarios);
        
        // Comparacion
        usuariosOrdenados.sort(
            Comparator.comparingInt((Usuario u) -> u.getPrestamosActivos().size()).reversed());
        
        return usuariosOrdenados;
    }
    // Retorna una lista de solo los préstamos que aún no han sido devueltos.
    public List<Prestamo> getPrestamosActivos() {
        return prestamosHistorico.stream()
                .filter(Prestamo::estaActivo) 
                .collect(Collectors.toList());
    }
}
