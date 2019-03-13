package bsoftlabecr.xml.reader.report;

import bsoftlabecr.exception.XmlFileInvalidException;
import bsoftlabecr.exception.XmlFileNotFoundException;
import bsoftlabecr.request.report.FiscalReportRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class FiscalReportRequestReaderXml {

    private static final String REQUEST = "FiscalReportRequest";
    private static final String REQUEST_STARTDATE = "startDate";
    private static final String REQUEST_ENDDATE = "endDate";
    private static final String REQUEST_REPORTTYPE = "reportType";
    private static final String REQUEST_CASHIERID = "cashierId";
    private static final String REQUEST_DEPTID = "deptId";
    private static final String REQUEST_TRANSACTIONTYPEID = "transactionTypeId";

    private String fileName = null;

    public FiscalReportRequestReaderXml() {}
    public FiscalReportRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FiscalReportRequest readFile()
            throws XmlFileInvalidException, XmlFileNotFoundException {

        Date startDate = null;
        Date endDate = null;
        Integer reportType = null;
        Integer cashierId = null;
        Integer deptId = null;
        Integer transactionTypeId = null;

        FiscalReportRequest fiscalReportRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {

                    fiscalReportRequest = new FiscalReportRequest();
                    fiscalReportRequest.setStartDate(startDate);
                    fiscalReportRequest.setEndDate(endDate);
                    fiscalReportRequest.setReportType(reportType);
                    fiscalReportRequest.setCashierId(cashierId);
                    fiscalReportRequest.setDeptId(deptId);
                    fiscalReportRequest.setTransactionTypeId(transactionTypeId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_STARTDATE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String startDateString = xmlEvent.asCharacters().getData();
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                        startDate = simpleDateFormat.parse(startDateString);
                    } catch (ParseException parseException) {
                        startDate = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_STARTDATE)) {
                    if (fiscalReportRequest != null) fiscalReportRequest.setStartDate(startDate);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_ENDDATE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String endDateString = xmlEvent.asCharacters().getData();
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                        endDate = simpleDateFormat.parse(endDateString);
                    } catch (ParseException parseException) {
                        endDate = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_ENDDATE)) {
                    if (fiscalReportRequest != null) fiscalReportRequest.setEndDate(endDate);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_REPORTTYPE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String reportTypeString = xmlEvent.asCharacters().getData();
                    try {
                        reportType = Integer.parseInt(reportTypeString);
                    } catch (NumberFormatException numberFormatException) {
                        reportType = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_REPORTTYPE)) {
                    if (fiscalReportRequest != null) fiscalReportRequest.setReportType(reportType);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CASHIERID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String cashierIdString = xmlEvent.asCharacters().getData();
                    try {
                        cashierId = Integer.parseInt(cashierIdString);
                    } catch (NumberFormatException numberFormatException) {
                        cashierId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CASHIERID)) {
                    if (fiscalReportRequest != null) fiscalReportRequest.setCashierId(cashierId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_DEPTID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String deptIdString = xmlEvent.asCharacters().getData();
                    try {
                        deptId = Integer.parseInt(deptIdString);
                    } catch (NumberFormatException numberFormatException) {
                        deptId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_DEPTID)) {
                    if (fiscalReportRequest != null) fiscalReportRequest.setDeptId(deptId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_TRANSACTIONTYPEID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String transactionTypeIdString = xmlEvent.asCharacters().getData();
                    try {
                        transactionTypeId = Integer.parseInt(transactionTypeIdString);
                    } catch (NumberFormatException numberFormatException) {
                        transactionTypeId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_TRANSACTIONTYPEID)) {
                    if (fiscalReportRequest != null) fiscalReportRequest.setTransactionTypeId(transactionTypeId);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                    break;
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {
            throw new XmlFileNotFoundException(this.fileName);
        } catch (XMLStreamException xmlStreamException) {
            throw new XmlFileInvalidException(this.fileName);
        }

        if (fiscalReportRequest == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        if (fiscalReportRequest.getStartDate() == null ||
                fiscalReportRequest.getEndDate() == null ||
                fiscalReportRequest.getReportType() == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        return fiscalReportRequest;
    }
}