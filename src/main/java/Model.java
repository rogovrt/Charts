package main.java;

import javafx.util.Pair;
import main.java.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    public static List<Pair<? extends Number, ? extends Number>> readXYChartData(File file) {
        List<String> lines = FileUtils.readAll(file);
        return lines.stream().map(line -> {
            String[] splitted = line.split(" ; ");
            return new Pair<>(Double.valueOf(splitted[0]), Double.valueOf(splitted[1]));
        }).collect(Collectors.toList());
    }

    public static List<Pair<String, Double>> readPieChartData(File file) {
        List<String> lines = FileUtils.readAll(file);
        return lines.stream().map(line -> {
            String[] splitted = line.split(" ; ");
            return new Pair<>(String.valueOf(splitted[0]), Double.valueOf(splitted[1]));
        }).collect(Collectors.toList());
    }
}
