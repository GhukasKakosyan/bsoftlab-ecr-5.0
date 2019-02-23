package BSOFTLABECR.betatests;

import BSOFTLABECR.entity.Constants;
import BSOFTLABECR.entity.ReturnItem;

import BSOFTLABECR.request.receipt.returns.sale.NewPRSaleRequest;
import BSOFTLABECR.response.receipt.returns.sale.NewPRSaleResponse;

import BSOFTLABECR.xml.reader.constants.ConstantsReaderXML;
import BSOFTLABECR.xml.reader.receipt.returns.sale.NewPRSaleRequestReaderXML;
import BSOFTLABECR.xml.writer.receipt.returns.sale.NewPRSaleResponseWriterXML;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class NewPRSaleTest {
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
            NewPRSaleRequestReaderXML newPRSaleRequestReaderXML = new NewPRSaleRequestReaderXML();
            newPRSaleRequestReaderXML.setFileName("BSOFTLABECRDATA/NewPRSaleRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newPRSaleRequestReaderXML.getFileName());
            NewPRSaleRequest NewPRSaleRequest = newPRSaleRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(NewPRSaleRequest == null)) {
                System.out.println("BSOFTLAB. NewPRSaleRequest.seq: " + NewPRSaleRequest.getSeq());
                System.out.println("BSOFTLAB. NewPRSaleRequest.returnTicketId: " + NewPRSaleRequest.getReturnTicketId());
                System.out.println("BSOFTLAB. NewPRSaleRequest.crn: " + NewPRSaleRequest.getCrn());
                System.out.println("BSOFTLAB. NewPRSaleRequest.cashAmountForReturn: " + NewPRSaleRequest.getCashAmountForReturn());
                System.out.println("BSOFTLAB. NewPRSaleRequest.cardAmountForReturn: " + NewPRSaleRequest.getCardAmountForReturn());
                if((NewPRSaleRequest.getReturnItemList() != null)
                        && (NewPRSaleRequest.getReturnItemList().size() > 0)) {
                    System.out.println("BSOFTLAB. NewPRSaleRequest.returnItemList");
                    for (ReturnItem returnItem : NewPRSaleRequest.getReturnItemList()) {
                        if(!(returnItem == null)) {
                            System.out.println("BSOFTLAB. NewPRSaleRequest.returnItemList.returnItem.rpid: " + returnItem.getRpid());
                            System.out.println("BSOFTLAB. NewPRSaleRequest.returnItemList.returnItem.quantity: " + returnItem.getQuantity());
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("BSOFTLAB. NewPRSaleRequest.returnItemList is empty object !!!");
                }
            } else {
                System.out.println("BSOFTLAB. NewPRSaleRequest is null pointer object !!!");
            }

            NewPRSaleResponse newPRSaleResponse = new NewPRSaleResponse();
            newPRSaleResponse.setRseq(3030);
            newPRSaleResponse.setCrn("03030303");
            newPRSaleResponse.setSn("30303030");
            newPRSaleResponse.setTin("00330033");
            newPRSaleResponse.setTaxpayer("BSOFTLAB03");
            newPRSaleResponse.setAddress("Yerevan. Norashkharhyan 17, 75.03");
            newPRSaleResponse.setTime(33003300.00);
            newPRSaleResponse.setFiscal("33303330");
            newPRSaleResponse.setLottery("33333333");
            newPRSaleResponse.setPrize(0);
            newPRSaleResponse.setTotal(300000.00);
            newPRSaleResponse.setChange(30000.00);
            newPRSaleResponse.setQr("Receipt ID:170, " + "TIN:00330033, " + "CRN:03030303");
            newPRSaleResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            NewPRSaleResponseWriterXML newPRSaleResponseWriterXML = new NewPRSaleResponseWriterXML();
            newPRSaleResponseWriterXML.setFileName("BSOFTLABECRDATA/NewPRSaleResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newPRSaleResponseWriterXML.getFileName());
            newPRSaleResponseWriterXML.writeFile(newPRSaleResponse);
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