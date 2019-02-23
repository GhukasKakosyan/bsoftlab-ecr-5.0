package BSOFTLABECR.betatests;

import BSOFTLABECR.entity.Constants;

import BSOFTLABECR.request.receipt.returns.sale.NewORSaleRequest;
import BSOFTLABECR.response.receipt.returns.sale.NewORSaleResponse;

import BSOFTLABECR.xml.reader.constants.ConstantsReaderXML;
import BSOFTLABECR.xml.reader.receipt.returns.sale.NewORSaleRequestReaderXML;
import BSOFTLABECR.xml.writer.receipt.returns.sale.NewORSaleResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class NewORSaleTest {
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
            NewORSaleRequestReaderXML newORSaleRequestReaderXML = new NewORSaleRequestReaderXML();
            newORSaleRequestReaderXML.setFileName("BSOFTLABECRDATA/NewORSaleRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newORSaleRequestReaderXML.getFileName());
            NewORSaleRequest NewORSaleRequest = newORSaleRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(NewORSaleRequest == null)) {
                System.out.println("BSOFTLAB. NewORSaleRequest.seq: " + NewORSaleRequest.getSeq());
                System.out.println("BSOFTLAB. NewORSaleRequest.returnTicketId: " + NewORSaleRequest.getReturnTicketId());
                System.out.println("BSOFTLAB. NewORSaleRequest.crn: " + NewORSaleRequest.getCrn());
            } else {
                System.out.println("BSOFTLAB. NewORSaleRequest is null pointer object !!!");
            }

            NewORSaleResponse newORSaleResponse = new NewORSaleResponse();
            newORSaleResponse.setRseq(5050);
            newORSaleResponse.setCrn("05050505");
            newORSaleResponse.setSn("50505050");
            newORSaleResponse.setTin("00550055");
            newORSaleResponse.setTaxpayer("BSOFTLAB05");
            newORSaleResponse.setAddress("Yerevan. Norashkharhyan 17, 75.05");
            newORSaleResponse.setTime(55005500.00);
            newORSaleResponse.setFiscal("55505550");
            newORSaleResponse.setLottery("55555555");
            newORSaleResponse.setPrize(0);
            newORSaleResponse.setTotal(500000.00);
            newORSaleResponse.setChange(50000.00);
            newORSaleResponse.setQr("Receipt ID:167, " + "TIN:00550055, " + "CRN:05050505");
            newORSaleResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            NewORSaleResponseWriterXML newORSaleResponseWriterXML = new NewORSaleResponseWriterXML();
            newORSaleResponseWriterXML.setFileName("BSOFTLABECRDATA/NewORSaleResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newORSaleResponseWriterXML.getFileName());
            newORSaleResponseWriterXML.writeFile(newORSaleResponse);
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