package com.github.lfernandocn.pharmamanager.repositorys;

import com.github.lfernandocn.pharmamanager.models.Fornecedor;
import com.github.lfernandocn.pharmamanager.models.Medicamento;
import com.github.lfernandocn.pharmamanager.utils.CSVUtil;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedicamentoRepository {
    private final List<Medicamento> medicamentos = new ArrayList<>();
    private final File arquivoCSV = new File("data/medicamentos.csv");


    public MedicamentoRepository() {
        carregarMedicamentos();
    }
    public void adicionar(Medicamento medicamento) {
        Optional<Medicamento> existente = buscarPorCodigo(medicamento.getCodigo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Já existe um medicamento com o código: " + medicamento.getCodigo());
        }

        medicamentos.add(medicamento);
        salvarMedicamentos();
    }

    public void remover(String codigo) {
        medicamentos.removeIf(m -> m.getCodigo().equals(codigo));
        salvarMedicamentos();
    }

    public void atualizar(Medicamento medicamentoAtualizado) {
        remover(medicamentoAtualizado.getCodigo());
        medicamentos.add(medicamentoAtualizado);
        salvarMedicamentos();
    }

    public Optional<Medicamento> buscarPorCodigo(String codigo) {
        return medicamentos.stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public List<Medicamento> listarTodos() {
        return new ArrayList<>(medicamentos);
    }

    public void salvarMedicamentos() {
        List<String[]> linhas = medicamentos.stream().map(m -> new String[]{
                m.getCodigo(),
                m.getNome(),
                m.getDescricao(),
                m.getPrincipioAtivo(),
                m.getDataValidade().toString(),
                String.valueOf(m.getQuantidadeEstoque()),
                m.getPreco().toString(),
                String.valueOf(m.isControlado()),
                m.getFornecedor().getCnpj(),
                m.getFornecedor().getRazaoSocial(),
                m.getFornecedor().getTelefone(),
                m.getFornecedor().getEmail(),
                m.getFornecedor().getCidade(),
                m.getFornecedor().getEstado()
        }).collect(Collectors.toList());

        CSVUtil.salvarCSV(arquivoCSV, linhas);
    }

    private void carregarMedicamentos() {
        List<String[]> linhas = CSVUtil.lerCSV(arquivoCSV);

        for (String[] campos : linhas) {
            if (campos.length < 14) continue;

            Fornecedor fornecedor = new Fornecedor(
                    campos[8], campos[9], campos[10],
                    campos[11], campos[12], campos[13]
            );

            Medicamento medicamento = new Medicamento(
                    campos[0], campos[1], campos[2], campos[3],
                    LocalDate.parse(campos[4]),
                    Integer.parseInt(campos[5]),
                    new BigDecimal(campos[6]),
                    Boolean.parseBoolean(campos[7]),
                    fornecedor
            );

            medicamentos.add(medicamento);
        }
    }
}
