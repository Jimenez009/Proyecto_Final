package com.uth.sistema_entrada_salida.modelo;

public class Puesto {
    private int idPuesto;
    private String nombre;
    private String descripcion;

    public Puesto() {}

    public Puesto(int idPuesto, String nombre, String descripcion) {
        this.idPuesto = idPuesto;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdPuesto() { return idPuesto; }
    public void setIdPuesto(int idPuesto) { this.idPuesto = idPuesto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}