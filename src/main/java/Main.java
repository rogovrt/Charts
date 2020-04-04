package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(new View(primaryStage), 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chart Application");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
