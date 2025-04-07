package com.github.lfernandocn.pharmamanager.utils;

public class CNPJValidador {
    public static boolean isValid(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++)
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];

            int digito1 = 11 - (soma % 11);
            digito1 = digito1 >= 10 ? 0 : digito1;

            soma = 0;
            for (int i = 0; i < 13; i++)
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];

            int digito2 = 11 - (soma % 11);
            digito2 = digito2 >= 10 ? 0 : digito2;

            return cnpj.endsWith("" + digito1 + digito2);
        } catch (Exception e) {
            return false;
        }
    }
}
