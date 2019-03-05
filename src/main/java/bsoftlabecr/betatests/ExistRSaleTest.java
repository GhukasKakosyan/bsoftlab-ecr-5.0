package bsoftlabecr.betatests;

import bsoftlabecr.entity.Constants;

import bsoftlabecr.request.receipt.returns.sale.ExistRSaleRequest;
import bsoftlabecr.response.receipt.returns.sale.ExistRSaleResponse;

import bsoftlabecr.xml.reader.constants.ConstantsReaderXML;
import bsoftlabecr.xml.reader.receipt.returns.sale.ExistRSaleRequestReaderXML;
import bsoftlabecr.xml.writer.receipt.returns.sale.ExistRSaleResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class ExistRSaleTest {
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
            ExistRSaleRequestReaderXML existRSaleRequestReaderXML = new ExistRSaleRequestReaderXML();
            existRSaleRequestReaderXML.setFileName("BSOFTLABECRDATA/ExistRSaleRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + existRSaleRequestReaderXML.getFileName());
            ExistRSaleRequest existRSaleRequest = existRSaleRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(existRSaleRequest == null)) {
                System.out.println("BSOFTLAB. existRSaleRequest.seq: " + existRSaleRequest.getSeq());
                System.out.println("BSOFTLAB. existRSaleRequest.crn: " + existRSaleRequest.getCrn());
                System.out.println("BSOFTLAB. existRSaleRequest.receiptId: " + existRSaleRequest.getReceiptId());
            } else {
                System.out.println("BSOFTLAB. existRSaleRequest is null pointer object !!!");
            }

            ExistRSaleResponse existRSaleResponse = new ExistRSaleResponse();
            existRSaleResponse.setCid("3");
            existRSaleResponse.setTime("2017.05.19 12.00.10");
            existRSaleResponse.setTa("110000.00");
            existRSaleResponse.setCash("30000.00");
            existRSaleResponse.setCard("30000.00");
            existRSaleResponse.setPpa("60000.00");
            existRSaleResponse.setPpu("50000.00");
            existRSaleResponse.setRef("11111111");
            existRSaleResponse.setRefcrn("10101010");
            existRSaleResponse.setSaleType("0");
            existRSaleResponse.setType("0");
            existRSaleResponse.setResponseCode(200);

            existRSaleResponse.totals = new ExistRSaleResponse.SubTotal[2];

            existRSaleResponse.totals[0] = new ExistRSaleResponse.SubTotal();
            existRSaleResponse.totals[0].setDid("1");
            existRSaleResponse.totals[0].setDt("1");
            existRSaleResponse.totals[0].setDtm("1");
            existRSaleResponse.totals[0].setT("40000.00");
            existRSaleResponse.totals[0].setTt("10000.00");
            existRSaleResponse.totals[0].setGc("10101010");
            existRSaleResponse.totals[0].setGn("Ապրանքա-նյութական արժեք մեկ");
            existRSaleResponse.totals[0].setQty("10.000");
            existRSaleResponse.totals[0].setP("4000.00");
            existRSaleResponse.totals[0].setAdg("01010");
            existRSaleResponse.totals[0].setMu("unit");
            existRSaleResponse.totals[0].setDsc("100.00");
            existRSaleResponse.totals[0].setAdsc("100.00");
            existRSaleResponse.totals[0].setDsct("1");
            existRSaleResponse.totals[0].setRpid("1");

            existRSaleResponse.totals[1] = new ExistRSaleResponse.SubTotal();
            existRSaleResponse.totals[1].setDid("2");
            existRSaleResponse.totals[1].setDt("2");
            existRSaleResponse.totals[1].setDtm("2");
            existRSaleResponse.totals[1].setT("70000.00");
            existRSaleResponse.totals[1].setTt("7000.00");
            existRSaleResponse.totals[1].setGc("20202020");
            existRSaleResponse.totals[1].setGn("Ապրանքա-նյութական արժեք երկու");
            existRSaleResponse.totals[1].setQty("7.000");
            existRSaleResponse.totals[1].setP("10000.00");
            existRSaleResponse.totals[1].setAdg("02020");
            existRSaleResponse.totals[1].setMu("meter");
            existRSaleResponse.totals[1].setDsc("200.00");
            existRSaleResponse.totals[1].setAdsc("200.00");
            existRSaleResponse.totals[1].setDsct("2");
            existRSaleResponse.totals[1].setRpid("2");

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            ExistRSaleResponseWriterXML existRSaleResponseWriterXML = new ExistRSaleResponseWriterXML();
            existRSaleResponseWriterXML.setFileName("BSOFTLABECRDATA/ExistRSaleResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + existRSaleResponseWriterXML.getFileName());
            existRSaleResponseWriterXML.writeFile(existRSaleResponse);
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