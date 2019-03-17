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
            for (ArmenianLetter armenianLetter : ArmenianLetter.values()) {
                if (armenianLetter.getCodering().equals(codering)) {
                    letter = armenianLetter.getLetter();
                    break;
                }
            }
            unicodeString.append(letter);
        }
        return unicodeString.toString();
    }
}