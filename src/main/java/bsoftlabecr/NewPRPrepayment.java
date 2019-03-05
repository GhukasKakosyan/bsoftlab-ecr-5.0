package bsoftlabecr;

import bsoftlabecr.client.CashRegisterClient;
import bsoftlabecr.entity.Cashier;
import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.Department;
import bsoftlabecr.exception.ECRException;

import bsoftlabecr.request.cashier.CashiersAndDepsRequest;
import bsoftlabecr.request.cashier.LoginCashierRequest;
import bsoftlabecr.request.cashier.LogoutCashierRequest;
import bsoftlabecr.request.receipt.returns.prepayment.NewPRPrepaymentRequest;

import bsoftlabecr.response.cashier.CashiersAndDepsResponse;
import bsoftlabecr.response.cashier.LoginCashierResponse;
import bsoftlabecr.response.cashier.LogoutCashierResponse;
import bsoftlabecr.response.receipt.returns.prepayment.NewPRPrepaymentResponse;

import bsoftlabecr.xml.reader.receipt.returns.prepayment.NewPRPrepaymentRequestReaderXML;
import bsoftlabecr.xml.reader.constants.ConstantsReaderXML;
import bsoftlabecr.xml.writer.receipt.returns.prepayment.NewPRPrepaymentResponseWriterXML;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.stream.XMLStreamException;

public class NewPRPrepayment {
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

            System.out.println("BSOFTLAB. Trying to initialize ECR...");
            CashRegisterClient cashRegisterClient = new CashRegisterClient(constants.getPassword());
            byte[] passwordKeyBytes = cashRegisterClient.getPasswordKey();
            System.out.println("BSOFTLAB. passwordKey is: ");
            for(byte passwordKeyByte : passwordKeyBytes) {
                System.out.print(passwordKeyByte);
            }
            System.out.println();
            System.out.println("BSOFTLAB. ECR is initialized successfully ! ");
            System.out.println("BSOFTLAB. Trying to connect to ECR... ");
            cashRegisterClient.connect(constants.getIp(), constants.getPort());
            System.out.println("BSOFTLAB. Connection to ECR is created successfully ! ");
            System.out.println();

            System.out.println("BSOFTLAB. Trying to get list of cashiers and departments... ");
            CashiersAndDepsRequest cashiersAndDepsRequest = new CashiersAndDepsRequest();
            cashiersAndDepsRequest.setPassword(constants.getPassword());
            CashiersAndDepsResponse cashiersAndDepsResponse;
            cashiersAndDepsResponse = cashRegisterClient.getCashiersAndDepsResponse(cashiersAndDepsRequest);
            List<Cashier> cashierList = cashiersAndDepsResponse.getC();
            List<Department> departmentList = cashiersAndDepsResponse.getD();
            for(Department department : departmentList) {
                System.out.println("BSOFTLAB. departmentList.department: "
                        + department.getId() + ", " + department.getName());
            }
            for(Cashier cashier : cashierList) {
                System.out.println("BSOFTLAB. cashierList.cashier: "
                        + cashier.getId() + ", " + cashier.getName());
            }
            System.out.println("BSOFTLAB. List of cashiers and departments is got successfully: " + cashiersAndDepsResponse.getResponseCode());
            System.out.println();

            System.out.println("BSOFTLAB. Trying to login cashier... ");
            LoginCashierRequest loginCashierRequest = new LoginCashierRequest();
            loginCashierRequest.setPassword(constants.getPassword());
            loginCashierRequest.setCashier(constants.getCashierId());
            loginCashierRequest.setPin(constants.getCashierPassword());
            LoginCashierResponse loginCashierResponse;
            loginCashierResponse = cashRegisterClient.getLoginCashierResponse(loginCashierRequest);
            cashRegisterClient.setSessionKey(loginCashierResponse.getKeyBytes());
            byte[] sessionKeyBytes = cashRegisterClient.getSessionKey();
            System.out.println("BSOFTLAB. sessionKey is: ");
            for(byte sessionKeyByte : sessionKeyBytes) {
                System.out.print(sessionKeyByte);
            }
            System.out.println();
            System.out.println("BSOFTLAB. Cashier is logged in successfully: " + loginCashierResponse.getResponseCode());
            System.out.println();

