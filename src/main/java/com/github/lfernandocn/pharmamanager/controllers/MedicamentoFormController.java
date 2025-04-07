package com.github.lfernandocn.pharmamanager.controllers;

import com.github.lfernandocn.pharmamanager.models.Fornecedor;
import com.github.lfernandocn.pharmamanager.models.Medicamento;
import com.github.lfernandocn.pharmamanager.repositorys.FornecedorRepository;
import com.github.lfernandocn.pharmamanager.repositorys.MedicamentoRepository;
import com.github.lfernandocn.pharmamanager.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MedicamentoFormController {

    @FXML private TextField txtCodigo, txtNome, txtDescricao, txtPrincipioAtivo, txtPreco, txtEstoque;
    @FXML private DatePicker dpValidade;
    @FXML private CheckBox chkControlado;
    @FXML private ComboBox<Fornecedor> cbxFornecedor;

    private final MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
    private final FornecedorRepository fornecedorRepository = new FornecedorRepository();

    private Medicamento medicamentoEmEdicao;
    private Runnable onUpdateCallback;

    @FXML
    public void initialize() {
        carregarFornecedores();
    }

    private void carregarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorRepository.listarTodos();
        cbxFornecedor.setItems(FXCollections.observableArrayList(fornecedores));
    }

    public void setMedicamentoParaEdicao(Medicamento medicamento, Runnable onUpdateCallback) {
        this.medicamentoEmEdicao = medicamento;
        this.onUpdateCallback = onUpdateCallback;

        txtCodigo.setText(medicamento.getCodigo());
        txtNome.setText(medicamento.getNome());
        txtDescricao.setText(medicamento.getDescricao());
        txtPrincipioAtivo.setText(medicamento.getPrincipioAtivo());
        dpValidade.setValue(medicamento.getDataValidade());
        chkControlado.setSelected(medicamento.isControlado());
        txtPreco.setText(medicamento.getPreco().toString());
        txtEstoque.setText(String.valueOf(medicamento.getQuantidadeEstoque()));
        cbxFornecedor.setValue(medicamento.getFornecedor());
    }

    @FXML
    public void salvar() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            String principio = txtPrincipioAtivo.getText().trim();
            String precoStr = txtPreco.getText().trim();
            LocalDate validade = dpValidade.getValue();
            boolean controlado = chkControlado.isSelected();
            Fornecedor fornecedor = cbxFornecedor.getValue();

            if (codigo.isEmpty() || nome.isEmpty() || descricao.isEmpty() || principio.isEmpty() ||
                    precoStr.isEmpty() || validade == null || fornecedor == null) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
            }

            if (!codigo.matches("[A-Za-z0-9]{7}")) {
                throw new IllegalArgumentException("Código deve ter 7 caracteres alfanuméricos.");
            }

            if (nome.length() < 3) {
                throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres.");
            }

            if (validade.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Data de validade inválida ou no passado.");
            }

            BigDecimal preco;
            try {
                preco = new BigDecimal(precoStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Preço inválido.");
            }

            if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero.");
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(txtEstoque.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Estoque deve ser um número inteiro.");
            }

            if (quantidade < 0) {
                throw new IllegalArgumentException("Estoque não pode ser negativo.");
            }

            if (medicamentoEmEdicao == null) {
                if (medicamentoRepository.buscarPorCodigo(codigo).isPresent()) {
                    throw new IllegalArgumentException("Já existe um medicamento com esse código.");
                }

                Medicamento novo = new Medicamento(codigo, nome, descricao, principio, validade, quantidade, preco, controlado, fornecedor);
                medicamentoRepository.adicionar(novo);
                AlertUtil.info("Medicamento salvo com sucesso!");
                limparCampos();
            } else {
                if (!codigo.equalsIgnoreCase(medicamentoEmEdicao.getCodigo())) {
                    if (medicamentoRepository.buscarPorCodigo(codigo).isPresent()) {
                        throw new IllegalArgumentException("Código já está sendo utilizado por outro medicamento.");
                    }
                }

                medicamentoEmEdicao.setCodigo(codigo);
                medicamentoEmEdicao.setNome(nome);
                medicamentoEmEdicao.setDescricao(descricao);
                medicamentoEmEdicao.setPrincipioAtivo(principio);
                medicamentoEmEdicao.setDataValidade(validade);
                medicamentoEmEdicao.setPreco(preco);
                medicamentoEmEdicao.setQuantidadeEstoque(quantidade);
                medicamentoEmEdicao.setControlado(controlado);
                medicamentoEmEdicao.setFornecedor(fornecedor);

                medicamentoRepository.atualizar(medicamentoEmEdicao);
                AlertUtil.info("Medicamento atualizado com sucesso!");

                if (onUpdateCallback != null) {
                    onUpdateCallback.run();
                    txtCodigo.getScene().getWindow().hide();
                }
            }

        } catch (IllegalArgumentException e) {
            AlertUtil.erro("Validação: " + e.getMessage());
        } catch (Exception e) {
            AlertUtil.erro("Erro inesperado: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtCodigo.clear();
        txtNome.clear();
        txtDescricao.clear();
        txtPrincipioAtivo.clear();
        txtPreco.clear();
        txtEstoque.clear();
        dpValidade.setValue(null);
        chkControlado.setSelected(false);
        cbxFornecedor.setValue(null);
    }
}
