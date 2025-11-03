  package gestion_biblioteca;

import java.util.Scanner;
import java.util.List;

// Clase principal que contiene el método main y el bucle del menú en consola.
// Actúa como la interfaz de usuario y gestiona las llamadas al SistemaBiblioteca.

public class Gestion_Biblioteca {

    public static void main(String[] args) {
        
        // La clase Scanner para la entrada de datos
        Scanner sc = new Scanner(System.in);
        // Instancia del sistema, que carga los datos de CSV al iniciar
        SistemaBiblioteca sistema = new SistemaBiblioteca();
        boolean salir = false;

        System.out.println("--- Sistema de Gestión de Biblioteca ---");
        
        // Bucle principal del menú
        while (!salir) {
            mostrarMenuPrincipal();
            
            try {
                int opcion = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        menuRegistrar(sc, sistema);
                        break;
                    case 2:
                        menuListar(sc, sistema);
                        break;
                    case 3:
                        buscarLibro(sc, sistema);
                        break;
                    case 4:
                        mostrarPrestamosActivosPorUsuario(sistema);
                        break;
                    case 5:
                        salir = true;
                        System.out.println("Saliendo del sistema..."); 
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("ERROR: Ingrese un valor numérico para la opción.");
                sc.nextLine(); // Limpiar linea
            }
            System.out.println("\n----------------------------------------\n");
        }
        sc.close(); // Cerramos el Scanner
    }
    
    // --- MÉTODOS DE MENÚ ---
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("(1) Registrar/Prestar/Devolver");
        System.out.println("(2) Listar/Reportes");
        System.out.println("(3) Buscar Libro por ISBN");
        System.out.println("(4) Mostrar cuántos libros tiene cada usuario");
        System.out.println("(5) Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void menuRegistrar(Scanner sc, SistemaBiblioteca sistema) {
        System.out.println("\n--- MENÚ DE REGISTRO Y GESTIÓN ---");
        System.out.println("(1) Registrar libro");
        System.out.println("(2) Registrar profesor");
        System.out.println("(3) Registrar estudiante");
        System.out.println("(4) Registrar préstamo");
        System.out.println("(5) Registrar devolución");
        System.out.print("Seleccione una opción: ");
        
        try {
            int opcion = sc.nextInt();
            sc.nextLine();
            
            switch(opcion) {
                case 1: registrarLibro(sc, sistema); break;
                case 2: registrarProfesor(sc, sistema); break;
                case 3: registrarEstudiante(sc, sistema); break;
                case 4: registrarPrestamo(sc, sistema); break;
                case 5: registrarDevolucion(sc, sistema); break;
                default: System.out.println("Opción no válida.");
            }
        } catch (java.util.InputMismatchException e) {
            System.err.println("ERROR: Ingrese un valor numérico.");
            sc.nextLine();
        }
    }
    
    private static void menuListar(Scanner sc, SistemaBiblioteca sistema) {
        System.out.println("\n--- MENÚ DE LISTADO Y REPORTES ---");
        System.out.println("(1) Listar todos los libros");
        System.out.println("(2) Listar todos los usuarios");
        System.out.println("(3) Reporte de préstamos activos");
        System.out.println("(4) Histórico de todos los préstamos");
        System.out.println("(5) Reporte: Usuarios con más préstamos"); // Extra
        System.out.print("Seleccione una opción: ");
        
        try {
            int opcion = sc.nextInt();
            sc.nextLine();
            
            switch(opcion) {
                case 1: listarLibros(sistema); break;
                case 2: listarUsuarios(sistema); break;
                case 3: listarPrestamosActivos(sistema); break;
                case 4: listarHistoricoPrestamos(sistema); break;
                case 5: reporteUsuariosConMasPrestamos(sistema); break; // Extra
                default: System.out.println("Opción no válida.");
            }
        } catch (java.util.InputMismatchException e) {
            System.err.println("ERROR: Ingrese un valor numérico.");
            sc.nextLine();
        }
    }

    // --- MÉTODOS DE REGISTRO ESPECÍFICOS ---

    private static void registrarLibro(Scanner sc, SistemaBiblioteca sistema) {
        System.out.print("Título del libro: ");
        String titulo = sc.nextLine();
        System.out.print("Autor del libro: ");
        String autor = sc.nextLine();
        System.out.print("ISBN (Identificador único): ");
        String isbn = sc.nextLine();
        
        Libro nuevoLibro = new Libro(titulo, autor, isbn);
        sistema.registrarLibro(nuevoLibro);
    }
    
    private static void registrarEstudiante(Scanner sc, SistemaBiblioteca sistema) {
        System.out.print("Nombre del estudiante: ");
        String nombre = sc.nextLine();
        System.out.print("ID/Matrícula del estudiante: ");
        String id = sc.nextLine();
        
        Estudiante nuevoEstudiante = new Estudiante(nombre, id);
        sistema.registrarUsuario(nuevoEstudiante);
    }

    private static void registrarProfesor(Scanner sc, SistemaBiblioteca sistema) {
        System.out.print("Nombre del profesor: ");
        String nombre = sc.nextLine();
        System.out.print("ID del profesor: ");
        String id = sc.nextLine();
        
        Profesor nuevoProfesor = new Profesor(nombre, id);
        sistema.registrarUsuario(nuevoProfesor);
    }
    
