package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.UsuarioDAO;
import com.uth.sistema_entrada_salida.modelo.Usuario;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Usuario usuarioLogueado;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String iniciarSesion() {
        usuarioLogueado = usuarioDAO.validarLogin(username, password);

        if (usuarioLogueado != null) {
            String rol = usuarioLogueado.getRol();

            if ("ADMIN".equalsIgnoreCase(rol) || "ADMINISTRADOR".equalsIgnoreCase(rol)) {
                return "/admin/dashboard.xhtml?faces-redirect=true";
            } else {
                return "/usuario/marcaciones.xhtml?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos"));
            return null;
        }
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean esAdmin() {
        return usuarioLogueado != null &&
                ("ADMIN".equalsIgnoreCase(usuarioLogueado.getRol()) || "ADMINISTRADOR".equalsIgnoreCase(usuarioLogueado.getRol()));
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Usuario getUsuarioLogueado() { return usuarioLogueado; }
    public void setUsuarioLogueado(Usuario usuarioLogueado) { this.usuarioLogueado = usuarioLogueado; }
}