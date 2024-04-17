package Zona_restringida;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
/**
 * Programa que nos muestra un menu y nos permite entrar a un area restringida.
 * 
 * @author Pablo Martinez
 * @version 2.0.0
 * La version ha sido actualizada con una refactorizacion y comentando el texto.
 */
public class Zona_Restringida {
    private static HashMap<String, String> usuarios = new HashMap<>();
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_REGISTRO = "registro.txt";
/**
 * Main con el menu inicial
 * @param args 
 */
    public static void main(String[] args) {
        cargarUsuarios();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Ingresar al área restringida");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    if (intentarIngreso(scanner)) {
                        menuAreaRestringida(scanner);
                    }
                    break;
                case 2:
                    guardarUsuarios();
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 2);
        scanner.close();
    }
/**
 * Cargamos los usuarios
 */
    private static void cargarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                usuarios.put(partes[0], partes[1]);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los usuarios.");
        }
    }
/**
 * Guardamos los usuarios
 */
    private static void guardarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Map.Entry<String, String> usuario : usuarios.entrySet()) {
                bw.write(usuario.getKey() + "," + usuario.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los usuarios.");
        }
    }
/**
 * Probamos a entrar al area restringida
 * @param scanner Escaner para escribir
 * @return si has podido acceder
 */
    private static boolean intentarIngreso(Scanner scanner) {
        int intentos = 3;
        while (intentos > 0) {
            System.out.print("Ingrese su usuario: ");
            String usuario = scanner.next();
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.next();
            if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contrasena)) {
                registrarIngreso(usuario);
                return true;
            } else {
                System.out.println("Usuario o contraseña incorrectos. Intentos restantes: " + --intentos);
            }
        }
        return false;
    }
/**
 * Apuntamos el ingrreso al area restringida
 * @param usuario Usuario que accede al area
 */
    private static void registrarIngreso(String usuario) {
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_REGISTRO, true))) {
            bw.write("Usuario: " + usuario + ", Fecha y hora de ingreso: " + timestamp);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al registrar el ingreso.");
        }
    }
/**
 * Medu del area restringida
 * @param scanner el escanner en el que escribiremos
 */
    private static void menuAreaRestringida(Scanner scanner) {
        int opcion;
        do {
            System.out.println("1. Ver registro");
            System.out.println("2. Crear nuevo usuario");
            System.out.println("3. Salir del área restringida");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    mostrarRegistro();
                    break;
                case 2:
                    crearUsuario(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del área restringida...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }
/**
 * Mostramos el registro de entradas
 */
    private static void mostrarRegistro() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_REGISTRO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al mostrar el registro.");
        }
    }
/**
 * Creamos usuario nuevo
 * @param scanner escanner en el que escribimos
 */
    private static void crearUsuario(Scanner scanner) {
        System.out.print("Ingrese el nuevo usuario: ");
        String nuevoUsuario = scanner.next();
        System.out.print("Ingrese la nueva contraseña: ");
        String nuevaContrasena = scanner.next();
        usuarios.put(nuevoUsuario, nuevaContrasena);
        System.out.println("Usuario creado exitosamente.");
    }
}

