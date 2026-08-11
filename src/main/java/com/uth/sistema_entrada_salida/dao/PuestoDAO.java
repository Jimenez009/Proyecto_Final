package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuestoDAO {

    // Método para listar todos los puestos
    public List<Puesto> listarPuestos() {
        List<Puesto> lista = new ArrayList<>();
        String sql = "SELECT * FROM puesto";

        // CORRECCIÓN: Se utiliza getConexion()
        try (Connection conn = Database.getConexion();
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

    // Método para guardar un nuevo puesto
    public boolean guardar(Puesto p) {
        String sql = "INSERT INTO puesto (nombre, descripcion) VALUES (?, ?)";

        // CORRECCIÓN: Se utiliza getConexion()
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // NUEVO MÉTODO: Para editar un puesto existente
    public boolean actualizar(Puesto p) {
        String sql = "UPDATE puesto SET nombre = ?, descripcion = ? WHERE id_puesto = ?";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdPuesto());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // NUEVO MÉTODO: Para eliminar un puesto por su ID
    public boolean eliminar(int idPuesto) {
        String sql = "DELETE FROM puesto WHERE id_puesto = ?";
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPuesto);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


}