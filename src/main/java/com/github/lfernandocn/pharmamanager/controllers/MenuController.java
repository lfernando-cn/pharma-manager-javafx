package com.github.lfernandocn.pharmamanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MenuController {

    @FXML
    private StackPane conteudoCentral;

    public void abrirFormulario() {
        carregarTela("/com/github/lfernandocn/pharmamanager/FormularioMedicamento.fxml");
    }

    public void abrirLista() {
        carregarTela("/com/github/lfernandocn/pharmamanager/ListaMedicamentos.fxml");
    }

    public void abrirRelatorio() {
        carregarTela("/com/github/lfernandocn/pharmamanager/Relatorio.fxml");
    }

    public void abrirFormularioFornecedor(){
        carregarTela("/com/github/lfernandocn/pharmamanager/FormularioFornecedor.fxml");
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
