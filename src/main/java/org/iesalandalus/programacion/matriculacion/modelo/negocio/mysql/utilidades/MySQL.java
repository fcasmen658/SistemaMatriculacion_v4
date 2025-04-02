package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private static final String HOST = "daw-2425.cvyltug3cy3z.us-east-1.rds.amazonaws.com";
    private static final String ESQUEMA = "sistemamatriculacion";
    private static final String USUARIO = "sistemamatriculacion";
    private static final String CONTRASENA = "sistemamatriculacion-2024";

    private static Connection conexion;

    private MySQL() {
    }

    // Método para establecer la conexión a la base de datos (Singleton)
    public static Connection establecerConexion() {
        if (conexion == null) {
            try {
                // Cargar el driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Crear la URL de conexión
                String url = "jdbc:mysql://" + HOST + ":3306/" + ESQUEMA;
                // Establecer la conexión
                conexion = DriverManager.getConnection(url, USUARIO, CONTRASENA);
                System.out.println("Conexión establecida con éxito a la base de datos.");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());;
            }
        }
        return conexion;
    }

    // Método para cerrar la conexión a la base de datos
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("Conexión con la base de datos cerrada con éxito.");
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
    }

}
