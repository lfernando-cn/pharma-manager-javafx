package com.github.lfernandocn.pharmamanager.controllers;

import com.github.lfernandocn.pharmamanager.models.Fornecedor;
import com.github.lfernandocn.pharmamanager.repositorys.FornecedorRepository;
import com.github.lfernandocn.pharmamanager.utils.CNPJValidador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class FornecedorFormController {

    @FXML private TextField txtCnpj;
    @FXML private TextField txtRazaoSocial;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCidade;
    @FXML private TextField txtEstado;
    @FXML private Label lblMensagem;

    @FXML private TableView<Fornecedor> tabelaFornecedores;
    @FXML private TableColumn<Fornecedor, String> colCnpj;
    @FXML private TableColumn<Fornecedor, String> colRazao;
    @FXML private TableColumn<Fornecedor, String> colTelefone;
    @FXML private TableColumn<Fornecedor, String> colEmail;
    @FXML private TableColumn<Fornecedor, String> colCidade;
    @FXML private TableColumn<Fornecedor, String> colEstado;

    private ObservableList<Fornecedor> lista = FXCollections.observableArrayList();

    private Fornecedor fornecedorSelecionado = null;
    @FXML private VBox containerPrincipal;

    @FXML
    public void initialize() {
        colCnpj.setCellValueFactory(data -> data.getValue().cnpjProperty());
        colRazao.setCellValueFactory(data -> data.getValue().razaoSocialProperty());
        colTelefone.setCellValueFactory(data -> data.getValue().telefoneProperty());
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());
        colCidade.setCellValueFactory(data -> data.getValue().cidadeProperty());
        colEstado.setCellValueFactory(data -> data.getValue().estadoProperty());

        tabelaFornecedores.setItems(lista);
        lista.addAll(FornecedorRepository.buscarTodos());

        tabelaFornecedores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            fornecedorSelecionado = newVal;
            if (newVal != null) {
                preencherCampos(newVal);
            }
        });

        containerPrincipal.setOnMousePressed(event -> {
            // Só limpa se estiver editando e o clique não for na tabela
            if (emModoEdicao() && !tabelaFornecedores.isHover()) {
                limparCampos();
            }
        });
    }

    @FXML
    protected void salvarFornecedor(ActionEvent event) {
        if (!validarCampos()) return;

        Fornecedor fornecedor = new Fornecedor(
                txtCnpj.getText(),
                txtRazaoSocial.getText(),
                txtTelefone.getText(),
                txtEmail.getText(),
                txtCidade.getText(),
                txtEstado.getText()
        );

        FornecedorRepository.salvar(fornecedor);
        lista.setAll(FornecedorRepository.buscarTodos());

        lblMensagem.setText("✅ Fornecedor salvo com sucesso!");
        limparCampos();
    }

    @FXML
    protected void editarFornecedor() {
        if (fornecedorSelecionado != null) {
            if (!validarCampos()) return;

            fornecedorSelecionado.setCnpj(txtCnpj.getText());
            fornecedorSelecionado.setRazaoSocial(txtRazaoSocial.getText());
            fornecedorSelecionado.setTelefone(txtTelefone.getText());
            fornecedorSelecionado.setEmail(txtEmail.getText());
            fornecedorSelecionado.setCidade(txtCidade.getText());
            fornecedorSelecionado.setEstado(txtEstado.getText());

            FornecedorRepository.salvarTodos(lista);
            lblMensagem.setText("✏️ Fornecedor editado!");
            lista.setAll(FornecedorRepository.buscarTodos());
            limparCampos();
        }
    }

    @FXML
    protected void removerFornecedor() {
        if (fornecedorSelecionado != null) {
            lista.remove(fornecedorSelecionado);
            FornecedorRepository.salvarTodos(lista);
            lblMensagem.setText("🗑️ Fornecedor removido!");
            limparCampos();
        }
    }

    private boolean validarCampos() {
        StringBuilder erros = new StringBuilder();

        if (txtCnpj.getText().isBlank()) {
            erros.append("CNPJ é obrigatório.\n");
        } else if (!CNPJValidador.isValid(txtCnpj.getText())) {
            erros.append("CNPJ inválido.\n");
        }

        if (txtRazaoSocial.getText().isBlank()) erros.append("Razão Social é obrigatória.\n");
        if (txtTelefone.getText().isBlank()) erros.append("Telefone é obrigatório.\n");

        if (txtEmail.getText().isBlank()) {
            erros.append("Email é obrigatório.\n");
        } else if (!txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            erros.append("Email inválido.\n");
        }

        if (txtCidade.getText().isBlank()) erros.append("Cidade é obrigatória.\n");
        if (txtEstado.getText().isBlank()) erros.append("Estado é obrigatório.\n");

        if (erros.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Validação");
            alert.setHeaderText("Corrija os seguintes erros:");
            alert.setContentText(erros.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private void preencherCampos(Fornecedor f) {
        txtCnpj.setText(f.getCnpj());
        txtRazaoSocial.setText(f.getRazaoSocial());
        txtTelefone.setText(f.getTelefone());
        txtEmail.setText(f.getEmail());
        txtCidade.setText(f.getCidade());
        txtEstado.setText(f.getEstado());
    }

    private void limparCampos() {
        txtCnpj.clear();
        txtRazaoSocial.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtCidade.clear();
        txtEstado.clear();
        tabelaFornecedores.getSelectionModel().clearSelection();
        fornecedorSelecionado = null;
    }

    private boolean emModoEdicao() {
        return fornecedorSelecionado != null;
    }
}
