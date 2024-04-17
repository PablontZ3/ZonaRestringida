package Zona_restringida;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Zona_Restringida {

	    public static void main(String[] args) {
	        HashMap<String, String> usuarios = new HashMap<>();
	        final String ARCHIVO_USUARIOS = "usuarios.txt";
	        final String ARCHIVO_REGISTRO = "registro.txt";

	        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                String[] partes = linea.split(",");
	                usuarios.put(partes[0], partes[1]);
	            }
	        } catch (IOException e) {
	            System.out.println("Error al cargar los usuarios.");
	        }
	        Scanner scanner = new Scanner(System.in);
	        int opcion;
	        do {
	            System.out.println("1. Ingresar al área restringida");
	            System.out.println("2. Salir");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();
	            switch (opcion) {
	                case 1:
	                    int intentos = 3;
	                    boolean acceso = false;
	                    while (intentos > 0 && !acceso) {
	                        System.out.print("Ingrese su usuario: ");
	                        String usuario = scanner.next();
	                        System.out.print("Ingrese su contraseña: ");
	                        String contrasena = scanner.next();
	                        if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contrasena)) {

	                            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_REGISTRO, true))) {
	                                bw.write("Usuario: " + usuario + ", Fecha y hora de ingreso: " + timestamp);
	                                bw.newLine();
	                            } catch (IOException e) {
	                                System.out.println("Error al registrar el ingreso.");
	                            }
	                            acceso = true;
	                        } else {
	                            System.out.println("Usuario o contraseña incorrectos. Intentos restantes: " + --intentos);
	                        }
	                    }
	                    if (acceso) {

	                        do {
	                            System.out.println("1. Ver registro");
	                            System.out.println("2. Crear nuevo usuario");
	                            System.out.println("3. Salir del área restringida");
	                            System.out.print("Seleccione una opción: ");
	                            opcion = scanner.nextInt();
	                            switch (opcion) {
	                                case 1:
	                                    try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_REGISTRO))) {
	                                        String linea;
	                                        while ((linea = br.readLine()) != null) {
	                                            System.out.println(linea);
	                                        }
	                                    } catch (IOException e) {
	                                        System.out.println("Error al mostrar el registro.");
	                                    }
	                                    break;
	                                case 2:
	                                    System.out.print("Ingrese el nuevo usuario: ");
	                                    String nuevoUsuario = scanner.next();
	                                    System.out.print("Ingrese la nueva contraseña: ");
	                                    String nuevaContrasena = scanner.next();
	                                    usuarios.put(nuevoUsuario, nuevaContrasena);
	                                    System.out.println("Usuario creado exitosamente.");
	                                    break;
	                                case 3:
	                                    System.out.println("Saliendo del área restringida...");
	                                    break;
	                            }
	                        } while (opcion != 3);
	                    }
	                    break;
	                case 2:
	                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
	                        for (Map.Entry<String, String> usuario : usuarios.entrySet()) {
	                            bw.write(usuario.getKey() + "," + usuario.getValue());
	                            bw.newLine();
	                        }
	                    } catch (IOException e) {
	                        System.out.println("Error al guardar los usuarios.");
	                    }
	                    System.out.println("Saliendo del programa...");
	                    break;
	            }
	        } while (opcion != 2);
	        scanner.close();
	    }
	}
