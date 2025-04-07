package com.github.lfernandocn.pharmamanager.utils;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {

    public static void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void erro(String mensagem) {
        mostrarAlerta("Erro", mensagem, AlertType.ERROR);
    }

    public static void info(String mensagem) {
        mostrarAlerta("Informação", mensagem, AlertType.INFORMATION);
    }

    public static void aviso(String mensagem) {
        mostrarAlerta("Aviso", mensagem, AlertType.WARNING);
    }
}
