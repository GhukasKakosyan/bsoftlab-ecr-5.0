package bsoftlabecr.betatests;

import bsoftlabecr.entity.Constants;

import bsoftlabecr.request.receipt.returns.prepayment.NewPRPrepaymentRequest;
import bsoftlabecr.response.receipt.returns.prepayment.NewPRPrepaymentResponse;

import bsoftlabecr.xml.reader.constants.ConstantsReaderXML;
import bsoftlabecr.xml.reader.receipt.returns.prepayment.NewPRPrepaymentRequestReaderXML;
import bsoftlabecr.xml.writer.receipt.returns.prepayment.NewPRPrepaymentResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class NewPRPrepaymentTest {
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
            NewPRPrepaymentRequestReaderXML newPRPrepaymentRequestReaderXML = new NewPRPrepaymentRequestReaderXML();
            newPRPrepaymentRequestReaderXML.setFileName("BSOFTLABECRDATA/NewPRPrepaymentRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newPRPrepaymentRequestReaderXML.getFileName());
            NewPRPrepaymentRequest NewPRPrepaymentRequest = newPRPrepaymentRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(NewPRPrepaymentRequest == null)) {
                System.out.println("BSOFTLAB. NewPRPrepaymentRequest.seq: " + NewPRPrepaymentRequest.getSeq());
                System.out.println("BSOFTLAB. NewPRPrepaymentRequest.returnTicketId: " + NewPRPrepaymentRequest.getReturnTicketId());
                System.out.println("BSOFTLAB. NewPRPrepaymentRequest.crn: " + NewPRPrepaymentRequest.getCrn());
                System.out.println("BSOFTLAB. NewPRPrepaymentRequest.cashAmountForReturn: " + NewPRPrepaymentRequest.getCashAmountForReturn());
                System.out.println("BSOFTLAB. NewPRPrepaymentRequest.cardAmountForReturn: " + NewPRPrepaymentRequest.getCardAmountForReturn());
            } else {
                System.out.println("BSOFTLAB. NewPRPrepaymentRequest is null pointer object !!!");
            }

            NewPRPrepaymentResponse newPRPrepaymentResponse = new NewPRPrepaymentResponse();
            newPRPrepaymentResponse.setRseq(4040);
            newPRPrepaymentResponse.setCrn("04040404");
            newPRPrepaymentResponse.setSn("40404040");
            newPRPrepaymentResponse.setTin("00440044");
            newPRPrepaymentResponse.setTaxpayer("BSOFTLAB04");
            newPRPrepaymentResponse.setAddress("Yerevan. Norashkharhyan 17, 75.04");
            newPRPrepaymentResponse.setTime(44004400.00);
            newPRPrepaymentResponse.setFiscal("44404440");
            newPRPrepaymentResponse.setLottery("44444444");
            newPRPrepaymentResponse.setPrize(0);
            newPRPrepaymentResponse.setTotal(400000.00);
            newPRPrepaymentResponse.setChange(40000.00);
            newPRPrepaymentResponse.setQr("Receipt ID:169, " + "TIN:00440044, " + "CRN:04040404");
            newPRPrepaymentResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            NewPRPrepaymentResponseWriterXML newPRPrepaymentResponseWriterXML = new NewPRPrepaymentResponseWriterXML();
            newPRPrepaymentResponseWriterXML.setFileName("BSOFTLABECRDATA/NewPRPrepaymentResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newPRPrepaymentResponseWriterXML.getFileName());
            newPRPrepaymentResponseWriterXML.writeFile(newPRPrepaymentResponse);
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