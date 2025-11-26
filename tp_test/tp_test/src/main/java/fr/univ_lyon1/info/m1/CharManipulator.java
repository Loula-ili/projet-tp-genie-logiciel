package fr.univ_lyon1.info.m1.tp_test;

public class CharManipulator implements ICharManipulator {

    @Override
    public String invertOrder(String s) {
        // Renverse la chaîne
        return new StringBuilder(s).reverse().toString();
    }

    @Override
    public String invertCase(String s) {
        // Inverse la casse de chaque caractère
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c); // chiffre ou symbole reste identique
            }
        }
        return sb.toString();
    }
}
