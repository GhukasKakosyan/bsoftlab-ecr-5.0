package bsoftlabecr;

import bsoftlabecr.client.CashRegisterClient;
import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.HeaderFooter;

import bsoftlabecr.exception.ConnectionException;
import bsoftlabecr.exception.InitialisationException;
import bsoftlabecr.exception.OperationException;
import bsoftlabecr.exception.XmlFileInvalidException;
import bsoftlabecr.exception.XmlFileNotFoundException;
import bsoftlabecr.exception.XmlFileWriteException;

import bsoftlabecr.request.cashier.LoginCashierRequest;
import bsoftlabecr.request.cashier.LogoutCashierRequest;
import bsoftlabecr.request.headerfooter.HeaderFooterRequest;
import bsoftlabecr.request.receipt.sale.NewSaleRequest;

import bsoftlabecr.response.cashier.LoginCashierResponse;
import bsoftlabecr.response.cashier.LogoutCashierResponse;
import bsoftlabecr.response.headerfooter.HeaderFooterResponse;
import bsoftlabecr.response.receipt.sale.NewSaleResponse;

import bsoftlabecr.xml.reader.receipt.sale.NewSaleRequestReaderXml;
import bsoftlabecr.xml.reader.constants.ConstantsReaderXml;
import bsoftlabecr.xml.writer.receipt.sale.NewSaleResponseWriterXml;

import java.util.ArrayList;
import java.util.List;

public class NewSale {
    private static final String ConstantsXmlFileName =
            "bsoftlabecrdata/Constants.xml";
    private static final String NewSaleRequestXmlFileName =
            "bsoftlabecrdata/NewSaleRequest.xml";
    private static final String NewSaleResponseXmlFileName =
            "bsoftlabecrdata/NewSaleResponse.xml";

