package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.EmpleadoDAO;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Puesto;

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

    public void eliminar(Empleado emp) {
        try {
            boolean exito = empleadoDAO.eliminar(emp.getIdEmpleado());
            if (exito) {
                cargarEmpleados(); // Recarga la lista para que desaparezca de la tabla

                jakarta.faces.application.FacesMessage msg = new jakarta.faces.application.FacesMessage(
                        jakarta.faces.application.FacesMessage.SEVERITY_INFO, "Éxito", "Empleado eliminado correctamente");
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                jakarta.faces.application.FacesMessage msg = new jakarta.faces.application.FacesMessage(
                        jakarta.faces.application.FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el empleado");
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepararNuevoEmpleado() {
        this.nuevoEmpleado = new Empleado();
        this.nuevoEmpleado.setPuesto(new Puesto());
    }

    public void cargarEmpleados() {
        listaEmpleados = empleadoDAO.listarEmpleados();
    }

    public void guardar() {
        try {
            boolean exito = empleadoDAO.guardar(nuevoEmpleado);
            if (exito) {
                cargarEmpleados(); // Actualiza la tabla automáticamente
                prepararNuevoEmpleado(); // Limpia el formulario

                jakarta.faces.application.FacesMessage msg = new jakarta.faces.application.FacesMessage(
                        jakarta.faces.application.FacesMessage.SEVERITY_INFO, "Éxito", "Empleado registrado correctamente");
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                jakarta.faces.application.FacesMessage msg = new jakarta.faces.application.FacesMessage(
                        jakarta.faces.application.FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el empleado");
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters y Setters
    public List<Empleado> getListaEmpleados() { return listaEmpleados; }
    public void setListaEmpleados(List<Empleado> listaEmpleados) { this.listaEmpleados = listaEmpleados; }

    public Empleado getNuevoEmpleado() { return nuevoEmpleado; }
    public void setNuevoEmpleado(Empleado nuevoEmpleado) { this.nuevoEmpleado = nuevoEmpleado; }
}