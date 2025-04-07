package com.github.lfernandocn.pharmamanager.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static List<String[]> lerCSV(File file) {
        List<String[]> linhas = new ArrayList<>();
        if (!file.exists()) return linhas;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha.split(";"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    public static void salvarCSV(File file, List<String[]> linhas) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (String[] campos : linhas) {
                bw.write(String.join(";", campos));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
