package BSOFTLABECR.betatests;

import BSOFTLABECR.entity.Constants;

import BSOFTLABECR.request.receipt.returns.prepayment.ExistRPrepaymentRequest;
import BSOFTLABECR.response.receipt.returns.prepayment.ExistRPrepaymentResponse;

import BSOFTLABECR.xml.reader.constants.ConstantsReaderXML;
import BSOFTLABECR.xml.reader.receipt.returns.prepayment.ExistRPrepaymentRequestReaderXML;
import BSOFTLABECR.xml.writer.receipt.returns.prepayment.ExistRPrepaymentResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class ExistRPrepaymentTest {
    public static void main(String[] args) {
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
            ExistRPrepaymentRequestReaderXML existRPrepaymentRequestReaderXML = new ExistRPrepaymentRequestReaderXML();
            existRPrepaymentRequestReaderXML.setFileName("BSOFTLABECRDATA/ExistRPrepaymentRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + existRPrepaymentRequestReaderXML.getFileName());
            ExistRPrepaymentRequest existRPrepaymentRequest = existRPrepaymentRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(existRPrepaymentRequest == null)) {
                System.out.println("BSOFTLAB. existRPrepaymentRequest.seq: " + existRPrepaymentRequest.getSeq());
                System.out.println("BSOFTLAB. existRPrepaymentRequest.crn: " + existRPrepaymentRequest.getCrn());
                System.out.println("BSOFTLAB. existRPrepaymentRequest.receiptId: " + existRPrepaymentRequest.getReceiptId());
            } else {
                System.out.println("BSOFTLAB. existRPrepaymentRequest is null pointer object !!!");
            }

            ExistRPrepaymentResponse existRPrepaymentResponse = new ExistRPrepaymentResponse();
            existRPrepaymentResponse.setCid("3");
            existRPrepaymentResponse.setTime("2017.05.19 12.00.10");
            existRPrepaymentResponse.setTa("110000.00");
            existRPrepaymentResponse.setCash("30000.00");
            existRPrepaymentResponse.setCard("30000.00");
            existRPrepaymentResponse.setPpa("60000.00");
            existRPrepaymentResponse.setPpu("50000.00");
            existRPrepaymentResponse.setRef("11111111");
            existRPrepaymentResponse.setRefcrn("10101010");
            existRPrepaymentResponse.setSaleType("0");
            existRPrepaymentResponse.setType("2");
            existRPrepaymentResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            ExistRPrepaymentResponseWriterXML existRPrepaymentResponseWriterXML = new ExistRPrepaymentResponseWriterXML();
            existRPrepaymentResponseWriterXML.setFileName("BSOFTLABECRDATA/ExistRPrepaymentResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + existRPrepaymentResponseWriterXML.getFileName());
            existRPrepaymentResponseWriterXML.writeFile(existRPrepaymentResponse);
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