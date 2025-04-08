package com.github.lfernandocn.pharmamanager.controllers;

import com.github.lfernandocn.pharmamanager.models.Medicamento;
import com.github.lfernandocn.pharmamanager.repositorys.MedicamentoRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioController {

    @FXML private ListView<String> listRelatorio;

    private final MedicamentoRepository repository = new MedicamentoRepository();

    @FXML
    public void vencendoEm30Dias() {
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<String> resultado = repository.listarTodos().stream()
                .filter(m -> !m.getDataValidade().isBefore(hoje) && m.getDataValidade().isBefore(hoje.plusDays(30)))
                .sorted(Comparator.comparing(Medicamento::getDataValidade))
                .map(m -> m.getNome() + " vence em " + m.getDataValidade().format(formatter))
                .collect(Collectors.toList());

        listRelatorio.setItems(FXCollections.observableArrayList(resultado));
    }
    @FXML
    public void estoqueBaixo() {
        List<String> resultado = repository.listarTodos().stream()
                .filter(m -> m.getQuantidadeEstoque() < 5)
                .map(m -> m.getNome() + " - Estoque: " + m.getQuantidadeEstoque())
                .collect(Collectors.toList());

        listRelatorio.setItems(FXCollections.observableArrayList(resultado));
    }

    @FXML
    public void valorTotalPorFornecedor() {
        Map<String, Double> totalPorFornecedor = repository.listarTodos().stream()
                .collect(Collectors.groupingBy(
                        m -> m.getFornecedor().getRazaoSocial(),
                        Collectors.summingDouble(m -> m.getPreco().doubleValue() * m.getQuantidadeEstoque())
                ));

        List<String> resultado = totalPorFornecedor.entrySet().stream()
                .map(e -> e.getKey() + ": R$ " + String.format("%.2f", e.getValue()))
                .collect(Collectors.toList());

        listRelatorio.setItems(FXCollections.observableArrayList(resultado));
    }

    @FXML
    public void controlados() {
        Map<Boolean, List<Medicamento>> mapa = repository.listarTodos().stream()
                .collect(Collectors.groupingBy(Medicamento::isControlado));

        List<String> resultado = mapa.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(m -> (e.getKey() ? "[CONTROLADO] " : "[NÃO CONTROLADO] ") + m.getNome()))
                .collect(Collectors.toList());

        listRelatorio.setItems(FXCollections.observableArrayList(resultado));
    }
}
