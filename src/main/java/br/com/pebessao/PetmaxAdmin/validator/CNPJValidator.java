package br.com.pebessao.PetmaxAdmin.validator;

public class CNPJValidator {

    public static boolean isCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) {
            return false;
        } else if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        } else {
            try {
                int soma = 0;
                int[] peso1 = new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

                int dig1;
                for(dig1 = 0; dig1 < 12; ++dig1) {
                    soma += (cnpj.charAt(dig1) - 48) * peso1[dig1];
                }

                dig1 = soma % 11;
                dig1 = dig1 < 2 ? 0 : 11 - dig1;
                soma = 0;
                int[] peso2 = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

                int dig2;
                for(dig2 = 0; dig2 < 13; ++dig2) {
                    soma += (cnpj.charAt(dig2) - 48) * peso2[dig2];
                }

                dig2 = soma % 11;
                dig2 = dig2 < 2 ? 0 : 11 - dig2;
                return cnpj.charAt(12) - 48 == dig1 && cnpj.charAt(13) - 48 == dig2;
            } catch (Exception var6) {
                return false;
            }
        }
    }
}