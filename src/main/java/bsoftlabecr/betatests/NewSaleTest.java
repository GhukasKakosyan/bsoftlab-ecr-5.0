package bsoftlabecr.betatests;

import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.Item;

import bsoftlabecr.exception.ECRException;
import bsoftlabecr.request.receipt.sale.NewSaleRequest;
import bsoftlabecr.response.receipt.sale.NewSaleResponse;

import bsoftlabecr.xml.reader.constants.ConstantsReaderXML;
import bsoftlabecr.xml.reader.receipt.sale.NewSaleRequestReaderXML;
import bsoftlabecr.xml.writer.receipt.sale.NewSaleResponseWriterXML;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class NewSaleTest {
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
            NewSaleRequestReaderXML newSaleRequestReaderXML = new NewSaleRequestReaderXML();
            newSaleRequestReaderXML.setFileName("BSOFTLABECRDATA/NewSaleRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newSaleRequestReaderXML.getFileName());
            NewSaleRequest newSaleRequest = newSaleRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");
            System.out.println();
            if(!(newSaleRequest == null)) {
                System.out.println("BSOFTLAB. newSaleRequest.seq: " + newSaleRequest.getSeq());
                System.out.println("BSOFTLAB. newSaleRequest.paidAmount: " + newSaleRequest.getPaidAmount());
                System.out.println("BSOFTLAB. newSaleRequest.paidAmountCard: " + newSaleRequest.getPaidAmountCard());
                System.out.println("BSOFTLAB. newSaleRequest.partialAmount: " + newSaleRequest.getPartialAmount());
                System.out.println("BSOFTLAB. newSaleRequest.prePaymentAmount: " + newSaleRequest.getPrePaymentAmount());
                System.out.println("BSOFTLAB. newSaleRequest.mode: " + newSaleRequest.getMode());
                System.out.println("BSOFTLAB. newSaleRequest.useExtPOS: " + newSaleRequest.isUseExtPOS());
                System.out.println();
                if((newSaleRequest.getItems() != null) && (newSaleRequest.getItems().size() > 0)) {
                    System.out.println("BSOFTLAB. newSaleRequest.items");
                    for (Item item : newSaleRequest.getItems()) {
                        if (!(item == null)) {
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.dep: " + item.getDep());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.adgCode: " + item.getAdgCode());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.productCode: " + item.getProductCode());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.productName: " + item.getProductName());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.unit: " + item.getUnit());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.qty: " + item.getQty());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.price: " + item.getPrice());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.discount: " + item.getDiscount());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.discountType: " + item.getDiscountType());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.additionalDiscount: " + item.getAdditionalDiscount());
                            System.out.println("BSOFTLAB. newSaleRequest.items.item.additionalDiscountType: " + item.getAdditionalDiscountType());
                        }
                    }
                } else {
                    System.out.println("BSOFTLAB. newSaleRequest.items is empty object !!!");
                }
            } else {
                System.out.println("BSOFTLAB. newSaleRequest is null pointer object !!!");
            }

            NewSaleResponse newSaleResponse = new NewSaleResponse();
            newSaleResponse.setRseq(1010);
            newSaleResponse.setCrn("01010101");
            newSaleResponse.setSn("10101010");
            newSaleResponse.setTin("00110011");
            newSaleResponse.setTaxpayer("BSOFTLAB01");
            newSaleResponse.setAddress("Հայաստանի հանրապետություն. Երեվան. Նորաշխարհյան 17. 75");
            newSaleResponse.setTime(11001100.10);
            newSaleResponse.setFiscal("11101110");
            newSaleResponse.setLottery("11111111");
            newSaleResponse.setPrize(0);
            newSaleResponse.setTotal(100000.00);
            newSaleResponse.setChange(10000.00);
            newSaleResponse.setQr("Receipt ID:171, " + "TIN:00110011, " + "CRN:01010101");
            newSaleResponse.setResponseCode(200);

            System.out.println("BSOFTLAB. Trying to write response to XML file...");
            NewSaleResponseWriterXML newSaleResponseWriterXML = new NewSaleResponseWriterXML();
            newSaleResponseWriterXML.setFileName("BSOFTLABECRDATA/NewSaleResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newSaleResponseWriterXML.getFileName());
            newSaleResponseWriterXML.writeFile(newSaleResponse);
            System.out.println("BSOFTLAB. Response is written into XML file successfully !");
            Thread.sleep(5000);

        } catch(XMLStreamException xmlStreamException) {
            System.out.println("XMLStreamException: " + xmlStreamException.toString());
        } catch(FileNotFoundException fileNotFoundException) {
            System.out.println("FileNotFoundException: " + fileNotFoundException.toString());
        } catch(InterruptedException interruptedException) {
            System.out.println("InterruptedException: " + interruptedException.toString());
        } catch (JsonProcessingException jsonProcessingException) {
            System.out.println("JsonProcessingException: " + jsonProcessingException.toString());
        } catch(IOException ioException) {
            System.out.println("IOException: " + ioException.toString());
        } catch(ECRException ecrException) {
            System.out.println("ECRException: " + ecrException.toString());
        }
    }
}