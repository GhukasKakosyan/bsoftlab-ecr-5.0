package bsoftlabecr;

import bsoftlabecr.client.CashRegisterClient;

import bsoftlabecr.entity.Cashier;
import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.Department;

import bsoftlabecr.exception.CashRegisterException;
import bsoftlabecr.exception.ConnectionException;
import bsoftlabecr.exception.InitialisationException;
import bsoftlabecr.exception.OperationException;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.exception.XmlFileWriteException;

import bsoftlabecr.request.cashier.CashiersAndDepsRequest;
import bsoftlabecr.request.cashier.LoginCashierRequest;
import bsoftlabecr.request.cashier.LogoutCashierRequest;
import bsoftlabecr.request.receipt.returns.sale.NewOriginalReturnSaleRequest;

import bsoftlabecr.response.cashier.CashiersAndDepsResponse;
import bsoftlabecr.response.cashier.LoginCashierResponse;
import bsoftlabecr.response.cashier.LogoutCashierResponse;
import bsoftlabecr.response.general.CashRegisterResponse;
import bsoftlabecr.response.receipt.returns.sale.NewOriginalReturnSaleResponse;

import bsoftlabecr.xml.reader.request.returns.sale.NewOriginalReturnSaleRequestReaderXml;
import bsoftlabecr.xml.reader.constants.ConstantsReaderXml;

import bsoftlabecr.xml.writer.response.general.CashRegisterResponseWriterXml;
import bsoftlabecr.xml.writer.response.returns.sale.NewOriginalReturnSaleResponseWriterXml;

import java.util.List;

public class NewOriginalReturnSale {
    private static final String ConstantsXmlFileName =
            "bsoftlabecrdata/Constants.xml";
    private static final String CashRegisterResponseXmlFileName =
            "bsoftlabecrdata/CashRegisterResponse.xml";
    private static final String NewOriginalReturnSaleRequestXmlFileName =
            "bsoftlabecrdata/NewOriginalReturnSaleRequest.xml";
    private static final String NewOriginalReturnSaleResponseXmlFileName =
            "bsoftlabecrdata/NewOriginalReturnSaleResponse.xml";

