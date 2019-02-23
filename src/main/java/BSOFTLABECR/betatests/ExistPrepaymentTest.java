package BSOFTLABECR.betatests;

import BSOFTLABECR.entity.Constants;

import BSOFTLABECR.request.receipt.prepayment.ExistPrepaymentRequest;
import BSOFTLABECR.response.receipt.prepayment.ExistPrepaymentResponse;

import BSOFTLABECR.xml.reader.constants.ConstantsReaderXML;
import BSOFTLABECR.xml.reader.receipt.prepayment.ExistPrepaymentRequestReaderXML;
import BSOFTLABECR.xml.writer.receipt.prepayment.ExistPrepaymentResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class ExistPrepaymentTest {
    public static void main (String[] args) {
        try {
            System.out.println("BSOFTLAB. Trying to read ECR constants... ");
            ConstantsReaderXML constantsReaderXML = new ConstantsReaderXML();
            constantsReaderXML.setFileName("BSOFTLABECRDATA/Constants.xml");
            System.out.println("BSOFTLAB. Reading ECR constants from file: " + constantsReaderXML.getFileName());
            Constants constants = constantsReaderXML.readFile();
            System.out.println("BSOFTLAB. ECR constants is read successfully !");
            System.out.println("BSOFTLAB. constants.crn: " + constants.getCrn());
            System.out.println("BSOFTLAB. constants.ip: " + constants.getIp());
            System.out.println("BSOFTLAB. constants.port: " + constants.getPort());
            System.out.println("BSOFTLAB. constants.password: " + constants.getPassword());
            System.out.println("BSOFTLAB. constants.cashierId: " + constants.getCashierId());
            System.out.println("BSOFTLAB. constants.cashierPassword: " + constants.getCashierPassword());
            System.out.println();

            System.out.println("BSOFTLAB. Trying to read request from XML file...");
            ExistPrepaymentRequestReaderXML existPrepaymentRequestReaderXML = new ExistPrepaymentRequestReaderXML();
            existPrepaymentRequestReaderXML.setFileName("BSOFTLABECRDATA/ExistPrepaymentRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + existPrepaymentRequestReaderXML.getFileName());
            ExistPrepaymentRequest existPrepaymentRequest = existPrepaymentRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(existPrepaymentRequest == null)) {
                System.out.println("BSOFTLAB. existPrepaymentRequest.seq: " + existPrepaymentRequest.getSeq());
                System.out.println("BSOFTLAB. existPrepaymentRequest.crn: " + existPrepaymentRequest.getCrn());
                System.out.println("BSOFTLAB. existPrepaymentRequest.receiptId: " + existPrepaymentRequest.getReceiptId());
            } else {
                System.out.println("BSOFTLAB. existPrepaymentRequest is null pointer object !!!");
            }

            ExistPrepaymentResponse existPrepaymentResponse = new ExistPrepaymentResponse();
            existPrepaymentResponse.setCid("2");
            existPrepaymentResponse.setTime("2017.05.19 12.00.10");
            existPrepaymentResponse.setTa("110000.00");
            existPrepaymentResponse.setCash("30000.00");
            existPrepaymentResponse.setCard("30000.00");
            existPrepaymentResponse.setPpa("60000.00");
            existPrepaymentResponse.setPpu("50000.00");
            existPrepaymentResponse.setType("3");
            existPrepaymentResponse.setRef("11111111");
            existPrepaymentResponse.setRefcrn("10101010");
            existPrepaymentResponse.setSaleType("0");
            existPrepaymentResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            ExistPrepaymentResponseWriterXML existPrepaymentResponseWriterXML = new ExistPrepaymentResponseWriterXML();
            existPrepaymentResponseWriterXML.setFileName("BSOFTLABECRDATA/ExistPrepaymentResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + existPrepaymentResponseWriterXML.getFileName());
            existPrepaymentResponseWriterXML.writeFile(existPrepaymentResponse);
            System.out.println("BSOFTLAB. Response is written into XML file successfully !");
            Thread.sleep(5000);

        } catch (XMLStreamException xmlStreamException) {
            System.out.println("XMLStreamException: " + xmlStreamException.toString());
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("FileNotFoundException: " + fileNotFoundException.toString());
        } catch (IOException ioException) {
            System.out.println("IOException: " + ioException.toString());
        } catch (InterruptedException interruptedException) {
            System.out.println("InterruptedException: " + interruptedException.toString());
        }
    }
}