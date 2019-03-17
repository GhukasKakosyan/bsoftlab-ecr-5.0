package bsoftlabecr.xml.writer.response.sale;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.receipt.sale.NewSaleResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.charset.StandardCharsets;

public class NewSaleResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String XML_TAG_OPEN = "<NewSaleResponse>";
    private static final String XML_TAG_CLOSE = "</NewSaleResponse>";
    private static final String XML_TAG_RSEQ_OPEN = "<rseq>";
    private static final String XML_TAG_RSEQ_CLOSE = "</rseq>";
    private static final String XML_TAG_CRN_OPEN = "<crn>";
    private static final String XML_TAG_CRN_CLOSE = "</crn>";
    private static final String XML_TAG_SN_OPEN = "<sn>";
    private static final String XML_TAG_SN_CLOSE = "</sn>";
    private static final String XML_TAG_TIN_OPEN = "<tin>";
    private static final String XML_TAG_TIN_CLOSE = "</tin>";
    private static final String XML_TAG_TAXPAYER_OPEN = "<taxpayer>";
    private static final String XML_TAG_TAXPAYER_CLOSE = "</taxpayer>";
    private static final String XML_TAG_ADDRESS_OPEN = "<address>";
    private static final String XML_TAG_ADDRESS_CLOSE = "</address>";
    private static final String XML_TAG_TIME_OPEN = "<time>";
    private static final String XML_TAG_TIME_CLOSE = "</time>";
    private static final String XML_TAG_FISCAL_OPEN = "<fiscal>";
    private static final String XML_TAG_FISCAL_CLOSE = "</fiscal>";
    private static final String XML_TAG_LOTTERY_OPEN = "<lottery>";
    private static final String XML_TAG_LOTTERY_CLOSE = "</lottery>";
    private static final String XML_TAG_PRIZE_OPEN = "<prize>";
    private static final String XML_TAG_PRIZE_CLOSE = "</prize>";
    private static final String XML_TAG_TOTAL_OPEN = "<total>";
    private static final String XML_TAG_TOTAL_CLOSE = "</total>";
    private static final String XML_TAG_CHANGE_OPEN = "<change>";
    private static final String XML_TAG_CHANGE_CLOSE = "</change>";
    private static final String XML_TAG_QR_OPEN = "<qr>";
    private static final String XML_TAG_QR_CLOSE = "</qr>";
    private static final String XML_TAG_RCODE_OPEN = "<rcode>";
    private static final String XML_TAG_RCODE_CLOSE = "</rcode>";

    private NewSaleResponse newSaleResponse;
    private String fileName;

    public NewSaleResponseWriterXml(NewSaleResponse newSaleResponse, String fileName) {
        this.newSaleResponse = newSaleResponse;
        this.fileName = fileName;
    }

    public NewSaleResponse getNewSaleResponse() {
        return this.newSaleResponse;
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
            writer.write(TAB_KEY + XML_TAG_RSEQ_OPEN +
                    this.newSaleResponse.getRseq() + XML_TAG_RSEQ_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CRN_OPEN +
                    this.newSaleResponse.getCrn() + XML_TAG_CRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_SN_OPEN +
                    this.newSaleResponse.getSn() + XML_TAG_SN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TIN_OPEN +
                    this.newSaleResponse.getTin() + XML_TAG_TIN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TAXPAYER_OPEN +
                    this.newSaleResponse.getTaxpayer() + XML_TAG_TAXPAYER_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_ADDRESS_OPEN +
                    this.newSaleResponse.getAddress() + XML_TAG_ADDRESS_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TIME_OPEN +
                    this.newSaleResponse.getTime() + XML_TAG_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_FISCAL_OPEN +
                    this.newSaleResponse.getFiscal() + XML_TAG_FISCAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_LOTTERY_OPEN +
                    this.newSaleResponse.getLottery() + XML_TAG_LOTTERY_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_PRIZE_OPEN +
                    this.newSaleResponse.getPrize() + XML_TAG_PRIZE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TOTAL_OPEN +
                    this.newSaleResponse.getTotal() + XML_TAG_TOTAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CHANGE_OPEN +
                    this.newSaleResponse.getChange() + XML_TAG_CHANGE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_QR_OPEN +
                    this.newSaleResponse.getQr() + XML_TAG_QR_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_RCODE_OPEN +
                    this.newSaleResponse.getResponseCode() + XML_TAG_RCODE_CLOSE + ENTER_KEY);
            writer.write(XML_TAG_CLOSE + ENTER_KEY);
            writer.close();
        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .NEW_SALE_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}