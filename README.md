# Sistema de Gestión de Biblioteca (POO - Consola)

Este proyecto es una aplicación de consola en Java para la administración de una biblioteca. Permite gestionar libros, usuarios (estudiantes y profesores) y el proceso de préstamos y devoluciones, aplicando principios de Programación Orientada a Objetos.

---

## 🚀 Funcionalidad Principal

El sistema, ejecutado desde la clase `Gestion_Biblioteca`, ofrece un menú interactivo en consola que permite:

* **Registrar:** Dar de alta nuevos libros, estudiantes y profesores en el sistema.
* **Prestar:** Asignar un libro a un usuario. El sistema valida automáticamente:
    * Que el libro esté `DISPONIBLE`.
    * Que el usuario no haya alcanzado su límite de préstamos (3 para Estudiantes, 5 para Profesores).
* **Devolver:** Registrar la devolución de un libro, marcándolo como `DISPONIBLE` nuevamente.
* **Listar y Reportar:**
    * Ver una lista de todos los libros registrados.
    * Ver una lista de todos los usuarios (estudiantes y profesores).
    * Generar un reporte de préstamos actualmente activos.
    * Ver un historial completo de todos los préstamos (activos y devueltos).
    * Generar un reporte de **usuarios ordenados** de mayor a menor según su cantidad de préstamos activos.
* **Consultar:**
    * Buscar un libro específico por su ISBN.
    * Mostrar un resumen de cuántos libros tiene cada usuario y cuál es su límite.

Al iniciar, el sistema carga un conjunto de datos de prueba (`cargarDatosIniciales`) para facilitar la demostración.

---

## 🏗️ Estructura del Proyecto

El proyecto está organizado en las siguientes clases clave:

### 1. Clases de Interfaz y Control

* **`Gestion_Biblioteca`**: Es el punto de entrada de la aplicación (`main`). Contiene toda la lógica del menú de consola, captura la entrada del usuario (`Scanner`) y delega las operaciones a `SistemaBiblioteca`.
* **`SistemaBiblioteca`**: Actúa como el "motor" o controlador principal. Mantiene las colecciones de datos (libros, usuarios e historial de préstamos) en `ArrayList`s. Contiene la lógica de negocio para registrar préstamos, devoluciones y realizar búsquedas.

### 2. Clases del Modelo (Dominio)

* **`Libro`**: Representa un libro con sus atributos (título, autor, ISBN). Contiene un `enum` anidado `EstadoLibro` (`DISPONIBLE`, `PRESTADO`) para gestionar su disponibilidad.
* **`Usuario` (Abstracta)**: Clase base que define las propiedades comunes de un usuario (nombre, id) y una lista de sus préstamos activos. Define métodos abstractos (`getLimitePrestamos`, `getTipoUsuario`) para ser implementados por sus subclases.
* **`Estudiante` y `Profesor`**: Clases concretas que heredan de `Usuario`. Implementan los métodos abstractos, especificando sus límites de préstamo (3 y 5, respectivamente), demostrando el polimorfismo.
* **`Prestamo`**: Representa la transacción de un préstamo. Vincula un `Libro` y un `Usuario`, y almacena la `LocalDate` de préstamo y devolución.

### 3. Manejo de Errores

El sistema utiliza excepciones personalizadas (definidas como clases estáticas anidadas dentro de `SistemaBiblioteca`) para gestionar situaciones excepcionales:

* `ExcepcionNoEncontrado`: Se lanza si se busca un libro o usuario por ID/ISBN y no se encuentra.
* `ExcepcionLibroNoDisponible`: Se lanza al intentar prestar un libro que ya está `PRESTADO`.
* `ExcepcionLimitePrestamosExcedido`: Se lanza si un usuario intenta pedir un libro pero ya alcanzó su límite.

Estas excepciones son lanzadas por `SistemaBiblioteca` y capturadas en `Gestion_Biblioteca` para mostrar un mensaje de error claro al usuario en la consola.

---

## ⚙️ Cómo Ejecutar

El proyecto no requiere dependencias externas más allá de Java.

1.  **Compilar:**
    ```
    # Navega al directorio que contiene la carpeta 'gestion_biblioteca'
    javac gestion_biblioteca/*.java
    ```

2.  **Ejecutar:**
    ```
    # Desde el mismo directorio
    java gestion_biblioteca.Gestion_Biblioteca
    ```
