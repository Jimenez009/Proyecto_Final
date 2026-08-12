package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.EmpleadoDAO;
import com.uth.sistema_entrada_salida.modelo.Empleado;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

    // Propiedades para la creación del usuario asociado
    private String username;
    private String password;
    private String rol = "USER";

    @PostConstruct
    public void init() {
        cargarEmpleados();
        prepararNuevoEmpleado();
    }

    public void eliminar(Empleado emp) {
        try {
            boolean exito = empleadoDAO.eliminar(emp.getIdEmpleado());
            if (exito) {
                cargarEmpleados(); // Recarga la lista para que desaparezca de la tabla

                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Éxito", "Empleado eliminado correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el empleado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepararNuevoEmpleado() {
        this.nuevoEmpleado = new Empleado();
        this.nuevoEmpleado.setPuesto(new Puesto());
        this.username = "";
        this.password = "";
        this.rol = "USER";
    }

    public void cargarEmpleados() {
        listaEmpleados = empleadoDAO.listarEmpleados();
    }

    public void guardar() {
        try {
            // Guarda el empleado y crea su usuario asociado mediante la transacción del DAO
            boolean exito = empleadoDAO.guardarConUsuario(nuevoEmpleado, username, password, rol);
            if (exito) {
                cargarEmpleados(); // Actualiza la tabla automáticamente
                prepararNuevoEmpleado(); // Limpia el formulario y campos de usuario

                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Éxito", "Empleado y usuario registrados correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el empleado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}