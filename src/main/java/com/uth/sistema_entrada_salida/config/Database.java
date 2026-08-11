package com.uth.sistema_entrada_salida.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String HOST = "mysql-3b4936c1-proyectofinal1.c.aivencloud.com";


    private static final String PORT = "15099";


    private static final String DB_NAME = "sistema_asistencia";

    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_B7GguJsrloKH_waDBlm";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME +
            "?useSSL=true&requireSSL=true&serverTimezone=UTC";

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos de Aiven para el sistema de control de entradas y salidas!");

        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL. Asegúrate de tener la dependencia en el pom.xml. " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con Aiven: " + e.getMessage());
        }
        return conexion;
    }

    // Método para probar la conexión directamente
    public static void main(String[] args) {
        getConexion();
    }
}