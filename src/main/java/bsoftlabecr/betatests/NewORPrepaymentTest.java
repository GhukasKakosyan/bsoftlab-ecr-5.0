package bsoftlabecr.betatests;

import bsoftlabecr.entity.Constants;

import bsoftlabecr.request.receipt.returns.prepayment.NewORPrepaymentRequest;
import bsoftlabecr.response.receipt.returns.prepayment.NewORPrepaymentResponse;

import bsoftlabecr.xml.reader.constants.ConstantsReaderXML;
import bsoftlabecr.xml.reader.receipt.returns.prepayment.NewORPrepaymentRequestReaderXML;
import bsoftlabecr.xml.writer.receipt.returns.prepayment.NewORPrepaymentResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class NewORPrepaymentTest {
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
            NewORPrepaymentRequestReaderXML newORPrepaymentRequestReaderXML = new NewORPrepaymentRequestReaderXML();
            newORPrepaymentRequestReaderXML.setFileName("BSOFTLABECRDATA/NewORPrepaymentRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newORPrepaymentRequestReaderXML.getFileName());
            NewORPrepaymentRequest NewORPrepaymentRequest = newORPrepaymentRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(NewORPrepaymentRequest == null)) {
                System.out.println("BSOFTLAB. NewORPrepaymentRequest.seq: " + NewORPrepaymentRequest.getSeq());
                System.out.println("BSOFTLAB. NewORPrepaymentRequest.returnTicketId: " + NewORPrepaymentRequest.getReturnTicketId());
                System.out.println("BSOFTLAB. NewORPrepaymentRequest.crn: " + NewORPrepaymentRequest.getCrn());
            } else {
                System.out.println("BSOFTLAB. NewORPrepaymentRequest is null pointer object !!!");
            }

            NewORPrepaymentResponse newORPrepaymentResponse = new NewORPrepaymentResponse();
            newORPrepaymentResponse.setRseq(6060);
            newORPrepaymentResponse.setCrn("06060606");
            newORPrepaymentResponse.setSn("60606060");
            newORPrepaymentResponse.setTin("00660066");
            newORPrepaymentResponse.setTaxpayer("BSOFTLAB06");
            newORPrepaymentResponse.setAddress("Yerevan. Norashkharhyan 17, 75.06");
            newORPrepaymentResponse.setTime(66006600.00);
            newORPrepaymentResponse.setFiscal("66606660");
            newORPrepaymentResponse.setLottery("66666666");
            newORPrepaymentResponse.setPrize(1);
            newORPrepaymentResponse.setTotal(600000.00);
            newORPrepaymentResponse.setChange(60000.00);
            newORPrepaymentResponse.setQr("Receipt ID:166, " + "TIN:00660066, " + "CRN:06060606");

            newORPrepaymentResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            NewORPrepaymentResponseWriterXML newORPrepaymentResponseWriterXML = new NewORPrepaymentResponseWriterXML();
            newORPrepaymentResponseWriterXML.setFileName("BSOFTLABECRDATA/NewORPrepaymentResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newORPrepaymentResponseWriterXML.getFileName());
            newORPrepaymentResponseWriterXML.writeFile(newORPrepaymentResponse);
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