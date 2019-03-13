package bsoftlabecr.xml.writer.receipt.prepayment;

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

    private static final String RESPONSE_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";
    
    private static final String RESPONSE_OPEN = "<NewPrepaymentResponse>";
    private static final String RESPONSE_CLOSE = "</NewPrepaymentResponse>";
    private static final String RESPONSE_RSEQ_OPEN = "<rseq>";
    private static final String RESPONSE_RSEQ_CLOSE = "</rseq>";
    private static final String RESPONSE_CRN_OPEN = "<crn>";
    private static final String RESPONSE_CRN_CLOSE = "</crn>";
    private static final String RESPONSE_SN_OPEN = "<sn>";
    private static final String RESPONSE_SN_CLOSE = "</sn>";
    private static final String RESPONSE_TIN_OPEN = "<tin>";
    private static final String RESPONSE_TIN_CLOSE = "</tin>";
    private static final String RESPONSE_TAXPAYER_OPEN = "<taxpayer>";
    private static final String RESPONSE_TAXPAYER_CLOSE = "</taxpayer>";
    private static final String RESPONSE_ADDRESS_OPEN = "<address>";
    private static final String RESPONSE_ADDRESS_CLOSE = "</address>";
    private static final String RESPONSE_TIME_OPEN = "<time>";
    private static final String RESPONSE_TIME_CLOSE = "</time>";
    private static final String RESPONSE_FISCAL_OPEN = "<fiscal>";
    private static final String RESPONSE_FISCAL_CLOSE = "</fiscal>";
    private static final String RESPONSE_LOTTERY_OPEN = "<lottery>";
    private static final String RESPONSE_LOTTERY_CLOSE = "</lottery>";
    private static final String RESPONSE_PRIZE_OPEN = "<prize>";
    private static final String RESPONSE_PRIZE_CLOSE = "</prize>";
    private static final String RESPONSE_TOTAL_OPEN = "<total>";
    private static final String RESPONSE_TOTAL_CLOSE = "</total>";
    private static final String RESPONSE_CHANGE_OPEN = "<change>";
    private static final String RESPONSE_CHANGE_CLOSE = "</change>";
    private static final String RESPONSE_QR_OPEN = "<qr>";
    private static final String RESPONSE_QR_CLOSE = "</qr>";
    private static final String RESPONSE_CODE_OPEN = "<rcode>";
    private static final String RESPONSE_CODE_CLOSE = "</rcode>";

    private String fileName = null;

    public NewPrepaymentResponseWriterXml() {}

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void writeFile(NewPrepaymentResponse newPrepaymentResponse)
            throws XmlFileWriteException {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.getFileName());
            Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(RESPONSE_TITLE + ENTER_KEY);
            writer.write(RESPONSE_OPEN + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_RSEQ_OPEN + newPrepaymentResponse.getRseq() + RESPONSE_RSEQ_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CRN_OPEN + newPrepaymentResponse.getCrn() + RESPONSE_CRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_SN_OPEN + newPrepaymentResponse.getSn() + RESPONSE_SN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TIN_OPEN + newPrepaymentResponse.getTin() + RESPONSE_TIN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TAXPAYER_OPEN + newPrepaymentResponse.getTaxpayer() + RESPONSE_TAXPAYER_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_ADDRESS_OPEN + newPrepaymentResponse.getAddress() + RESPONSE_ADDRESS_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TIME_OPEN + newPrepaymentResponse.getTime() + RESPONSE_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_FISCAL_OPEN + newPrepaymentResponse.getFiscal() + RESPONSE_FISCAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_LOTTERY_OPEN + newPrepaymentResponse.getLottery() + RESPONSE_LOTTERY_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_PRIZE_OPEN + newPrepaymentResponse.getPrize() + RESPONSE_PRIZE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TOTAL_OPEN + newPrepaymentResponse.getTotal() + RESPONSE_TOTAL_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CHANGE_OPEN + newPrepaymentResponse.getChange() + RESPONSE_CHANGE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_QR_OPEN + newPrepaymentResponse.getQr() + RESPONSE_QR_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CODE_OPEN + newPrepaymentResponse.getResponseCode() + RESPONSE_CODE_CLOSE + ENTER_KEY);
            writer.write(RESPONSE_CLOSE + ENTER_KEY);
            writer.close();
        } catch (IOException ioException) {
            throw new XmlFileWriteException(ioException.getMessage());
        }
    }
}