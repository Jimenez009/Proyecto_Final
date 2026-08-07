package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.*, p.nombre AS puesto_nombre, p.descripcion FROM empleado e " +
                "LEFT JOIN puesto p ON e.id_puesto = p.id_puesto";
        try (Connection conn = Database.getConnection();
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

    public boolean guardar(Empleado e) {
        String sql = "INSERT INTO empleado (nombre, apellido, identidad, id_puesto) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
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
}