    private static void registrarPrestamo(Scanner sc, SistemaBiblioteca sistema) {
        System.out.print("ISBN del libro a prestar: ");
        String isbn = sc.nextLine();
        System.out.print("ID del usuario (Estudiante/Profesor): ");
        String idUsuario = sc.nextLine();
        
        // Manejo de las excepciones anidadas
        try {
            sistema.registrarPrestamo(isbn, idUsuario);
        } catch (SistemaBiblioteca.ExcepcionNoEncontrado | 
                 SistemaBiblioteca.ExcepcionLibroNoDisponible | 
                 SistemaBiblioteca.ExcepcionLimitePrestamosExcedido e) {
            System.err.println(e.getMessage());
        }
    }
    

    private static void registrarDevolucion(Scanner sc, SistemaBiblioteca sistema) {
        System.out.print("ISBN del libro a devolver: ");
        String isbn = sc.nextLine();
        System.out.print("ID del usuario (Estudiante/Profesor): "); // <-- Línea completada
        String idUsuario = sc.nextLine();
        
        try {
            // Llama al método del sistema para procesar la devolución
            sistema.registrarDevolucion(isbn, idUsuario);
        } catch (SistemaBiblioteca.ExcepcionNoEncontrado e) {
            // Maneja el caso en que el libro o usuario no existan
            System.err.println(e.getMessage());
        }
    }

    // --- MÉTODOS DE BÚSQUEDA Y REPORTE (FALTANTES) ---

    // Busca un libro por su ISBN y muestra su información.
    // (Llamado desde la opción 3 del menú principal)
    private static void buscarLibro(Scanner sc, SistemaBiblioteca sistema) {
        System.out.print("Ingrese el ISBN del libro a buscar: ");
        String isbn = sc.nextLine();
        try {
            Libro libro = sistema.buscarLibro(isbn);
            System.out.println("Libro encontrado:");
            System.out.println(libro); // Usa el método toString() del Libro
        } catch (SistemaBiblioteca.ExcepcionNoEncontrado e) {
            System.err.println(e.getMessage());
        }
    }

    // Muestra un resumen de cuántos libros tiene cada usuario.
    // (Llamado desde la opción 4 del menú principal)
    private static void mostrarPrestamosActivosPorUsuario(SistemaBiblioteca sistema) {
        System.out.println("\n--- REPORTE: PRÉSTAMOS ACTIVOS POR USUARIO ---");
        if (sistema.getUsuarios().isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        
        for (Usuario u : sistema.getUsuarios()) {
            int prestamosActivos = u.getPrestamosActivos().size();
            System.out.println("Usuario: " + u.getNombre() + " (ID: " + u.getId() + ") - Tipo: " + u.getTipoUsuario());
            System.out.println("  Límite: " + u.getLimiteP<ctrl61>restamos());
            System.out.println("  Activos: " + prestamosActivos);
            if (prestamosActivos > 0) {
                System.out.println("  Libros:");
                for (Prestamo p : u.getPrestamosActivos()) {
                    System.out.println("    - " + p.getLibro().getTitulo() + " (ISBN: " + p.getLibro().getIsbn() + ")");
                }
            }
            System.out.println("  -----------------");
        }
    }

    // --- MÉTODOS DE LISTADO (FALTANTES) ---

    // Imprime en consola todos los libros registrados.
    private static void listarLibros(SistemaBiblioteca sistema) {
        System.out.println("\n--- LISTADO DE LIBROS ---");
        if (sistema.getLibros().isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        for (Libro libro : sistema.getLibros()) {
            System.out.println(libro);
        }
    }

    // Imprime en consola todos los usuarios registrados.
    private static void listarUsuarios(SistemaBiblioteca sistema) {
        System.out.println("\n--- LISTADO DE USUARIOS ---");
        if (sistema.getUsuarios().isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        for (Usuario usuario : sistema.getUsuarios()) {
            System.out.println(usuario);
        }
    }

    //  Imprime en consola solo los préstamos que están activos.
    private static void listarPrestamosActivos(SistemaBiblioteca sistema) {
        System.out.println("\n--- REPORTE: PRÉSTAMOS ACTIVOS ---");
        List<Prestamo> activos = sistema.getPrestamosActivos();
        if (activos.isEmpty()) {
            System.out.println("No hay préstamos activos en este momento.");
            return;
        }
        for (Prestamo p : activos) {
            System.out.println(p);
        }
    }


    // Metodo del Extra
    // Muestra el reporte de usuarios ordenados por préstamos activos.
    private static void reporteUsuariosConMasPrestamos(SistemaBiblioteca sistema) {
        System.out.println("\n--- REPORTE: USUARIOS CON MÁS PRÉSTAMOS ACTIVOS ---");
        List<Usuario> usuariosOrdenados = sistema.getUsuariosOrdenadosPorPrestamosActivos();

        if (usuariosOrdenados.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("(Ordenados de mayor a menor número de préstamos)");
        int pos = 1;
        for (Usuario u : usuariosOrdenados) {
            System.out.printf("%d. [%d Préstamos] - %s (ID: %s, Tipo: %s)\n",
                    pos++,
                    u.getPrestamosActivos().size(),
                    u.getNombre(),
                    u.getId(),
                    u.getTipoUsuario());
        }
    }



    // Imprime en consola todo el historial de préstamos (activos y devueltos).
    private static void listarHistoricoPrestamos(SistemaBiblioteca sistema) {
        System.out.println("\n--- HISTÓRICO DE PRÉSTAMOS (TODOS) ---");
        if (sistema.getPrestamosHistorico().isEmpty()) {
            System.out.println("No hay historial de préstamos.");
            return;
        }
        for (Prestamo p : sistema.getPrestamosHistorico()) {
            System.out.println(p);
        }
    }

} 
