package com.uth.sistema_entrada_salida.controlador;

import com.uth.sistema_entrada_salida.dao.EmpleadoDAO;
import com.uth.sistema_entrada_salida.dao.MarcacionDAO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

    private int totalEmpleados;
    private int empleadosPresentes;
    private int empleadosAusentes;

    private MarcacionDAO marcacionDAO = new MarcacionDAO();
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO(); // O tu servicio correspondiente

    @PostConstruct
    public void init() {
        actualizarMetricas();
    }

    public void actualizarMetricas() {
        // 1. Obtener el total de empleados registrados en el sistema
        this.totalEmpleados = empleadoDAO.contarEmpleados(); // O el método que uses para el total

        // 2. Obtener los presentes de hoy usando el nuevo método del DAO
        this.empleadosPresentes = marcacionDAO.contarEmpleadosPresentesHoy();

        // 3. Calcular los ausentes
        this.empleadosAusentes = Math.max(0, this.totalEmpleados - this.empleadosPresentes);
    }

    // Getters y Setters obligatorios para JSF
    public int getTotalEmpleados() { return totalEmpleados; }
    public void setTotalEmpleados(int totalEmpleados) { this.totalEmpleados = totalEmpleados; }

    public int getEmpleadosPresentes() { return empleadosPresentes; }
    public void setEmpleadosPresentes(int empleadosPresentes) { this.empleadosPresentes = empleadosPresentes; }

    public int getEmpleadosAusentes() { return empleadosAusentes; }
    public void setEmpleadosAusentes(int empleadosAusentes) { this.empleadosAusentes = empleadosAusentes; }
}