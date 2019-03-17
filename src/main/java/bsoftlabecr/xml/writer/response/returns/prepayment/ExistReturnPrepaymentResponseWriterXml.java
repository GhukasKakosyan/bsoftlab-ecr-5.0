package bsoftlabecr.xml.writer.response.returns.prepayment;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.receipt.returns.prepayment.ExistReturnPrepaymentResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class ExistReturnPrepaymentResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);
    private static final String TOTALSNULL = "null";

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String XML_TAG_OPEN = "<ExistReturnPrepaymentResponse>";
    private static final String XML_TAG_CLOSE = "</ExistReturnPrepaymentResponse>";
    private static final String XML_TAG_CID_OPEN = "<cid>";
    private static final String XML_TAG_CID_CLOSE = "</cid>";
    private static final String XML_TAG_TIME_OPEN = "<time>";
    private static final String XML_TAG_TIME_CLOSE = "</time>";
    private static final String XML_TAG_TA_OPEN = "<ta>";
    private static final String XML_TAG_TA_CLOSE = "</ta>";
    private static final String XML_TAG_CASH_OPEN = "<cash>";
    private static final String XML_TAG_CASH_CLOSE = "</cash>";
    private static final String XML_TAG_CARD_OPEN = "<card>";
    private static final String XML_TAG_CARD_CLOSE = "</card>";
    private static final String XML_TAG_PPA_OPEN = "<ppa>";
    private static final String XML_TAG_PPA_CLOSE = "</ppa>";
    private static final String XML_TAG_PPU_OPEN = "<ppu>";
    private static final String XML_TAG_PPU_CLOSE = "</ppu>";
    private static final String XML_TAG_TYPE_OPEN = "<type>";
    private static final String XML_TAG_TYPE_CLOSE = "</type>";
    private static final String XML_TAG_REF_OPEN = "<ref>";
    private static final String XML_TAG_REF_CLOSE = "</ref>";
    private static final String XML_TAG_REFCRN_OPEN = "<refcrn>";
    private static final String XML_TAG_REFCRN_CLOSE = "</refcrn>";
    private static final String XML_TAG_SALETYPE_OPEN = "<saleType>";
    private static final String XML_TAG_SALETYPE_CLOSE = "</saleType>";
    private static final String XML_TAG_RCODE_OPEN = "<rcode>";
    private static final String XML_TAG_RCODE_CLOSE = "</rcode>";
    private static final String XML_TAG_TOTALS_OPEN = "<totals>";
    private static final String XML_TAG_TOTALS_CLOSE = "</totals>";

    private ExistReturnPrepaymentResponse existReturnPrepaymentResponse;
    private String fileName;

    public ExistReturnPrepaymentResponseWriterXml(
            ExistReturnPrepaymentResponse existReturnPrepaymentResponse,
            String fileName) {
        this.existReturnPrepaymentResponse = existReturnPrepaymentResponse;
        this.fileName = fileName;
    }

    public ExistReturnPrepaymentResponse getExistReturnPrepaymentResponse() {
        return this.existReturnPrepaymentResponse;
    }
    public String getFileName() {
        return this.fileName;
    }

    public void write() throws XmlFileWriteException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName);
            Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(XML_TITLE + ENTER_KEY);
            writer.write(XML_TAG_OPEN + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CID_OPEN +
                    this.existReturnPrepaymentResponse.getCid() +
                    XML_TAG_CID_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TIME_OPEN +
                    this.existReturnPrepaymentResponse.getTime() +
                    XML_TAG_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TA_OPEN +
                    this.existReturnPrepaymentResponse.getTa() +
                    XML_TAG_TA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CASH_OPEN +
                    this.existReturnPrepaymentResponse.getCash() +
                    XML_TAG_CASH_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CARD_OPEN +
                    this.existReturnPrepaymentResponse.getCard() +
                    XML_TAG_CARD_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_PPA_OPEN +
                    this.existReturnPrepaymentResponse.getPpa() +
                    XML_TAG_PPA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_PPU_OPEN +
                    this.existReturnPrepaymentResponse.getPpu() +
                    XML_TAG_PPU_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TYPE_OPEN +
                    this.existReturnPrepaymentResponse.getType() +
                    XML_TAG_TYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_REF_OPEN +
                    this.existReturnPrepaymentResponse.getRef() +
                    XML_TAG_REF_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_REFCRN_OPEN +
                    this.existReturnPrepaymentResponse.getRefcrn() +
                    XML_TAG_REFCRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_SALETYPE_OPEN +
                    this.existReturnPrepaymentResponse.getSaleType() +
                    XML_TAG_SALETYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_RCODE_OPEN +
                    this.existReturnPrepaymentResponse.getResponseCode() +
                    XML_TAG_RCODE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TOTALS_OPEN +
                    TOTALSNULL + XML_TAG_TOTALS_CLOSE + ENTER_KEY);
            writer.write(XML_TAG_CLOSE + ENTER_KEY);
            writer.close();

        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .EXIST_RETURN_PREPAYMENT_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}