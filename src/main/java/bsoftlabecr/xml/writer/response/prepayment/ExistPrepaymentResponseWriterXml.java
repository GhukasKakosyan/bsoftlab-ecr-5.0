package bsoftlabecr.xml.writer.response.prepayment;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.receipt.prepayment.ExistPrepaymentResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.charset.StandardCharsets;

public class ExistPrepaymentResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);
    private static final String TOTALSNULL = "null";

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String XML_OPEN = "<ExistPrepaymentResponse>";
    private static final String XML_CLOSE = "</ExistPrepaymentResponse>";
    private static final String XML_CID_OPEN = "<cid>";
    private static final String XML_CID_CLOSE = "</cid>";
    private static final String XML_TIME_OPEN = "<time>";
    private static final String XML_TIME_CLOSE = "</time>";
    private static final String XML_TA_OPEN = "<ta>";
    private static final String XML_TA_CLOSE = "</ta>";
    private static final String XML_CASH_OPEN = "<cash>";
    private static final String XML_CASH_CLOSE = "</cash>";
    private static final String XML_CARD_OPEN = "<card>";
    private static final String XML_CARD_CLOSE = "</card>";
    private static final String XML_PPA_OPEN = "<ppa>";
    private static final String XML_PPA_CLOSE = "</ppa>";
    private static final String XML_PPU_OPEN = "<ppu>";
    private static final String XML_PPU_CLOSE = "</ppu>";
    private static final String XML_TYPE_OPEN = "<type>";
    private static final String XML_TYPE_CLOSE = "</type>";
    private static final String XML_REF_OPEN = "<ref>";
    private static final String XML_REF_CLOSE = "</ref>";
    private static final String XML_REFCRN_OPEN = "<refcrn>";
    private static final String XML_REFCRN_CLOSE = "</refcrn>";
    private static final String XML_SALETYPE_OPEN = "<saleType>";
    private static final String XML_SALETYPE_CLOSE = "</saleType>";
    private static final String XML_RCODE_OPEN = "<rcode>";
    private static final String XML_RCODE_CLOSE = "</rcode>";
    private static final String XML_TOTALS_OPEN = "<totals>";
    private static final String XML_TOTALS_CLOSE = "</totals>";

    private ExistPrepaymentResponse existPrepaymentResponse;
    private String fileName;

    public ExistPrepaymentResponseWriterXml(
            ExistPrepaymentResponse existPrepaymentResponse, String fileName) {
        this.existPrepaymentResponse = existPrepaymentResponse;
        this.fileName = fileName;
    }

    public ExistPrepaymentResponse getExistPrepaymentResponse() {
        return this.existPrepaymentResponse;
    }
    public String getFileName() {
        return this.fileName;
    }

    public void write() throws XmlFileWriteException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName);
            Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(XML_TITLE + ENTER_KEY);
            writer.write(XML_OPEN + ENTER_KEY);
            writer.write(TAB_KEY + XML_CID_OPEN +
                    this.existPrepaymentResponse.getCid() +
                    XML_CID_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TIME_OPEN +
                    this.existPrepaymentResponse.getTime() +
                    XML_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TA_OPEN +
                    this.existPrepaymentResponse.getTa() +
                    XML_TA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_CASH_OPEN +
                    this.existPrepaymentResponse.getCash() +
                    XML_CASH_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_CARD_OPEN +
                    this.existPrepaymentResponse.getCard() +
                    XML_CARD_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_PPA_OPEN +
                    this.existPrepaymentResponse.getPpa() +
                    XML_PPA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_PPU_OPEN +
                    this.existPrepaymentResponse.getPpu() +
                    XML_PPU_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TYPE_OPEN +
                    this.existPrepaymentResponse.getType() +
                    XML_TYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_REF_OPEN +
                    this.existPrepaymentResponse.getRef() +
                    XML_REF_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_REFCRN_OPEN +
                    this.existPrepaymentResponse.getRefcrn() +
                    XML_REFCRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_SALETYPE_OPEN +
                    this.existPrepaymentResponse.getSaleType() +
                    XML_SALETYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_RCODE_OPEN +
                    this.existPrepaymentResponse.getResponseCode() +
                    XML_RCODE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TOTALS_OPEN +
                    TOTALSNULL + XML_TOTALS_CLOSE + ENTER_KEY);
            writer.write(XML_CLOSE + ENTER_KEY);
            writer.close();
        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .EXIST_PREPAYMENT_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}