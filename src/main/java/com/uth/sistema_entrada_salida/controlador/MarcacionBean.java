package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.MarcacionDAO;
import com.uth.sistema_entrada_salida.modelo.Marcacion;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("marcacionBean")
@ViewScoped
public class MarcacionBean implements Serializable {

    private List<Marcacion> listaMarcaciones;
    private int idEmpleadoSel;
    private String tipoMarcacion = "ENTRADA";
    private MarcacionDAO marcacionDAO = new MarcacionDAO();

    @PostConstruct
    public void init() {
        cargarMarcaciones();
    }

    public void cargarMarcaciones() {
        listaMarcaciones = marcacionDAO.listarMarcaciones();
    }

    public void registrar() {
        if (idEmpleadoSel > 0) {
            if (marcacionDAO.registrarMarcacion(idEmpleadoSel, tipoMarcacion)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Marcación Exitosa", "Registro guardado correctamente."));
                cargarMarcaciones();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar el registro."));
            }
        }
    }

    // Getters y Setters
    public List<Marcacion> getListaMarcaciones() { return listaMarcaciones; }
    public int getIdEmpleadoSel() { return idEmpleadoSel; }
    public void setIdEmpleadoSel(int idEmpleadoSel) { this.idEmpleadoSel = idEmpleadoSel; }
    public String getTipoMarcacion() { return tipoMarcacion; }
    public void setTipoMarcacion(String tipoMarcacion) { this.tipoMarcacion = tipoMarcacion; }
}