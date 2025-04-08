package com.github.lfernandocn.pharmamanager.controllers;

import com.github.lfernandocn.pharmamanager.models.Medicamento;
import com.github.lfernandocn.pharmamanager.repositorys.MedicamentoRepository;
import com.github.lfernandocn.pharmamanager.utils.AlertUtil;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ListMedicamentoController {

    @FXML private TextField txtCodigoBusca;

    @FXML private TableView<Medicamento> tableMedicamentos;
    @FXML private TableColumn<Medicamento, String> colCodigo;
    @FXML private TableColumn<Medicamento, String> colNome;
    @FXML private TableColumn<Medicamento, String> colPrincipioAtivo;
    @FXML private TableColumn<Medicamento, String> colDescricao;
    @FXML private TableColumn<Medicamento, String> colFornecedor;
    @FXML private TableColumn<Medicamento, Integer> colQuantidade;

    @FXML private VBox containerPrincipal; // ← Adicionado para detectar cliques fora da tabela

    private final MedicamentoRepository repository = new MedicamentoRepository();

    @FXML
    public void initialize() {
        // Associa as colunas às propriedades do modelo
        colCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colPrincipioAtivo.setCellValueFactory(cellData -> cellData.getValue().principioAtivoProperty());
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colFornecedor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFornecedor().getRazaoSocial()));
        colQuantidade.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantidadeEstoque()));

        atualizarLista();

        // → Desmarca a seleção se clicar fora da tabela
        containerPrincipal.setOnMousePressed(event -> {
            if (!tableMedicamentos.isHover()) {
                tableMedicamentos.getSelectionModel().clearSelection();
                txtCodigoBusca.clear();
            }
        });
    }

    private void atualizarLista() {
        tableMedicamentos.setItems(FXCollections.observableArrayList(repository.listarTodos()));
    }

    @FXML
    public void buscar() {
        String codigo = txtCodigoBusca.getText().trim();
        if (codigo.isEmpty()) {
            AlertUtil.aviso("Digite um código para buscar.");
            return;
        }

        repository.buscarPorCodigo(codigo).ifPresentOrElse(
                m -> {
                    atualizarLista(); // Garante a lista completa

                    for (int i = 0; i < tableMedicamentos.getItems().size(); i++) {
                        Medicamento med = tableMedicamentos.getItems().get(i);
                        if (med.getCodigo().equals(m.getCodigo())) {
                            tableMedicamentos.getSelectionModel().select(i); // Marca como selecionado
                            tableMedicamentos.scrollTo(i); // Rola até o item
                            tableMedicamentos.requestFocus();
                            break;
                        }
                    }
                },
                () -> AlertUtil.aviso("Medicamento não encontrado.")
        );
    }

    @FXML
    public void excluirSelecionado() {
        Medicamento selecionado = tableMedicamentos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de Exclusão");
            confirmacao.setHeaderText("Tem certeza que deseja excluir este medicamento?");
            confirmacao.setContentText("Código: " + selecionado.getCodigo() + "\nNome: " + selecionado.getNome());

            ButtonType sim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
            ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacao.getButtonTypes().setAll(sim, nao);

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == sim) {
                    repository.remover(selecionado.getCodigo());
                    atualizarLista();
                    AlertUtil.info("Medicamento removido com sucesso.");
                }
            });
        } else {
            AlertUtil.aviso("Selecione um medicamento para remover.");
        }
    }

    @FXML
    public void editarSelecionado() {
        Medicamento selecionado = tableMedicamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            AlertUtil.aviso("Selecione um medicamento para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/lfernandocn/pharmamanager/MedicamentoEditForm.fxml"));

            Parent root = loader.load();

            MedicamentoFormController controller = loader.getController();
            controller.setMedicamentoParaEdicao(selecionado, () -> {
                repository.salvarMedicamentos();
                atualizarLista();
                tableMedicamentos.refresh();
            });

            Stage stage = new Stage();
            stage.setTitle("Editar Medicamento");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.erro("Erro ao abrir a janela de edição.");
        }
    }

}
