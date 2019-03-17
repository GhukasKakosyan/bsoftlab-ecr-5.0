package bsoftlabecr.xml.writer.response.general;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.general.CashRegisterResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class CashRegisterResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String XML_OPEN = "<CashRegisterResponse>";
    private static final String XML_CLOSE = "</CashRegisterResponse>";
    private static final String XML_RESPONSE_CODE_OPEN = "<responseCode>";
    private static final String XML_RESPONSE_CODE_CLOSE = "</responseCode>";

    private CashRegisterResponse cashRegisterResponse;
    private String fileName;

    public CashRegisterResponseWriterXml(
            CashRegisterResponse cashRegisterResponse,
            String fileName) {
        this.cashRegisterResponse = cashRegisterResponse;
        this.fileName = fileName;
    }

    public CashRegisterResponse getCashRegisterResponse() {
        return this.cashRegisterResponse;
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
            writer.write(TAB_KEY + XML_RESPONSE_CODE_OPEN +
                    this.cashRegisterResponse.getResponseCode() +
                    XML_RESPONSE_CODE_CLOSE + ENTER_KEY);
            writer.write(XML_CLOSE + ENTER_KEY);
            writer.close();

        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .CASH_REGISTER_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}