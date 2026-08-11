package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Marcacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcacionDAO {

    // Método para listar todas las marcaciones generales
    public List<Marcacion> listarMarcaciones() {
        List<Marcacion> lista = new ArrayList<>();
        String sql = "SELECT m.*, e.nombre, e.apellido FROM marcacion m " +
                "INNER JOIN empleado e ON m.id_empleado = e.id_empleado " +
                "ORDER BY m.fecha_hora DESC";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));

                Marcacion m = new Marcacion();
                m.setIdMarcacion(rs.getInt("id_marcacion"));
                m.setEmpleado(emp);
                m.setFechaHora(rs.getTimestamp("fecha_hora"));
                m.setTipo(rs.getString("tipo"));

                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // NUEVO MÉTODO: Obtiene las marcaciones pasadas de un solo empleado ordenadas por fecha más reciente
    public List<Marcacion> listarMarcacionesPorEmpleado(int idEmpleado) {
        List<Marcacion> lista = new ArrayList<>();
        String sql = "SELECT m.*, e.nombre, e.apellido FROM marcacion m " +
                "INNER JOIN empleado e ON m.id_empleado = e.id_empleado " +
                "WHERE m.id_empleado = ? " +
                "ORDER BY m.fecha_hora DESC";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(rs.getInt("id_empleado"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellido(rs.getString("apellido"));

                    Marcacion m = new Marcacion();
                    m.setIdMarcacion(rs.getInt("id_marcacion"));
                    m.setEmpleado(emp);
                    m.setFechaHora(rs.getTimestamp("fecha_hora"));
                    m.setTipo(rs.getString("tipo"));

                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para registrar una nueva entrada o salida
    public boolean registrarMarcacion(int idEmpleado, String tipo) {
        String sql = "INSERT INTO marcacion (id_empleado, fecha_hora, tipo) VALUES (?, NOW(), ?)";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.setString(2, tipo);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // NUEVO MÉTODO: Filtra las marcaciones ingresando un rango de fechas para reportes
    public List<Marcacion> filtrarPorRangoFecha(Date fechaInicio, Date fechaFin) {
        List<Marcacion> lista = new ArrayList<>();
        String sql = "SELECT m.*, e.nombre, e.apellido FROM marcacion m " +
                "INNER JOIN empleado e ON m.id_empleado = e.id_empleado " +
                "WHERE DATE(m.fecha_hora) BETWEEN ? AND ? " +
                "ORDER BY m.fecha_hora DESC";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, fechaInicio);
            ps.setDate(2, fechaFin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(rs.getInt("id_empleado"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellido(rs.getString("apellido"));

                    Marcacion m = new Marcacion();
                    m.setIdMarcacion(rs.getInt("id_marcacion"));
                    m.setEmpleado(emp);
                    m.setFechaHora(rs.getTimestamp("fecha_hora"));
                    m.setTipo(rs.getString("tipo"));

                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}