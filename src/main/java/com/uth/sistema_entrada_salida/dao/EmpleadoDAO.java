package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // Método para listar todos los empleados (Para llenar tu p:dataTable)
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.*, p.nombre AS puesto_nombre, p.descripcion FROM empleado e " +
                "LEFT JOIN puesto p ON e.id_puesto = p.id_puesto";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Puesto p = new Puesto();
                p.setIdPuesto(rs.getInt("id_puesto"));
                p.setNombre(rs.getString("puesto_nombre"));
                p.setDescripcion(rs.getString("descripcion"));

                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setIdentidad(rs.getString("identidad"));
                e.setPuesto(p);

                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // Método para contar el total de empleados
    public int contarEmpleados() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM empleado";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    // Método para registrar un nuevo empleado y su usuario de acceso en la misma transacción
    public boolean guardarConUsuario(Empleado e, String username, String password, String rol) {
        String sqlEmpleado = "INSERT INTO empleado (nombre, apellido, identidad, id_puesto) VALUES (?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (username, password, rol, id_empleado) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = Database.getConexion();
            conn.setAutoCommit(false); // Inicia la transacción

            // 1. Insertar el Empleado y obtener el id_empleado generado
            try (PreparedStatement psEmp = conn.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS)) {
                psEmp.setString(1, e.getNombre());
                psEmp.setString(2, e.getApellido());
                psEmp.setString(3, e.getIdentidad());
                psEmp.setInt(4, e.getPuesto().getIdPuesto());

                int filasAfectadas = psEmp.executeUpdate();
                if (filasAfectadas == 0) {
                    conn.rollback();
                    return false;
                }

                int idEmpleadoGenerado = 0;
                try (ResultSet rs = psEmp.getGeneratedKeys()) {
                    if (rs.next()) {
                        idEmpleadoGenerado = rs.getInt(1);
                    }
                }

                // 2. Insertar el Usuario vinculado al empleado creado
                if (idEmpleadoGenerado > 0 && username != null && !username.trim().isEmpty()) {
                    try (PreparedStatement psUser = conn.prepareStatement(sqlUsuario)) {
                        psUser.setString(1, username);
                        psUser.setString(2, password);
                        psUser.setString(3, rol);
                        psUser.setInt(4, idEmpleadoGenerado);

                        psUser.executeUpdate();
                    }
                }
            }

            conn.commit(); // Confirmar cambios si todo fue exitoso
            return true;

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback(); // Cancelar cambios si ocurre un error
                } catch (SQLException exRollback) {
                    exRollback.printStackTrace();
                }
            }
            ex.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException exClose) {
                    exClose.printStackTrace();
                }
            }
        }
    }

    // Método para modificar un empleado existente
    public boolean actualizar(Empleado e) {
        String sql = "UPDATE empleado SET nombre = ?, apellido = ?, identidad = ?, id_puesto = ? WHERE id_empleado = ?";
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getIdentidad());
            ps.setInt(4, e.getPuesto().getIdPuesto());
            ps.setInt(5, e.getIdEmpleado());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un empleado por su ID
    public boolean eliminar(int idEmpleado) {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}