            System.out.println("BSOFTLAB. Trying to read request from XML file... ");
            NewPRPrepaymentRequestReaderXML newPRPrepaymentRequestReaderXML;
            newPRPrepaymentRequestReaderXML = new NewPRPrepaymentRequestReaderXML();
            newPRPrepaymentRequestReaderXML.setFileName("BSOFTLABECRDATA/NewPRPrepaymentRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + newPRPrepaymentRequestReaderXML.getFileName());
            NewPRPrepaymentRequest newPRPrepaymentRequest = newPRPrepaymentRequestReaderXML.readFile();
            newPRPrepaymentRequest.setSeq(cashRegisterClient.getSeq());
            System.out.println("BSOFTLAB. Request is read from XML file successfully !");
            System.out.println();

            System.out.println("BSOFTLAB. Trying to send request to ECR... ");
            NewPRPrepaymentResponse newPRPrepaymentResponse;
            newPRPrepaymentResponse = cashRegisterClient.getNewPRPrepaymentResponse(newPRPrepaymentRequest);
            System.out.println("BSOFTLAB. Request is sent to ECR successfully !");
            System.out.println("BSOFTLAB. Response is received from ECR successfully: " + newPRPrepaymentResponse.getResponseCode());
            System.out.println();

            System.out.println("BSOFTLAB. Trying to write response into XML file... ");
            NewPRPrepaymentResponseWriterXML newPRPrepaymentResponseWriterXML;
            newPRPrepaymentResponseWriterXML = new NewPRPrepaymentResponseWriterXML();
            newPRPrepaymentResponseWriterXML.setFileName("BSOFTLABECRDATA/NewPRPrepaymentResponse.xml");
            System.out.println("BSOFTLAB. Writing response into file: " + newPRPrepaymentResponseWriterXML.getFileName());
            newPRPrepaymentResponseWriterXML.writeFile(newPRPrepaymentResponse);
            System.out.println("BSOFTLAB. Response is written into XML file successfully !");
            System.out.println();

            System.out.println("BSOFTLAB. Trying to logout cashier from ECR... ");
            LogoutCashierRequest logoutCashierRequest = new LogoutCashierRequest();
            logoutCashierRequest.setSeq(cashRegisterClient.getSeq());
            LogoutCashierResponse logoutCashierResponse;
            logoutCashierResponse = cashRegisterClient.getLogoutCashierResponse(logoutCashierRequest);
            System.out.println("BSOFTLAB. Cashier is logged out from ECR successfully: " + logoutCashierResponse.getResponseCode());
            System.out.println("BSOFTLAB. Trying to close connection with ECR... ");
            cashRegisterClient.disconnect();
            System.out.println("BSOFTLAB. Connection with ECR is closed successfully !");
            Thread.sleep(5000);

        } catch(XMLStreamException xmlStreamException) {
            System.out.println("XMLStreamException: " + xmlStreamException.toString());
        } catch(FileNotFoundException fileNotFoundException) {
            System.out.println("FileNotFoundException: " + fileNotFoundException.toString());
        } catch(InvalidKeyException invalidKeyException) {
            System.out.println("InvalidKeyException: " + invalidKeyException.toString());
        } catch(InterruptedException interruptedException) {
            System.out.println("InterruptedException: " + interruptedException.toString());
        } catch(NoSuchAlgorithmException noSuchAlgorithmException) {
            System.out.println("NoSuchAlgorithmException: " + noSuchAlgorithmException.toString());
        } catch(BadPaddingException badPaddingException) {
            System.out.println("BadPaddingException: " + badPaddingException.toString());
        } catch(IllegalBlockSizeException illegalBlockSizeException) {
            System.out.println("IllegalBlockSizeException: " + illegalBlockSizeException.toString());
        } catch(NoSuchPaddingException noSuchPaddingException) {
            System.out.println("NoSuchPaddingException: " + noSuchPaddingException.toString());
        } catch (JsonProcessingException jsonProcessingException) {
            System.out.println("JsonProcessingException: " + jsonProcessingException.toString());
        } catch(IOException ioException) {
            System.out.println("IOException: " + ioException.toString());
        } catch(ECRException ecrException) {
            System.out.println("ECRException: " + ecrException.toString());
        }
    }
}