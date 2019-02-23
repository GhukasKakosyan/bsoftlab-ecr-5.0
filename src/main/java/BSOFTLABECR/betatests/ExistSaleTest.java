package BSOFTLABECR.betatests;

import BSOFTLABECR.entity.Constants;

import BSOFTLABECR.request.receipt.sale.ExistSaleRequest;
import BSOFTLABECR.response.receipt.sale.ExistSaleResponse;

import BSOFTLABECR.xml.reader.constants.ConstantsReaderXML;
import BSOFTLABECR.xml.reader.receipt.sale.ExistSaleRequestReaderXML;
import BSOFTLABECR.xml.writer.receipt.sale.ExistSaleResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class ExistSaleTest {
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
            ExistSaleRequestReaderXML existSaleRequestReaderXML = new ExistSaleRequestReaderXML();
            existSaleRequestReaderXML.setFileName("BSOFTLABECRDATA/ExistSaleRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + existSaleRequestReaderXML.getFileName());
            ExistSaleRequest existSaleRequest = existSaleRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(existSaleRequest == null)) {
                System.out.println("BSOFTLAB. existSaleRequest.seq: " + existSaleRequest.getSeq());
                System.out.println("BSOFTLAB. existSaleRequest.crn: " + existSaleRequest.getCrn());
                System.out.println("BSOFTLAB. existSaleRequest.receiptId: " + existSaleRequest.getReceiptId());
            } else {
                System.out.println("BSOFTLAB. existSaleRequest is null pointer object !!!");
            }

            ExistSaleResponse existSaleResponse = new ExistSaleResponse();
            existSaleResponse.setCid("3");
            existSaleResponse.setTime("2017.05.19 12.00.10");
            existSaleResponse.setTa("200000.00");
            existSaleResponse.setCash("60000.00");
            existSaleResponse.setCard("60000.00");
            existSaleResponse.setPpa("80000.00");
            existSaleResponse.setPpu("80000.00");
            existSaleResponse.setRef("22222222");
            existSaleResponse.setRefcrn("20202020");
            existSaleResponse.setSaleType("1");
            existSaleResponse.setType("0");
            existSaleResponse.setResponseCode(200);

            existSaleResponse.totals = new ExistSaleResponse.SubTotal[2];

            existSaleResponse.totals[0] = new ExistSaleResponse.SubTotal();
            existSaleResponse.totals[0].setDid("3");
            existSaleResponse.totals[0].setDt("3");
            existSaleResponse.totals[0].setDtm("3");
            existSaleResponse.totals[0].setT("50000.00");
            existSaleResponse.totals[0].setTt("20000.00");
            existSaleResponse.totals[0].setGc("20202020");
            existSaleResponse.totals[0].setGn("Product name number one");
            existSaleResponse.totals[0].setQty("20.000");
            existSaleResponse.totals[0].setP("2000.00");
            existSaleResponse.totals[0].setAdg("02020");
            existSaleResponse.totals[0].setMu("kg");
            existSaleResponse.totals[0].setDsc("200.00");
            existSaleResponse.totals[0].setAdsc("200.00");
            existSaleResponse.totals[0].setDsct("2");
            existSaleResponse.totals[0].setRpid("2");

            existSaleResponse.totals[1] = new ExistSaleResponse.SubTotal();
            existSaleResponse.totals[1].setDid("4");
            existSaleResponse.totals[1].setDt("4");
            existSaleResponse.totals[1].setDtm("4");
            existSaleResponse.totals[1].setT("90000.00");
            existSaleResponse.totals[1].setTt("9000.00");
            existSaleResponse.totals[1].setGc("30303030");
            existSaleResponse.totals[1].setGn("Product name number two");
            existSaleResponse.totals[1].setQty("9.000");
            existSaleResponse.totals[1].setP("120000.00");
            existSaleResponse.totals[1].setAdg("03030");
            existSaleResponse.totals[1].setMu("meter");
            existSaleResponse.totals[1].setDsc("300.00");
            existSaleResponse.totals[1].setAdsc("300.00");
            existSaleResponse.totals[1].setDsct("3");
            existSaleResponse.totals[1].setRpid("3");

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            ExistSaleResponseWriterXML existSaleResponseWriterXML = new ExistSaleResponseWriterXML();
            existSaleResponseWriterXML.setFileName("BSOFTLABECRDATA/ExistSaleResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + existSaleResponseWriterXML.getFileName());
            existSaleResponseWriterXML.writeFile(existSaleResponse);
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