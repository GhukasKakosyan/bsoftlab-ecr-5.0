package BSOFTLABECR.entity;

public class CoderingConverter {

    public static String convertCoderingStrToUnicodeStr(String coderingString) {
        int lengthCoderingString = coderingString.length();
        int indexCharString;
        int startIndex;
        int endIndex;
        String coderingChar;
        String unicodeChar;
        StringBuilder unicodeString = new StringBuilder();
        for (indexCharString = 0; indexCharString < lengthCoderingString; indexCharString = indexCharString + 6) {
            startIndex = indexCharString + 2;
            endIndex = indexCharString + 6;
            coderingChar = coderingString.substring(startIndex, endIndex);
            unicodeChar = convertCoderingCharToUnicodeChar(coderingChar);
            unicodeString.append(unicodeChar);
        }
        return unicodeString.toString();
    }

    private static String convertCoderingCharToUnicodeChar(String coderingChar) {
        String unicodeChar;
        switch (coderingChar) {
            case "0531":
                unicodeChar = "Ա";
                break;
            case "0561":
                unicodeChar = "ա";
                break;
            case "0532":
                unicodeChar = "Բ";
                break;
            case "0562":
                unicodeChar = "բ";
                break;
            case "0533":
                unicodeChar = "Գ";
                break;
            case "0563":
                unicodeChar = "գ";
                break;
            case "0534":
                unicodeChar = "Դ";
                break;
            case "0564":
                unicodeChar = "դ";
                break;
            case "0535":
                unicodeChar = "Ե";
                break;
            case "0565":
                unicodeChar = "ե";
                break;
            case "0536":
                unicodeChar = "Զ";
                break;
            case "0566":
                unicodeChar = "զ";
                break;
            case "0537":
                unicodeChar = "Է";
                break;
            case "0567":
                unicodeChar = "է";
                break;
            case "0538":
                unicodeChar = "Ը";
                break;
            case "0568":
                unicodeChar = "ը";
                break;
            case "0539":
                unicodeChar = "Թ";
                break;
            case "0569":
                unicodeChar = "թ";
                break;
            case "053A":
                unicodeChar = "Ժ";
                break;
            case "056A":
                unicodeChar = "ժ";
                break;
            case "053B":
                unicodeChar = "Ի";
                break;
            case "056B":
                unicodeChar = "ի";
                break;
            case "053C":
                unicodeChar = "Լ";
                break;
            case "056C":
                unicodeChar = "լ";
                break;
            case "053D":
                unicodeChar = "Խ";
                break;
            case "056D":
                unicodeChar = "խ";
                break;
            case "053E":
                unicodeChar = "Ծ";
                break;
            case "056E":
                unicodeChar = "ծ";
                break;
            case "053F":
                unicodeChar = "Կ";
                break;
            case "056F":
                unicodeChar = "կ";
                break;
            case "0540":
                unicodeChar = "Հ";
                break;
            case "0570":
                unicodeChar = "հ";
                break;
            case "0541":
                unicodeChar = "Ձ";
                break;
            case "0571":
                unicodeChar = "ձ";
                break;
            case "0542":
                unicodeChar = "Ղ";
                break;
            case "0572":
                unicodeChar = "ղ";
                break;
            case "0543":
                unicodeChar = "Ճ";
                break;
            case "0573":
                unicodeChar = "ճ";
                break;
            case "0544":
                unicodeChar = "Մ";
                break;
            case "0574":
                unicodeChar = "մ";
                break;
            case "0545":
                unicodeChar = "Յ";
                break;
            case "0575":
                unicodeChar = "յ";
                break;
            case "0546":
                unicodeChar = "Ն";
                break;
            case "0576":
                unicodeChar = "ն";
                break;
            case "0547":
                unicodeChar = "Շ";
                break;
            case "0577":
                unicodeChar = "շ";
                break;
            case "0548":
                unicodeChar = "Ո";
                break;
            case "0578":
                unicodeChar = "ո";
                break;
            case "0549":
                unicodeChar = "Չ";
                break;
            case "0579":
                unicodeChar = "չ";
                break;
            case "054A":
                unicodeChar = "Պ";
                break;
            case "057A":
                unicodeChar = "պ";
                break;
            case "054B":
                unicodeChar = "Ջ";
                break;
            case "057B":
                unicodeChar = "ջ";
                break;
            case "054C":
                unicodeChar = "Ռ";
                break;
            case "057C":
                unicodeChar = "ռ";
                break;
            case "054D":
                unicodeChar = "Ս";
                break;
            case "057D":
                unicodeChar = "ս";
                break;
            case "054E":
                unicodeChar = "Վ";
                break;
            case "057E":
                unicodeChar = "վ";
                break;
            case "054F":
                unicodeChar = "Տ";
                break;
            case "057F":
                unicodeChar = "տ";
                break;
            case "0550":
                unicodeChar = "Ր";
                break;
            case "0580":
                unicodeChar = "ր";
                break;
            case "0551":
                unicodeChar = "Ց";
                break;
            case "0581":
                unicodeChar = "ց";
                break;
            case "0552":
                unicodeChar = "Ւ";
                break;
            case "0582":
                unicodeChar = "ւ";
                break;
            case "0553":
                unicodeChar = "Փ";
                break;
            case "0583":
                unicodeChar = "փ";
                break;
            case "0554":
                unicodeChar = "Ք";
                break;
            case "0584":
                unicodeChar = "Ք";
                break;
            case "0555":
                unicodeChar = "Օ";
                break;
            case "0585":
                unicodeChar = "օ";
                break;
            case "0556":
                unicodeChar = "Ֆ";
                break;
            case "0586":
                unicodeChar = "ֆ";
                break;
            case "0587":
                unicodeChar = "և";
                break;
            case "0559":
                unicodeChar = "";
                break;
            case "055A":
                unicodeChar = "";
                break;
            case "055B":
                unicodeChar = "";
                break;
            case "055C":
                unicodeChar = "";
                break;
            case "055D":
                unicodeChar = "";
                break;
            case "055E":
                unicodeChar = "";
                break;
            case "055F":
                unicodeChar = "";
                break;
            case "0589":
                unicodeChar = ":";
                break;
            default:
                unicodeChar = (char)Integer.parseInt(coderingChar, 16) + "";
                break;
        }
        return unicodeChar;
    }
}
