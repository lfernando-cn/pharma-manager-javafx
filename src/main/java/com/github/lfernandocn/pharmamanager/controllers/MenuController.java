package com.github.lfernandocn.pharmamanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MenuController {

    @FXML
    private StackPane conteudoCentral;

    public void abrirFormulario() {
        carregarTela("/com/example/pharmamanager/FormularioMedicamento.fxml");
    }

    public void abrirLista() {
        carregarTela("/com/example/pharmamanager/ListaMedicamentos.fxml");
    }

    public void abrirRelatorio() {
        carregarTela("/com/example/pharmamanager/Relatorio.fxml");
    }

    public void abrirFormularioFornecedor(){
        carregarTela("/com/example/pharmamanager/FormularioFornecedor.fxml");
    }

    private void carregarTela(String caminhoFXML) {
        try {
            Node tela = FXMLLoader.load(getClass().getResource(caminhoFXML));
            conteudoCentral.getChildren().setAll(tela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
