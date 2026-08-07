package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuestoDAO {

    public List<Puesto> listarPuestos() {
        List<Puesto> lista = new ArrayList<>();
        String sql = "SELECT * FROM puesto";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Puesto p = new Puesto();
                p.setIdPuesto(rs.getInt("id_puesto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardar(Puesto p) {
        String sql = "INSERT INTO puesto (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}