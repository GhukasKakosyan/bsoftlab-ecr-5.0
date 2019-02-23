package BSOFTLABECR.betatests;

import BSOFTLABECR.entity.Constants;

import BSOFTLABECR.request.receipt.prepayment.NewPrepaymentRequest;
import BSOFTLABECR.response.receipt.prepayment.NewPrepaymentResponse;

import BSOFTLABECR.xml.reader.constants.ConstantsReaderXML;
import BSOFTLABECR.xml.reader.receipt.prepayment.NewPrepaymentRequestReaderXML;
import BSOFTLABECR.xml.writer.receipt.prepayment.NewPrepaymentResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class NewPrepaymentTest {
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
            NewPrepaymentRequestReaderXML newPrepaymentRequestReaderXML = new NewPrepaymentRequestReaderXML();
            newPrepaymentRequestReaderXML.setFileName("BSOFTLABECRDATA/NewPrepaymentRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newPrepaymentRequestReaderXML.getFileName());
            NewPrepaymentRequest newPrepaymentRequest = newPrepaymentRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if(!(newPrepaymentRequest == null)) {
                System.out.println("BSOFTLAB. newPrepaymentRequest.seq: " + newPrepaymentRequest.getSeq());
                System.out.println("BSOFTLAB. newPrepaymentRequest.paidAmount: " + newPrepaymentRequest.getPaidAmount());
                System.out.println("BSOFTLAB. newPrepaymentRequest.paidAmountCard: " + newPrepaymentRequest.getPaidAmountCard());
                System.out.println("BSOFTLAB. newPrepaymentRequest.partialAmount: " + newPrepaymentRequest.getPartialAmount());
                System.out.println("BSOFTLAB. newPrepaymentRequest.prePaymentAmount: " + newPrepaymentRequest.getPrePaymentAmount());
                System.out.println("BSOFTLAB. newPrepaymentRequest.mode: " + newPrepaymentRequest.getMode());
                System.out.println("BSOFTLAB. newPrepaymentRequest.useExtPOS: " + newPrepaymentRequest.isUseExtPOS());
                System.out.println("BSOFTLAB. newPrepaymentRequest.items: " + newPrepaymentRequest.getItems());
                System.out.println();
            } else {
                System.out.println("BSOFTLAB. newPrepaymentRequest is null pointer object !!!");
            }

            NewPrepaymentResponse newPrepaymentResponse = new NewPrepaymentResponse();
            newPrepaymentResponse.setRseq(2020);
            newPrepaymentResponse.setCrn("02020202");
            newPrepaymentResponse.setSn("20202020");
            newPrepaymentResponse.setTin("00220022");
            newPrepaymentResponse.setTaxpayer("BSOFTLAB02");
            newPrepaymentResponse.setAddress("Yerevan. st.Norashkharhyan 17, 75.02");
            newPrepaymentResponse.setTime(22002200.00);
            newPrepaymentResponse.setFiscal("22202220");
            newPrepaymentResponse.setLottery("22222222");
            newPrepaymentResponse.setPrize(0);
            newPrepaymentResponse.setTotal(200000.00);
            newPrepaymentResponse.setChange(20000.00);
            newPrepaymentResponse.setQr("Receipt ID:168, " + "TIN:00220022, " + "CRN:02020202");
            newPrepaymentResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            NewPrepaymentResponseWriterXML newPrepaymentResponseWriterXML = new NewPrepaymentResponseWriterXML();
            newPrepaymentResponseWriterXML.setFileName("BSOFTLABECRDATA/NewPrepaymentResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newPrepaymentResponseWriterXML.getFileName());
            newPrepaymentResponseWriterXML.writeFile(newPrepaymentResponse);
            System.out.println("BSOFTLAB. Response is written into XML file successfully !");
            Thread.sleep(5000);

        } catch(XMLStreamException xmlStreamException) {
            System.out.println("XMLStreamException: " + xmlStreamException.toString());
        } catch(FileNotFoundException fileNotFoundException) {
            System.out.println("FileNotFoundException: " + fileNotFoundException.toString());
        } catch(IOException ioException) {
            System.out.println("IOException: " + ioException.toString());
        } catch(InterruptedException interruptedException) {
            System.out.println("InterruptedException: " + interruptedException.toString());
        }
    }
}