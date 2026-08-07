package com.uth.sistema_entrada_salida.controlador;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("dashBoardBean")
@ViewScoped
public class DashBoardBean implements Serializable {

    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        crearPieModel();
    }

    private void crearPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(15);
        values.add(3);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(255, 99, 132)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Presentes");
        labels.add("Ausentes");
        data.setLabels(labels);

        pieModel.setData(data);
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }
}