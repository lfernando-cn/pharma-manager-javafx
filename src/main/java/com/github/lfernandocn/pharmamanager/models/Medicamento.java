package com.github.lfernandocn.pharmamanager.models;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Medicamento {
    private final StringProperty codigo = new SimpleStringProperty();
    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty descricao = new SimpleStringProperty();
    private final StringProperty principioAtivo = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dataValidade = new SimpleObjectProperty<>();
    private final IntegerProperty quantidadeEstoque = new SimpleIntegerProperty();
    private final ObjectProperty<BigDecimal> preco = new SimpleObjectProperty<>();
    private final BooleanProperty controlado = new SimpleBooleanProperty();
    private final ObjectProperty<Fornecedor> fornecedor = new SimpleObjectProperty<>();

    public Medicamento() {}

    public Medicamento(String codigo, String nome, String descricao, String principioAtivo,
                       LocalDate dataValidade, int quantidadeEstoque, BigDecimal preco,
                       boolean controlado, Fornecedor fornecedor) {
        this.codigo.set(codigo);
        this.nome.set(nome);
        this.descricao.set(descricao);
        this.principioAtivo.set(principioAtivo);
        this.dataValidade.set(dataValidade);
        this.quantidadeEstoque.set(quantidadeEstoque);
        this.preco.set(preco);
        this.controlado.set(controlado);
        this.fornecedor.set(fornecedor);
    }


    public String getCodigo() { return codigo.get(); }
    public void setCodigo(String codigo) { this.codigo.set(codigo); }
    public StringProperty codigoProperty() { return codigo; }

    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }
    public StringProperty nomeProperty() { return nome; }

    public String getDescricao() { return descricao.get(); }
    public void setDescricao(String descricao) { this.descricao.set(descricao); }
    public StringProperty descricaoProperty() { return descricao; }

    public String getPrincipioAtivo() { return principioAtivo.get(); }
    public void setPrincipioAtivo(String principioAtivo) { this.principioAtivo.set(principioAtivo); }
    public StringProperty principioAtivoProperty() { return principioAtivo; }

    public LocalDate getDataValidade() { return dataValidade.get(); }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade.set(dataValidade); }
    public ObjectProperty<LocalDate> dataValidadeProperty() { return dataValidade; }

    public int getQuantidadeEstoque() { return quantidadeEstoque.get(); }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque.set(quantidadeEstoque); }
    public IntegerProperty quantidadeEstoqueProperty() { return quantidadeEstoque; }

    public BigDecimal getPreco() { return preco.get(); }
    public void setPreco(BigDecimal preco) { this.preco.set(preco); }
    public ObjectProperty<BigDecimal> precoProperty() { return preco; }

    public boolean isControlado() { return controlado.get(); }
    public void setControlado(boolean controlado) { this.controlado.set(controlado); }
    public BooleanProperty controladoProperty() { return controlado; }

    public Fornecedor getFornecedor() { return fornecedor.get(); }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor.set(fornecedor); }
    public ObjectProperty<Fornecedor> fornecedorProperty() { return fornecedor; }


    @Override
    public String toString() {
        String fornecedorNome = fornecedor.get() != null ? fornecedor.get().getRazaoSocial() : "Sem fornecedor";
        return nome.get() + " - " + principioAtivo.get() +
                " (Código: " + codigo.get() + ") | Fornecedor: " +
                fornecedorNome + " | Quantidade: " + quantidadeEstoque.get();
    }
}
