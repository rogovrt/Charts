package main.java;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.UncheckedIOException;

public class View extends BorderPane {
    private XYChart xyChart;
    private PieChart pieChart;
    private HBox hBox;

    public View(Stage stage) {
        hBox = new HBox(100);
        initHBox(stage);
        setBottom(hBox);
    }

    private void initHBox(Stage stage) {
        hBox.setPadding(new Insets(25, 100, 25, 100));
        hBox.setMinSize(500, 100);

        ChoiceBox cb = new ChoiceBox();
        cb.getItems().add("Line Chart");
        cb.getItems().add("Pie Chart");
        cb.getItems().add("Area Chart");
        cb.getItems().add("Bubble Chart");
        cb.setMinSize(100, 50);
        cb.setTooltip(new Tooltip("Select type of chart"));

        Button openFile = new Button("Open file");
        openFile.setMinSize(100, 50);
        openFile.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(stage);
            if (file != null) {
                try {
                    if (cb.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Choose chart type!");
                        alert.showAndWait();
                    }
                    else {
                        if ((String) cb.getValue() == "Pie Chart")
                            initPieChart(file);
                        else
                            initXYChart(file, (String) cb.getValue());
                    }
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect data format!");
                    alert.showAndWait();
                }
                catch (UncheckedIOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("File can't be written!");
                    alert.showAndWait();
                }
            }
        });
        hBox.getChildren().addAll(openFile, cb);
    }

    private void initXYChart(File file, String type) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        switch (type) {
            case "Line Chart" :
                xyChart = new LineChart<>(xAxis, yAxis);
                break;
            case "Area Chart" :
                xyChart = new AreaChart<>(xAxis, yAxis);
                break;
        }
        XYChart.Series series = new XYChart.Series();
        ObservableList<XYChart.Data> data = Controller.receiveXYChartPoints(file);
        series.getData().addAll(data);
        xyChart.getData().add(series);
        xyChart.setMinSize(500, 400);
        xyChart.setLegendVisible(false);
        setCenter(xyChart);
    }

    private void initPieChart(File file) {
        ObservableList<PieChart.Data> data = Controller.receivePieChartPoints(file);
        pieChart = new PieChart(data);
        pieChart.setMinSize(500, 400);
        pieChart.setLegendVisible(false);
        setCenter(pieChart);
    }
}
