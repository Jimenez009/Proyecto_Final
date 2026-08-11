package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.MarcacionDAO;
import com.uth.sistema_entrada_salida.modelo.Marcacion;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("marcacionBean")
@ViewScoped
public class MarcacionBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private List<Marcacion> listaMarcaciones;
    private List<Marcacion> listaMarcacionesUsuario;
    private int idEmpleadoSel;
    private String tipoMarcacion = "ENTRADA";
    private MarcacionDAO marcacionDAO = new MarcacionDAO();

    @PostConstruct
    public void init() {
        cargarMarcaciones();
        cargarMarcacionesUsuario();
    }

    public void cargarMarcaciones() {
        listaMarcaciones = marcacionDAO.listarMarcaciones();
    }

    public void cargarMarcacionesUsuario() {
        if (loginBean != null && loginBean.getUsuarioLogueado() != null && loginBean.getUsuarioLogueado().getEmpleado() != null) {
            int idEmpleado = loginBean.getUsuarioLogueado().getEmpleado().getIdEmpleado();
            listaMarcacionesUsuario = marcacionDAO.listarMarcacionesPorEmpleado(idEmpleado);
        }
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
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un empleado."));
        }
    }

    public void registrarEntrada() {
        registrarMarcacionEmpleado("ENTRADA");
    }

    public void registrarSalida() {
        registrarMarcacionEmpleado("SALIDA");
    }

    private void registrarMarcacionEmpleado(String tipo) {
        if (loginBean != null && loginBean.getUsuarioLogueado() != null && loginBean.getUsuarioLogueado().getEmpleado() != null) {
            int idEmpleado = loginBean.getUsuarioLogueado().getEmpleado().getIdEmpleado();

            if (idEmpleado > 0) {
                if (marcacionDAO.registrarMarcacion(idEmpleado, tipo)) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Marcación de " + tipo + " registrada correctamente."));
                    cargarMarcaciones();
                    cargarMarcacionesUsuario();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la marcación."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario no tiene un ID de empleado válido."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay una sesión activa de empleado."));
        }
    }

    // Getters y Setters
    public List<Marcacion> getListaMarcaciones() { return listaMarcaciones; }

    public List<Marcacion> getListaMarcacionesUsuario() { return listaMarcacionesUsuario; }
    public void setListaMarcacionesUsuario(List<Marcacion> listaMarcacionesUsuario) { this.listaMarcacionesUsuario = listaMarcacionesUsuario; }

    public int getIdEmpleadoSel() { return idEmpleadoSel; }
    public void setIdEmpleadoSel(int idEmpleadoSel) { this.idEmpleadoSel = idEmpleadoSel; }

    public String getTipoMarcacion() { return tipoMarcacion; }
    public void setTipoMarcacion(String tipoMarcacion) { this.tipoMarcacion = tipoMarcacion; }

    public LoginBean getLoginBean() { return loginBean; }
    public void setLoginBean(LoginBean loginBean) { this.loginBean = loginBean; }
}