    public static void main(String[] args) {
        Constants constants;
        try {
            System.out.println("BSOFTLAB. Trying to read ECR constants... ");
            ConstantsReaderXml constantsReaderXml = new ConstantsReaderXml();
            constantsReaderXml.setFileName(ConstantsXmlFileName);
            System.out.println("BSOFTLAB. Reading ECR constants from file: " +
                    constantsReaderXml.getFileName());
            constants = constantsReaderXml.readFile();
            System.out.println("BSOFTLAB. ECR constants is read successfully !");
            System.out.println("BSOFTLAB. constants.crn: " + constants.getCrn());
            System.out.println("BSOFTLAB. constants.ip: " + constants.getIp());
            System.out.println("BSOFTLAB. constants.port: " + constants.getPort());
            System.out.println("BSOFTLAB. constants.password: " + constants.getPassword());
            System.out.println("BSOFTLAB. constants.cashierId: " + constants.getCashierId());
            System.out.println("BSOFTLAB. constants.cashierPassword: " + constants.getCashierPassword());
            System.out.println();
        } catch (XmlFileNotFoundException | XmlFileInvalidException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        CashRegisterClient cashRegisterClient;
        try {
            System.out.println("BSOFTLAB. Trying to initialize ECR...");
            cashRegisterClient = new CashRegisterClient(constants);
            byte[] passwordKeyBytes = cashRegisterClient.getPasswordKey();
            System.out.println("BSOFTLAB. passwordKey is: ");
            for (byte passwordKeyByte : passwordKeyBytes) {
                System.out.print(passwordKeyByte);
            }
            System.out.println();
            System.out.println("BSOFTLAB. ECR is initialized successfully ! ");
        } catch (InitialisationException initialisationException) {
            System.out.println(initialisationException.getMessage());
            return;
        }

        try {
            System.out.println("BSOFTLAB. Trying to connect to ECR... ");
            cashRegisterClient.connect();
            System.out.println("BSOFTLAB. Connection to ECR is created successfully ! ");
            System.out.println();
        } catch (ConnectionException connectionException) {
            System.out.println(connectionException.getMessage());
            return;
        }

        try {
            System.out.println("BSOFTLAB. Trying to login cashier... ");
            LoginCashierRequest loginCashierRequest = new LoginCashierRequest();
            loginCashierRequest.setPassword(constants.getPassword());
            loginCashierRequest.setCashier(constants.getCashierId());
            loginCashierRequest.setPin(constants.getCashierPassword());
            LoginCashierResponse loginCashierResponse =
                    cashRegisterClient.getLoginCashierResponse(loginCashierRequest);
            cashRegisterClient.setSessionKey(loginCashierResponse.getKeyBytes());
            byte[] sessionKeyBytes = cashRegisterClient.getSessionKey();
            System.out.println("BSOFTLAB. sessionKey is: ");
            for (byte sessionKeyByte : sessionKeyBytes) {
                System.out.print(sessionKeyByte);
            }
            System.out.println();
            System.out.println("BSOFTLAB. Cashier is logged in successfully: " +
                    loginCashierResponse.getResponseCode());
            System.out.println();
        } catch (OperationException operationException) {
            System.out.println(operationException.getMessage());
            return;
        }

        try {
            System.out.println("BSOFTLAB. Trying to setup header of sale receipt...");
            List<HeaderFooter> headerList = new ArrayList<>();
            HeaderFooter header = new HeaderFooter();
            header.setAlign(HeaderFooter.ALIGNMENT_CENTER);
            header.setBold(HeaderFooter.HEADER_STYLE_BOLD);
            header.setFsize(HeaderFooter.FONT_SIZE_MEDIUM);
            header.setText("ՇՆՈՐՀԱԿԱԼՈՒԹՅՈՒՆ ԳՆՈՒՄՆԵՐԻ ՀԱՄԱՐ");
            headerList.add(header);
            System.out.println("BSOFTLAB. Header of sale receipt is initialized successfully !");
            System.out.println("BSOFTLAB. Trying to setup footer of sale receipt...");
            List<HeaderFooter> footerList = new ArrayList<>();
            HeaderFooter footer = new HeaderFooter();
            footer.setAlign(HeaderFooter.ALIGNMENT_CENTER);
            footer.setBold(HeaderFooter.HEADER_STYLE_BOLD);
            footer.setFsize(HeaderFooter.FONT_SIZE_MEDIUM);
            footer.setText("ՀԱՄԵՑԵՔ ԳՆՈՒՄՆԵՐԻ ԿՐԿԻՆ");
            footerList.add(footer);
            System.out.println("BSOFTLAB. Footer of sale receipt is initialized successfully !");
            System.out.println("BSOFTLAB. Trying to send request to ECR...");
            HeaderFooterRequest headerFooterRequest = new HeaderFooterRequest();
            headerFooterRequest.setHeaders(headerList);
            headerFooterRequest.setFooters(footerList);
            HeaderFooterResponse headerFooterResponse;
            headerFooterResponse = cashRegisterClient.setupHeaderFooter(headerFooterRequest);
            System.out.println("BSOFTLAB. Request is sent to ECR successfully !");
            System.out.println("BSOFTLAB. Response is received from ECR successfully: " +
                    headerFooterResponse.getResponseCode());
            System.out.println();
        } catch (OperationException operationException) {
            System.out.println(operationException.getMessage());
            return;
        }

        NewSaleRequest newSaleRequest;
        try {
            System.out.println("BSOFTLAB. Trying to read request from XML file... ");
            NewSaleRequestReaderXml newSaleRequestReaderXml = new NewSaleRequestReaderXml();
            newSaleRequestReaderXml.setFileName(NewSaleRequestXmlFileName);
            System.out.println("BSOFTLAB. Reading request from file: " +
                    newSaleRequestReaderXml.getFileName());
            newSaleRequest = newSaleRequestReaderXml.readFile();
            newSaleRequest.setSeq(cashRegisterClient.getSeq());
            System.out.println("BSOFTLAB. Request is read from XML file successfully !");
            System.out.println();
        } catch (XmlFileNotFoundException | XmlFileInvalidException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        NewSaleResponse newSaleResponse;
        try {
            System.out.println("BSOFTLAB. Trying to send request to ECR... ");
            newSaleResponse = cashRegisterClient.getNewSaleResponse(newSaleRequest);
            System.out.println("BSOFTLAB. Request is sent to ECR successfully !");
            System.out.println("BSOFTLAB. Response is received from ECR successfully: " +
                    newSaleResponse.getResponseCode());
            System.out.println();
        } catch (OperationException operationException) {
            System.out.println(operationException.getMessage());
            return;
        }

        try {
            System.out.println("BSOFTLAB. Trying to write response into XML file... ");
            NewSaleResponseWriterXml newSaleResponseWriterXml =
                    new NewSaleResponseWriterXml();
            newSaleResponseWriterXml.setFileName(NewSaleResponseXmlFileName);
            System.out.println("BSOFTLAB. Writing response into file: " +
                    newSaleResponseWriterXml.getFileName());
            newSaleResponseWriterXml.writeFile(newSaleResponse);
            System.out.println("BSOFTLAB. Response is written into XML file successfully !");
            System.out.println();
        } catch (XmlFileWriteException xmlFileWriteException) {
            System.out.println(xmlFileWriteException.getMessage());
            return;
        }

        try {
            System.out.println("BSOFTLAB. Trying to logout cashier from ECR...");
            LogoutCashierRequest logoutCashierRequest = new LogoutCashierRequest();
            logoutCashierRequest.setSeq(cashRegisterClient.getSeq());
            LogoutCashierResponse logoutCashierResponse =
                    cashRegisterClient.getLogoutCashierResponse(logoutCashierRequest);
            System.out.println("BSOFTLAB. Cashier is logged out from ECR successfully: " +
                    logoutCashierResponse.getResponseCode());
            System.out.println("BSOFTLAB. Trying to close connection with ECR...");
            cashRegisterClient.disconnect();
            System.out.println("BSOFTLAB. Connection with ECR is closed successfully !");
            Thread.sleep(5000);

        } catch(OperationException | ConnectionException exception) {
            System.out.println(exception.getMessage());
        } catch(InterruptedException interruptedException) {
            System.out.println(interruptedException.toString());
        }
    }
}