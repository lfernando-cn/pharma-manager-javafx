package com.github.lfernandocn.pharmamanager.repositorys;

import com.github.lfernandocn.pharmamanager.models.Fornecedor;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FornecedorRepository {
    private static final String FILE_NAME = "fornecedores.csv";
    private static final Path FILE_PATH = Paths.get("data", FILE_NAME);

    public static void salvar(Fornecedor fornecedor) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(String.join(";",
                    fornecedor.getCnpj(),
                    fornecedor.getRazaoSocial(),
                    fornecedor.getTelefone(),
                    fornecedor.getEmail(),
                    fornecedor.getCidade(),
                    fornecedor.getEstado()
            ));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Fornecedor> listarTodos() {
        List<Fornecedor> fornecedores = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) return fornecedores;

        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                if (campos.length == 6) {
                    fornecedores.add(new Fornecedor(
                            campos[0], campos[1], campos[2], campos[3], campos[4], campos[5]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fornecedores;
    }

    public static void salvarTodos(List<Fornecedor> fornecedores) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Fornecedor f : fornecedores) {
                writer.write(String.join(";",
                        f.getCnpj(),
                        f.getRazaoSocial(),
                        f.getTelefone(),
                        f.getEmail(),
                        f.getCidade(),
                        f.getEstado()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Fornecedor> buscarTodos() {
        return listarTodos();
    }
}
