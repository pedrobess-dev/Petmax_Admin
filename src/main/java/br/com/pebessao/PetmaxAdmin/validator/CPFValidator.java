package br.com.pebessao.PetmaxAdmin.validator;

public class CPFValidator {

    public static boolean isCPF(String cpf) {

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        } else if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        } else {
            try {
                int soma = 0;
                int peso = 10;

                int dig1;
                for(dig1 = 0; dig1 < 9; ++dig1) {
                    soma += (cpf.charAt(dig1) - 48) * peso--;
                }

                dig1 = 11 - soma % 11;
                if (dig1 >= 10) {
                    dig1 = 0;
                }

                soma = 0;
                peso = 11;

                int dig2;
                for(dig2 = 0; dig2 < 10; ++dig2) {
                    soma += (cpf.charAt(dig2) - 48) * peso--;
                }

                dig2 = 11 - soma % 11;
                if (dig2 >= 10) {
                    dig2 = 0;
                }

                return cpf.charAt(9) - 48 == dig1 && cpf.charAt(10) - 48 == dig2;
            } catch (Exception var5) {
                return false;
            }
        }
    }
}