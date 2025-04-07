package com.github.lfernandocn.pharmamanager.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fornecedor {
    private final StringProperty cnpj = new SimpleStringProperty();
    private final StringProperty razaoSocial = new SimpleStringProperty();
    private final StringProperty telefone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty cidade = new SimpleStringProperty();
    private final StringProperty estado = new SimpleStringProperty();

    public Fornecedor(String cnpj, String razaoSocial, String telefone, String email, String cidade, String estado) {
        this.cnpj.set(cnpj);
        this.razaoSocial.set(razaoSocial);
        this.telefone.set(telefone);
        this.email.set(email);
        this.cidade.set(cidade);
        this.estado.set(estado);
    }


    public String getCnpj() { return cnpj.get(); }
    public void setCnpj(String value) { cnpj.set(value); }
    public StringProperty cnpjProperty() { return cnpj; }

    public String getRazaoSocial() { return razaoSocial.get(); }
    public void setRazaoSocial(String value) { razaoSocial.set(value); }
    public StringProperty razaoSocialProperty() { return razaoSocial; }

    public String getTelefone() { return telefone.get(); }
    public void setTelefone(String value) { telefone.set(value); }
    public StringProperty telefoneProperty() { return telefone; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    public String getCidade() { return cidade.get(); }
    public void setCidade(String value) { cidade.set(value); }
    public StringProperty cidadeProperty() { return cidade; }

    public String getEstado() { return estado.get(); }
    public void setEstado(String value) { estado.set(value); }
    public StringProperty estadoProperty() { return estado; }

    public String toString() {
        return getRazaoSocial();
    }
}
