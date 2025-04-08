package com.github.lfernandocn.pharmamanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/lfernandocn/pharmamanager/MenuPrincipal.fxml"));
        Scene scene = new Scene(loader.load(),900,600);
        stage.setTitle("Sistema da Farmácia");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
