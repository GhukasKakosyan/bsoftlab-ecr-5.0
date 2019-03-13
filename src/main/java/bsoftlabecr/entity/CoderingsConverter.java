package bsoftlabecr.entity;

public class CoderingsConverter {
    public static String convert(String coderings) {
        int lengthCoderings = coderings.length();
        int indexLetter;
        StringBuilder unicodeString = new StringBuilder();
        for (indexLetter = 0; indexLetter < lengthCoderings; indexLetter = indexLetter + 6) {
            int startIndex = indexLetter + 2;
            int endIndex = indexLetter + 6;
            String codering = coderings.substring(startIndex, endIndex);
            String letter = "";
            for (Alphabet alphabet : Alphabet.values()) {
                if (alphabet.getCodering().equals(codering)) {
                    letter = alphabet.getLetter();
                    break;
                }
            }
            unicodeString.append(letter);
        }
        return unicodeString.toString();
    }
}