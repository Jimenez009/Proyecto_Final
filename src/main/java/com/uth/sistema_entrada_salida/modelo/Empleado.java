package com.uth.sistema_entrada_salida.modelo;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String identidad;
    private Puesto puesto;

    public Empleado() {}

    public Empleado(int idEmpleado, String nombre, String apellido, String identidad, Puesto puesto) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identidad = identidad;
        this.puesto = puesto;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getIdentidad() { return identidad; }
    public void setIdentidad(String identidad) { this.identidad = identidad; }

    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }
}