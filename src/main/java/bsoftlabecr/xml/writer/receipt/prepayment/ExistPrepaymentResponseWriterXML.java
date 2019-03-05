package bsoftlabecr.xml.writer.receipt.prepayment;

import bsoftlabecr.response.receipt.prepayment.ExistPrepaymentResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.charset.StandardCharsets;

public class ExistPrepaymentResponseWriterXML {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);
    private static final String TOTALSNULL = "null";

    private static final String RESPONSE_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String RESPONSE_OPEN = "<ExistPrepaymentResponse>";
    private static final String RESPONSE_CLOSE = "</ExistPrepaymentResponse>";
    private static final String RESPONSE_CID_OPEN = "<cid>";
    private static final String RESPONSE_CID_CLOSE = "</cid>";
    private static final String RESPONSE_TIME_OPEN = "<time>";
    private static final String RESPONSE_TIME_CLOSE = "</time>";
    private static final String RESPONSE_TA_OPEN = "<ta>";
    private static final String RESPONSE_TA_CLOSE = "</ta>";
    private static final String RESPONSE_CASH_OPEN = "<cash>";
    private static final String RESPONSE_CASH_CLOSE = "</cash>";
    private static final String RESPONSE_CARD_OPEN = "<card>";
    private static final String RESPONSE_CARD_CLOSE = "</card>";
    private static final String RESPONSE_PPA_OPEN = "<ppa>";
    private static final String RESPONSE_PPA_CLOSE = "</ppa>";
    private static final String RESPONSE_PPU_OPEN = "<ppu>";
    private static final String RESPONSE_PPU_CLOSE = "</ppu>";
    private static final String RESPONSE_TYPE_OPEN = "<type>";
    private static final String RESPONSE_TYPE_CLOSE = "</type>";
    private static final String RESPONSE_REF_OPEN = "<ref>";
    private static final String RESPONSE_REF_CLOSE = "</ref>";
    private static final String RESPONSE_REFCRN_OPEN = "<refcrn>";
    private static final String RESPONSE_REFCRN_CLOSE = "</refcrn>";
    private static final String RESPONSE_SALETYPE_OPEN = "<saleType>";
    private static final String RESPONSE_SALETYPE_CLOSE = "</saleType>";
    private static final String RESPONSE_CODE_OPEN = "<rcode>";
    private static final String RESPONSE_CODE_CLOSE = "</rcode>";
    private static final String RESPONSE_TOTALS_OPEN = "<totals>";
    private static final String RESPONSE_TOTALS_CLOSE = "</totals>";

    private String fileName = null;

    public String getFileName() {return this.fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public void writeFile(ExistPrepaymentResponse existPrepaymentResponse) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.getFileName());
        Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        writer.write(RESPONSE_TITLE + ENTER_KEY);
        writer.write(RESPONSE_OPEN + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_CID_OPEN + existPrepaymentResponse.getCid() + RESPONSE_CID_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_TIME_OPEN + existPrepaymentResponse.getTime() + RESPONSE_TIME_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_TA_OPEN + existPrepaymentResponse.getTa() + RESPONSE_TA_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_CASH_OPEN + existPrepaymentResponse.getCash() + RESPONSE_CASH_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_CARD_OPEN + existPrepaymentResponse.getCard() + RESPONSE_CARD_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_PPA_OPEN + existPrepaymentResponse.getPpa() + RESPONSE_PPA_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_PPU_OPEN + existPrepaymentResponse.getPpu() + RESPONSE_PPU_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_TYPE_OPEN + existPrepaymentResponse.getType() + RESPONSE_TYPE_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_REF_OPEN + existPrepaymentResponse.getRef() + RESPONSE_REF_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_REFCRN_OPEN + existPrepaymentResponse.getRefcrn() + RESPONSE_REFCRN_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_SALETYPE_OPEN + existPrepaymentResponse.getSaleType() + RESPONSE_SALETYPE_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_CODE_OPEN + existPrepaymentResponse.getResponseCode() + RESPONSE_CODE_CLOSE + ENTER_KEY);
        writer.write(TAB_KEY + RESPONSE_TOTALS_OPEN + TOTALSNULL + RESPONSE_TOTALS_CLOSE + ENTER_KEY);
        writer.write(RESPONSE_CLOSE + ENTER_KEY);
        writer.close();
    }
}