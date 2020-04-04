package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    public static ObservableList<XYChart.Data> receiveXYChartPoints(File file) throws NumberFormatException{
        List<Pair<? extends Number, ? extends Number>> pairs = Model.readXYChartData(file);
        return FXCollections.observableArrayList(pairs.stream().map(pair ->
                new XYChart.Data(pair.getKey(), pair.getValue())).collect(Collectors.toList())
        );
    }

    public static ObservableList<PieChart.Data> receivePieChartPoints(File file) throws NumberFormatException{
        List<Pair<String, Double>> pairs = Model.readPieChartData(file);
        return FXCollections.observableArrayList(pairs.stream().map(pair ->
                new PieChart.Data(pair.getKey(), pair.getValue())).collect(Collectors.toList())
        );
    }
}
