package bsoftlabecr.xml.writer.response.returns.prepayment;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.receipt.returns.prepayment.NewPartialReturnPrepaymentResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class NewPartialReturnPrepaymentResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String XML_TAG_OPEN = "<NewPartialReturnPrepaymentResponse>";
    private static final String XML_TAG_CLOSE = "</NewPartialReturnPrepaymentResponse>";
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

    private NewPartialReturnPrepaymentResponse newPartialReturnPrepaymentResponse;
    private String fileName;

    public NewPartialReturnPrepaymentResponseWriterXml(
            NewPartialReturnPrepaymentResponse newPartialReturnPrepaymentResponse,
            String fileName) {
        this.newPartialReturnPrepaymentResponse = newPartialReturnPrepaymentResponse;
        this.fileName = fileName;
    }

    public NewPartialReturnPrepaymentResponse getNewPartialReturnPrepaymentResponse() {
        return this.newPartialReturnPrepaymentResponse;
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
                    this.newPartialReturnPrepaymentResponse.getRseq() +
                    XML_TAG_RSEQ_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CRN_OPEN +
                    this.newPartialReturnPrepaymentResponse.getCrn() +
                    XML_TAG_CRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_SN_OPEN +
                    this.newPartialReturnPrepaymentResponse.getSn() +
                    XML_TAG_SN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TIN_OPEN +
                    this.newPartialReturnPrepaymentResponse.getTin() +
                    XML_TAG_TIN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TAXPAYER_OPEN +
                    this.newPartialReturnPrepaymentResponse.getTaxpayer() +
                    XML_TAG_TAXPAYER_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_ADDRESS_OPEN +
                    this.newPartialReturnPrepaymentResponse.getAddress() +
                    XML_TAG_ADDRESS_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TIME_OPEN +
                    this.newPartialReturnPrepaymentResponse.getTime() +
                    XML_TAG_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_FISCAL_OPEN +
                    this.newPartialReturnPrepaymentResponse.getFiscal() +
                    XML_TAG_FISCAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_LOTTERY_OPEN +
                    this.newPartialReturnPrepaymentResponse.getLottery() +
                    XML_TAG_LOTTERY_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_PRIZE_OPEN +
                    this.newPartialReturnPrepaymentResponse.getPrize() +
                    XML_TAG_PRIZE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TOTAL_OPEN +
                    this.newPartialReturnPrepaymentResponse.getTotal() +
                    XML_TAG_TOTAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CHANGE_OPEN +
                    this.newPartialReturnPrepaymentResponse.getChange() +
                    XML_TAG_CHANGE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_QR_OPEN +
                    this.newPartialReturnPrepaymentResponse.getQr() +
                    XML_TAG_QR_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_RCODE_OPEN +
                    this.newPartialReturnPrepaymentResponse.getResponseCode() +
                    XML_TAG_RCODE_CLOSE + ENTER_KEY);
            writer.write(XML_TAG_CLOSE + ENTER_KEY);
            writer.close();

        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .NEW_PARTIAL_RETURN_PREPAYMENT_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}