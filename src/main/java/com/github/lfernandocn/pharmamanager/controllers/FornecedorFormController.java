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

    @FXML private TableView<Fornecedor> tabelaFornecedores;
    @FXML private TableColumn<Fornecedor, String> colCnpj;
    @FXML private TableColumn<Fornecedor, String> colRazao;
    @FXML private TableColumn<Fornecedor, String> colTelefone;
    @FXML private TableColumn<Fornecedor, String> colEmail;
    @FXML private TableColumn<Fornecedor, String> colCidade;
    @FXML private TableColumn<Fornecedor, String> colEstado;

    private ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
    private Fornecedor fornecedorSelecionado = null;
    private boolean modoEdicao = false;

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
        });

        containerPrincipal.setOnMousePressed(event -> {
            if (!emModoEdicao() && !tabelaFornecedores.isHover()) {
                limparCampos();
            }
        });
    }

    @FXML
    protected void salvarFornecedor(ActionEvent event) {
        if (!validarCampos()) return;

        String cnpjDigitado = txtCnpj.getText();

        // Verifica se já existe um CNPJ igual na lista (exceto no modo edição e sendo o mesmo fornecedor)
        boolean cnpjDuplicado = lista.stream().anyMatch(f ->
                f.getCnpj().equals(cnpjDigitado) && (!modoEdicao || !f.equals(fornecedorSelecionado))
        );

        if (cnpjDuplicado) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "CNPJ ja cadastrado.");
            return;
        }

        if (modoEdicao && fornecedorSelecionado != null) {
            fornecedorSelecionado.setCnpj(cnpjDigitado);
            fornecedorSelecionado.setRazaoSocial(txtRazaoSocial.getText());
            fornecedorSelecionado.setTelefone(txtTelefone.getText());
            fornecedorSelecionado.setEmail(txtEmail.getText());
            fornecedorSelecionado.setCidade(txtCidade.getText());
            fornecedorSelecionado.setEstado(txtEstado.getText());

            FornecedorRepository.salvarTodos(lista);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Fornecedor editado com sucesso.");
        } else {
            Fornecedor fornecedor = new Fornecedor(
                    cnpjDigitado,
                    txtRazaoSocial.getText(),
                    txtTelefone.getText(),
                    txtEmail.getText(),
                    txtCidade.getText(),
                    txtEstado.getText()
            );
            FornecedorRepository.salvar(fornecedor);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Fornecedor salvo com sucesso.");
        }

        lista.setAll(FornecedorRepository.buscarTodos());
        limparCampos();
        modoEdicao = false;
    }

    @FXML
    protected void editarFornecedor() {
        if (fornecedorSelecionado != null) {
            preencherCampos(fornecedorSelecionado);
            modoEdicao = true;
        }
    }

    @FXML
    protected void removerFornecedor() {
        if (fornecedorSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Remocao");
            alert.setHeaderText("Deseja realmente remover este fornecedor?");
            alert.setContentText("Fornecedor: " + fornecedorSelecionado.getRazaoSocial() +
                    "\nCNPJ: " + fornecedorSelecionado.getCnpj());

            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Nao", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(btnSim, btnNao);

            alert.showAndWait().ifPresent(resposta -> {
                if (resposta == btnSim) {
                    lista.remove(fornecedorSelecionado);
                    FornecedorRepository.salvarTodos(lista);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Fornecedor removido com sucesso.");
                    limparCampos();
                }
            });
        }
    }

    private boolean validarCampos() {
        StringBuilder erros = new StringBuilder();

        if (txtCnpj.getText().isBlank()) {
            erros.append("CNPJ é obrigatorio.\n");
        } else if (!CNPJValidador.isValid(txtCnpj.getText())) {
            erros.append("CNPJ invalido.\n");
        }

        if (txtRazaoSocial.getText().isBlank()) erros.append("Razao Social é obrigatoria.\n");
        if (txtTelefone.getText().isBlank()) erros.append("Telefone é obrigatorio.\n");

        if (txtEmail.getText().isBlank()) {
            erros.append("Email é obrigatorio.\n");
        } else if (!txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            erros.append("Email invalido.\n");
        }

        if (txtCidade.getText().isBlank()) erros.append("Cidade é obrigatoria.\n");
        if (txtEstado.getText().isBlank()) erros.append("Estado é obrigatorio.\n");

        if (erros.length() > 0) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validacao", erros.toString());
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
        modoEdicao = false;
    }

    private boolean emModoEdicao() {
        return modoEdicao;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
