package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.EmpleadoDAO;
import com.uth.sistema_entrada_salida.modelo.Empleado;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("empleadoBean")
@ViewScoped
public class EmpleadoBean implements Serializable {

    private List<Empleado> listaEmpleados;
    private Empleado nuevoEmpleado = new Empleado();
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    @PostConstruct
    public void init() {
        cargarEmpleados();
    }

    public void cargarEmpleados() {
        listaEmpleados = empleadoDAO.listarEmpleados();
    }

    // Getters y Setters
    public List<Empleado> getListaEmpleados() { return listaEmpleados; }
    public Empleado getNuevoEmpleado() { return nuevoEmpleado; }
    public void setNuevoEmpleado(Empleado nuevoEmpleado) { this.nuevoEmpleado = nuevoEmpleado; }
}