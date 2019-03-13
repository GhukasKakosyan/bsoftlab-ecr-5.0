package bsoftlabecr;

import bsoftlabecr.client.CashRegisterClient;
import bsoftlabecr.entity.Cashier;
import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.Department;
import bsoftlabecr.exception.*;

import bsoftlabecr.request.cashier.CashiersAndDepsRequest;
import bsoftlabecr.request.cashier.LoginCashierRequest;
import bsoftlabecr.request.cashier.LogoutCashierRequest;
import bsoftlabecr.request.report.FiscalReportRequest;

import bsoftlabecr.response.cashier.CashiersAndDepsResponse;
import bsoftlabecr.response.cashier.LoginCashierResponse;
import bsoftlabecr.response.cashier.LogoutCashierResponse;
import bsoftlabecr.response.report.FiscalReportResponse;

import bsoftlabecr.xml.reader.constants.ConstantsReaderXml;
import bsoftlabecr.xml.reader.report.FiscalReportRequestReaderXml;

import java.util.List;

public class FiscalReport {
    private static final String ConstantsXmlFileName =
            "bsoftlabecrdata/Constants.xml";
    private static final String FiscalReportRequestXmlFileName =
            "bsoftlabecrdata/FiscalReportRequest.xml";

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
            System.out.println("BSOFTLAB. Trying to get list of cashiers and departments... ");
            CashiersAndDepsRequest cashiersAndDepsRequest = new CashiersAndDepsRequest();
            cashiersAndDepsRequest.setPassword(constants.getPassword());
            CashiersAndDepsResponse cashiersAndDepsResponse =
                    cashRegisterClient.getCashiersAndDepsResponse(cashiersAndDepsRequest);
            List<Department> departmentList = cashiersAndDepsResponse.getD();
            for (Department department : departmentList) {
                System.out.println("BSOFTLAB. departmentList.department: "
                        + department.getId() + ", " + department.getName());
            }
            List<Cashier> cashierList = cashiersAndDepsResponse.getC();
            for (Cashier cashier : cashierList) {
                System.out.println("BSOFTLAB. cashierList.cashier: "
                        + cashier.getId() + ", " + cashier.getName());
            }
            System.out.println("BSOFTLAB. List of cashiers and departments is got successfully: " + cashiersAndDepsResponse.getResponseCode());
            System.out.println();
        } catch (OperationException operationException) {
            System.out.println(operationException.getMessage());
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

        FiscalReportRequest fiscalReportRequest;
        try {
            System.out.println("BSOFTLAB. Trying to read request from XML file... ");
            FiscalReportRequestReaderXml fiscalReportRequestReaderXml =
                    new FiscalReportRequestReaderXml();
            fiscalReportRequestReaderXml.setFileName(FiscalReportRequestXmlFileName);
            System.out.println("BSOFTLAB. Reading request from file: " +
                    fiscalReportRequestReaderXml.getFileName());
            fiscalReportRequest = fiscalReportRequestReaderXml.readFile();
            fiscalReportRequest.setSeq(cashRegisterClient.getSeq());
            System.out.println("BSOFTLAB. Request is read from XML file successfully !");
            System.out.println();
        } catch (XmlFileNotFoundException | XmlFileInvalidException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        try {
            System.out.println("BSOFTLAB. Trying to send request to ECR... ");
            FiscalReportResponse fiscalReportResponse =
                    cashRegisterClient.printFiscalReport(fiscalReportRequest);
            System.out.println("BSOFTLAB. Request is sent to ECR successfully !");
            System.out.println("BSOFTLAB. Fiscal report was printed by ECR successfully: " +
                    fiscalReportResponse.getResponseCode());
            System.out.println();
        } catch (OperationException operationException) {
            System.out.println(operationException.getMessage());
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