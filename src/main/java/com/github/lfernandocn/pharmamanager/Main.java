package com.github.lfernandocn.pharmamanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmamanager/MenuPrincipal.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Sistema da Farmácia");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
