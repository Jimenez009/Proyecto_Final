package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import java.sql.*;
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

    // Método para registrar un nuevo empleado
    public boolean guardar(Empleado e) {
        String sql = "INSERT INTO empleado (nombre, apellido, identidad, id_puesto) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getIdentidad());
            ps.setInt(4, e.getPuesto().getIdPuesto());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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