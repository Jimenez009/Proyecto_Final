package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.PuestoDAO;
import com.uth.sistema_entrada_salida.modelo.Puesto;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("puestoBean")
@ViewScoped
public class PuestoBean implements Serializable {

    private List<Puesto> listaPuestos;
    private Puesto nuevoPuesto = new Puesto();
    private PuestoDAO puestoDAO = new PuestoDAO();

    @PostConstruct
    public void init() {
        cargarPuestos();
    }

    public void cargarPuestos() {
        listaPuestos = puestoDAO.listarPuestos();
    }

    public void eliminar(Puesto p) {
        try {
            boolean exito = puestoDAO.eliminar(p.getIdPuesto()); // <- Uso correcto del getter
            if (exito) {
                cargarPuestos(); // Método que recarga la lista de puestos

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Puesto eliminado correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el puesto");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarPuesto() {
        if (puestoDAO.guardar(nuevoPuesto)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Puesto registrado correctamente"));
            nuevoPuesto = new Puesto();
            cargarPuestos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el puesto"));
        }
    }

    // Getters y Setters
    public List<Puesto> getListaPuestos() { return listaPuestos; }
    public Puesto getNuevoPuesto() { return nuevoPuesto; }
    public void setNuevoPuesto(Puesto nuevoPuesto) { this.nuevoPuesto = nuevoPuesto; }
}