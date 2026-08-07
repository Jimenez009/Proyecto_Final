package com.uth.sistema_entrada_salida.modelo;

import java.util.Date;

public class Marcacion {
    private int idMarcacion;
    private Empleado empleado;
    private Date fechaHora;
    private String tipo;

    public Marcacion() {}

    public Marcacion(int idMarcacion, Empleado empleado, Date fechaHora, String tipo) {
        this.idMarcacion = idMarcacion;
        this.empleado = empleado;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
    }

    public int getIdMarcacion() { return idMarcacion; }
    public void setIdMarcacion(int idMarcacion) { this.idMarcacion = idMarcacion; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}