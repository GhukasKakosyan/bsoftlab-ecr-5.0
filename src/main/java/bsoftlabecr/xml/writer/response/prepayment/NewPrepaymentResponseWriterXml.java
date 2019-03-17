package bsoftlabecr.xml.writer.response.prepayment;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.receipt.prepayment.NewPrepaymentResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class NewPrepaymentResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString ((char)9);

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";
    
    private static final String XML_OPEN = "<NewPrepaymentResponse>";
    private static final String XML_CLOSE = "</NewPrepaymentResponse>";
    private static final String XML_RSEQ_OPEN = "<rseq>";
    private static final String XML_RSEQ_CLOSE = "</rseq>";
    private static final String XML_CRN_OPEN = "<crn>";
    private static final String XML_CRN_CLOSE = "</crn>";
    private static final String XML_SN_OPEN = "<sn>";
    private static final String XML_SN_CLOSE = "</sn>";
    private static final String XML_TIN_OPEN = "<tin>";
    private static final String XML_TIN_CLOSE = "</tin>";
    private static final String XML_TAXPAYER_OPEN = "<taxpayer>";
    private static final String XML_TAXPAYER_CLOSE = "</taxpayer>";
    private static final String XML_ADDRESS_OPEN = "<address>";
    private static final String XML_ADDRESS_CLOSE = "</address>";
    private static final String XML_TIME_OPEN = "<time>";
    private static final String XML_TIME_CLOSE = "</time>";
    private static final String XML_FISCAL_OPEN = "<fiscal>";
    private static final String XML_FISCAL_CLOSE = "</fiscal>";
    private static final String XML_LOTTERY_OPEN = "<lottery>";
    private static final String XML_LOTTERY_CLOSE = "</lottery>";
    private static final String XML_PRIZE_OPEN = "<prize>";
    private static final String XML_PRIZE_CLOSE = "</prize>";
    private static final String XML_TOTAL_OPEN = "<total>";
    private static final String XML_TOTAL_CLOSE = "</total>";
    private static final String XML_CHANGE_OPEN = "<change>";
    private static final String XML_CHANGE_CLOSE = "</change>";
    private static final String XML_QR_OPEN = "<qr>";
    private static final String XML_QR_CLOSE = "</qr>";
    private static final String XML_RCODE_OPEN = "<rcode>";
    private static final String XML_RCODE_CLOSE = "</rcode>";

    private NewPrepaymentResponse newPrepaymentResponse;
    private String fileName;

    public NewPrepaymentResponseWriterXml(
            NewPrepaymentResponse newPrepaymentResponse,
            String fileName) {
        this.newPrepaymentResponse = newPrepaymentResponse;
        this.fileName = fileName;
    }

    public NewPrepaymentResponse getNewPrepaymentResponse() {
        return this.newPrepaymentResponse;
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
            writer.write(TAB_KEY + XML_RSEQ_OPEN +
                    this.newPrepaymentResponse.getRseq() +
                    XML_RSEQ_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_CRN_OPEN +
                    this.newPrepaymentResponse.getCrn() +
                    XML_CRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_SN_OPEN +
                    this.newPrepaymentResponse.getSn() +
                    XML_SN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TIN_OPEN +
                    this.newPrepaymentResponse.getTin() +
                    XML_TIN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAXPAYER_OPEN +
                    this.newPrepaymentResponse.getTaxpayer() +
                    XML_TAXPAYER_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_ADDRESS_OPEN +
                    this.newPrepaymentResponse.getAddress() +
                    XML_ADDRESS_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TIME_OPEN +
                    this.newPrepaymentResponse.getTime() +
                    XML_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_FISCAL_OPEN +
                    this.newPrepaymentResponse.getFiscal() +
                    XML_FISCAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_LOTTERY_OPEN +
                    this.newPrepaymentResponse.getLottery() +
                    XML_LOTTERY_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_PRIZE_OPEN +
                    this.newPrepaymentResponse.getPrize() +
                    XML_PRIZE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TOTAL_OPEN +
                    this.newPrepaymentResponse.getTotal() +
                    XML_TOTAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_CHANGE_OPEN +
                    this.newPrepaymentResponse.getChange() +
                    XML_CHANGE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_QR_OPEN +
                    this.newPrepaymentResponse.getQr() +
                    XML_QR_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_RCODE_OPEN +
                    this.newPrepaymentResponse.getResponseCode() +
                    XML_RCODE_CLOSE + ENTER_KEY);
            writer.write(XML_CLOSE + ENTER_KEY);
            writer.close();

        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .NEW_PREPAYMENT_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}