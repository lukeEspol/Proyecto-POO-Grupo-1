package gestion_biblioteca;

import java.util.Scanner;

public class Gestion_Biblioteca {

    public static void main(String[] args) {
        System.out.println("(1) Registrar");
        System.out.println("(2) Listar");
        System.out.println("(3) Buscar Libro");
        System.out.println("(4) Mostrar cuantos libros tiene cada usuario");
        System.out.println("(5) Salir");

        Scanner sc = new Scanner(System.in);
        int numUsuario = sc.nextInt();
        sc.nextLine();

        switch (numUsuario) {
            case (1):
                System.out.println("(1) Registrar libro");
                System.out.println("(2) Registrar profesor");
                System.out.println("(3) Registrar estudiante");
                System.out.println("(4) Registrar prestamo");
                System.out.println("(5) Registrar devolucion");

                int numUsuario1 = sc.nextInt();
                sc.nextLine();
                switch (numUsuario1) {
                    case (1):

                    case (2):

                    case (3):

                    case (4):

                    case (5):

                    default:
                        System.out.println("Error"); // no esta completo

                }
            case (2):
                System.out.println("(1) Listar libros");
                System.out.println("(2) Listar usuarios");
                System.out.println("(3) Listar prestamos activos");
                System.out.println("(4) Listar prestamos por usuario");
                int numUsuario2 = sc.nextInt();
                sc.nextLine();
                switch (numUsuario2) {
                    case (1):

                    case (2):

                    case (3):

                    case (4):

                    default:
                        System.out.println("Error"); // (no esta completo)

                }

            case (3):
                System.out.println("1");
            case (4):
                System.out.println("1");
            case (5):
                System.out.println("1");
            default:
                System.out.println("Error"); // (no esta completo)

        }

    }

}