    public static void main(String[] args) {
        try {
            try {
                System.out.println("BSOFTLAB. Trying to read ECR constants... ");
                ConstantsReaderXml constantsReaderXml =
                        new ConstantsReaderXml(ConstantsXmlFileName);
                System.out.println("BSOFTLAB. Reading ECR constants from file: " +
                        constantsReaderXml.getFileName());
                Constants constants = constantsReaderXml.read();
                System.out.println("BSOFTLAB. ECR constants is read successfully !");
                System.out.println("BSOFTLAB. constants.crn: " + constants.getCrn());
                System.out.println("BSOFTLAB. constants.ip: " + constants.getIp());
                System.out.println("BSOFTLAB. constants.port: " + constants.getPort());
                System.out.println("BSOFTLAB. constants.password: " + constants.getPassword());
                System.out.println("BSOFTLAB. constants.cashierId: " + constants.getCashierId());
                System.out.println("BSOFTLAB. constants.cashierPassword: " + constants.getCashierPassword());
                System.out.println();

                System.out.println("BSOFTLAB. Trying to initialize ECR...");
                CashRegisterClient cashRegisterClient = new CashRegisterClient(constants);
                byte[] passwordKeyBytes = cashRegisterClient.getPasswordKey();
                System.out.println("BSOFTLAB. passwordKey is: ");
                for (byte passwordKeyByte : passwordKeyBytes) {
                    System.out.print(passwordKeyByte);
                }
                System.out.println();
                System.out.println("BSOFTLAB. ECR is initialized successfully ! ");

                System.out.println("BSOFTLAB. Trying to connect to ECR... ");
                cashRegisterClient.connect();
                System.out.println("BSOFTLAB. Connection to ECR is created successfully ! ");
                System.out.println();

                System.out.println("BSOFTLAB. Trying to get list of cashiers and departments... ");
                CashiersAndDepsRequest cashiersAndDepsRequest = new CashiersAndDepsRequest();
                cashiersAndDepsRequest.setPassword(constants.getPassword());
                CashiersAndDepsResponse cashiersAndDepsResponse = cashRegisterClient
                        .getCashiersAndDepsResponse(cashiersAndDepsRequest);
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
                System.out.println("BSOFTLAB. List of cashiers and departments is got successfully: " +
                        cashiersAndDepsResponse.getResponseCode());
                System.out.println();

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

                System.out.println("BSOFTLAB. Trying to read request from XML file... ");
                NewOriginalReturnSaleRequestReaderXml
                        newOriginalReturnSaleRequestReaderXml =
                        new NewOriginalReturnSaleRequestReaderXml(
                                NewOriginalReturnSaleRequestXmlFileName);
                System.out.println("BSOFTLAB. Reading request from file: " +
                        newOriginalReturnSaleRequestReaderXml.getFileName());
                NewOriginalReturnSaleRequest newOriginalReturnSaleRequest =
                        newOriginalReturnSaleRequestReaderXml.read();
                newOriginalReturnSaleRequest.setSeq(cashRegisterClient.getSeq());
                System.out.println("BSOFTLAB. Request is read from XML file successfully !");
                System.out.println();

                System.out.println("BSOFTLAB. Trying to send request to ECR... ");
                NewOriginalReturnSaleResponse newOriginalReturnSaleResponse =
                        cashRegisterClient.getNewOriginalReturnSaleResponse(
                                newOriginalReturnSaleRequest);
                System.out.println("BSOFTLAB. Request is sent to ECR successfully !");
                System.out.println("BSOFTLAB. Response is received from ECR successfully: " + newOriginalReturnSaleResponse.getResponseCode());
                System.out.println();

                System.out.println("BSOFTLAB. Trying to write response into XML file... ");
                NewOriginalReturnSaleResponseWriterXml
                        newOriginalReturnSaleResponseWriterXml =
                        new NewOriginalReturnSaleResponseWriterXml(
                                newOriginalReturnSaleResponse, NewOriginalReturnSaleResponseXmlFileName);
                System.out.println("BSOFTLAB. Writing response into file: " +
                        newOriginalReturnSaleResponseWriterXml.getFileName());
                newOriginalReturnSaleResponseWriterXml.write();
                System.out.println("BSOFTLAB. Response is written into XML file successfully !");
                System.out.println();

                System.out.println("BSOFTLAB. Trying to logout cashier from ECR... ");
                LogoutCashierRequest logoutCashierRequest = new LogoutCashierRequest();
                logoutCashierRequest.setSeq(cashRegisterClient.getSeq());
                LogoutCashierResponse logoutCashierResponse =
                        cashRegisterClient.getLogoutCashierResponse(logoutCashierRequest);
                System.out.println("BSOFTLAB. Cashier is logged out from ECR successfully: " +
                        logoutCashierResponse.getResponseCode());
                System.out.println("BSOFTLAB. Trying to close connection with ECR... ");
                cashRegisterClient.disconnect();
                System.out.println("BSOFTLAB. Connection with ECR is closed successfully !");

            } catch (XmlFileReadException | InitialisationException |
                    ConnectionException | OperationException |
                    XmlFileWriteException exception) {
                throw new CashRegisterException(exception.getResponseCode());
            }

        } catch (CashRegisterException cashRegisterException) {
            CashRegisterResponse cashRegisterResponse = new CashRegisterResponse();
            cashRegisterResponse.setResponseCode(cashRegisterException.getResponseCode());
            try {
                System.out.println("BSOFTLAB. Trying to write response into XML file... ");
                CashRegisterResponseWriterXml cashRegisterResponseWriterXml
                        = new CashRegisterResponseWriterXml(
                                cashRegisterResponse, CashRegisterResponseXmlFileName);
                System.out.println("BSOFTLAB. Writing response to file: " +
                        cashRegisterResponseWriterXml.getFileName());
                cashRegisterResponseWriterXml.write();
                System.out.println("BSOFTLAB. Response is written into XML file successfully !");
                System.out.println();
            } catch (XmlFileWriteException xmlFileWriteException) {
                System.out.println(xmlFileWriteException.getMessage());
            }
        }
    }
}