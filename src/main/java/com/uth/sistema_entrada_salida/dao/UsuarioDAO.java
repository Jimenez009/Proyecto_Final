package com.uth.sistema_entrada_salida.dao;

import com.uth.sistema_entrada_salida.config.Database;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para validar las credenciales de inicio de sesión
    public Usuario validarLogin(String username, String password) {
        String sql = "SELECT u.*, e.nombre, e.apellido FROM usuario u " +
                "INNER JOIN empleado e ON u.id_empleado = e.id_empleado " +
                "WHERE u.username = ? AND u.password = ?";

        // CORRECCIÓN: Se utiliza getConexion()
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(rs.getInt("id_empleado"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellido(rs.getString("apellido"));

                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setUsername(rs.getString("username"));
                    u.setRol(rs.getString("rol"));
                    u.setEmpleado(emp);
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para listar todos los usuarios del sistema
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.*, e.nombre, e.apellido FROM usuario u " +
                "LEFT JOIN empleado e ON u.id_empleado = e.id_empleado";

        // CORRECCIÓN: Se utiliza getConexion()
        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setRol(rs.getString("rol"));
                u.setEmpleado(emp);
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // NUEVO MÉTODO: Para registrar un nuevo usuario en la base de datos
    public boolean guardar(Usuario u) {
        String sql = "INSERT INTO usuario (username, password, rol, id_empleado) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRol());
            ps.setInt(4, u.getEmpleado().getIdEmpleado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // NUEVO MÉTODO: Para editar los datos de un usuario existente
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuario SET username = ?, password = ?, rol = ?, id_empleado = ? WHERE id_usuario = ?";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRol());
            ps.setInt(4, u.getEmpleado().getIdEmpleado());
            ps.setInt(5, u.getIdUsuario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // NUEVO MÉTODO: Para eliminar un usuario del sistema
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conn = Database.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}