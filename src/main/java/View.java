package main.java;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class View extends BorderPane {
    private XYChart xyChart;
    private PieChart pieChart;
    private HBox hBox;
    private Controller controller;

    public View(Stage stage) {
        hBox = new HBox(100);
        initHBox(stage);
        setBottom(hBox);
    }

    private void initHBox(Stage stage) {
        hBox.setPadding(new Insets(25, 100, 25, 100));
        hBox.setMinSize(600, 100);

        RadioButton radioButtonLine = new RadioButton("Line Chart");
        radioButtonLine.setSelected(true);
        RadioButton radioButtonPie = new RadioButton("Pie Chart");
        RadioButton radioButtonArea = new RadioButton("Area Chart");
        RadioButton radioButtonBubble = new RadioButton("Bubble Chart");
        ToggleGroup group = new ToggleGroup();
        radioButtonLine.setToggleGroup(group);
        radioButtonPie.setToggleGroup(group);
        radioButtonArea.setToggleGroup(group);
        radioButtonBubble.setToggleGroup(group);
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.getChildren().addAll(radioButtonLine, radioButtonPie, radioButtonArea, radioButtonBubble);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                initChart(group);
            }
        });

        Button openFile = new Button("Open file");
        openFile.setMinSize(100, 50);
        openFile.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(stage);
            if (file != null)
                controller = new Controller(file);
            initChart(group);
        });
        hBox.getChildren().addAll(openFile, vBox);
    }

    private void initChart(ToggleGroup group) {
        if ((group.getSelectedToggle() != null) && (controller != null)) {
            RadioButton selectedButton = (RadioButton) group.getSelectedToggle();
            String type = selectedButton.getText();
            try {
                if (type == "Pie Chart")
                    initPieChart();
                else
                    initXYChart(type);
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect data format!");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Choose chart type and correct file!");
            alert.showAndWait();
        }
    }

    private void initXYChart(String type) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        switch (type) {
            case "Line Chart" :
                xyChart = new LineChart<>(xAxis, yAxis);
                break;
            case "Area Chart" :
                xyChart = new AreaChart<>(xAxis, yAxis);
                break;
            case "Bubble Chart" :
                xyChart = new BubbleChart<>(xAxis, yAxis);
        }
        XYChart.Series series = new XYChart.Series();
        ObservableList<XYChart.Data> data = controller.receiveXYChartPoints();
        series.getData().addAll(data);
        xyChart.getData().add(series);
        xyChart.setMinSize(500, 400);
        xyChart.setLegendVisible(false);
        setCenter(xyChart);
    }

    private void initPieChart() {
        ObservableList<PieChart.Data> data = controller.receivePieChartPoints();
        pieChart = new PieChart(data);
        pieChart.setMinSize(500, 400);
        pieChart.setLegendVisible(false);
        setCenter(pieChart);
    }
}
