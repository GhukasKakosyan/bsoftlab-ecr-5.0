package bsoftlabecr.betatests;

import bsoftlabecr.entity.Constants;

import bsoftlabecr.request.report.FiscalReportRequest;
import bsoftlabecr.response.report.FiscalReportResponse;

import bsoftlabecr.xml.reader.constants.ConstantsReaderXML;
import bsoftlabecr.xml.reader.report.FiscalReportRequestReaderXML;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;

public class FiscalReportTest {
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
            FiscalReportRequestReaderXML fiscalReportRequestReaderXML = new FiscalReportRequestReaderXML();
            fiscalReportRequestReaderXML.setFileName("BSOFTLABECRDATA/FiscalReportRequest.xml");
            System.out.println("BSOFTLAB. Reading request from file: " + fiscalReportRequestReaderXML.getFileName());
            FiscalReportRequest fiscalReportRequest = fiscalReportRequestReaderXML.readFile();
            System.out.println("BSOFTLAB. Request is read successfully !");

            if (!(fiscalReportRequest == null)) {
                System.out.println("BSOFTLAB. fiscalReportRequest.seq: " + fiscalReportRequest.getSeq());
                System.out.println("BSOFTLAB. fiscalReportRequest.startDate: " + fiscalReportRequest.getStartDate());
                System.out.println("BSOFTLAB. fiscalReportRequest.endDate: " + fiscalReportRequest.getEndDate());
                System.out.println("BSOFTLAB. fiscalReportRequest.reportType: " + fiscalReportRequest.getReportType());
                System.out.println("BSOFTLAB. fiscalReportRequest.cashierId: " + fiscalReportRequest.getCashierId());
                System.out.println("BSOFTLAB. fiscalReportRequest.deptId: " + fiscalReportRequest.getDeptId());
                System.out.println("BSOFTLAB. fiscalReportRequest.transactionTypeId: " + fiscalReportRequest.getTransactionTypeId());
            } else {
                System.out.println("BSOFTLAB. fiscalReportRequest is null pointer object !!!");
            }

            FiscalReportResponse fiscalReportResponse = new FiscalReportResponse();
            fiscalReportResponse.setResponseCode(200);
            Thread.sleep(5000);

        } catch (XMLStreamException xmlStreamException) {
            System.out.println("XMLStreamException: " + xmlStreamException.toString());
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("FileNotFoundException: " + fileNotFoundException.toString());
        } catch (InterruptedException interruptedException) {
            System.out.println("InterruptedException: " + interruptedException.toString());
        }
    }
}