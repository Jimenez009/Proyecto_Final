package com.uth.sistema_entrada_salida.modelo;

public class Usuario {
    private int idUsuario;
    private String username;
    private String password;
    private String rol;
    private Empleado empleado;

    public Usuario() {}

    public Usuario(int idUsuario, String username, String password, String rol, Empleado empleado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.empleado = empleado;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}