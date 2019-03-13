package bsoftlabecr.xml.reader.receipt.returns.prepayment;

import bsoftlabecr.exception.XmlFileInvalidException;
import bsoftlabecr.exception.XmlFileNotFoundException;
import bsoftlabecr.request.receipt.returns.prepayment.NewPartialReturnPrepaymentRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.math.BigDecimal;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewPartialReturnPrepaymentRequestReaderXml {
    private static final String REQUEST = "NewPartialReturnPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RETURNTICKETID = "returnTicketId";
    private static final String REQUEST_CASHAMOUNTFORRETURN = "cashAmountForReturn";
    private static final String REQUEST_CARDAMOUNTFORRETURN = "cardAmountForReturn";

    private String fileName = null;

    public NewPartialReturnPrepaymentRequestReaderXml() {}
    public NewPartialReturnPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public NewPartialReturnPrepaymentRequest readFile()
            throws XmlFileNotFoundException, XmlFileInvalidException {

        Integer returnTicketId = null;
        Integer seq = null;
        String crn = null;
        BigDecimal cashAmountForReturn = null;
        BigDecimal cardAmountForReturn = null;

        NewPartialReturnPrepaymentRequest newPartialReturnPrepaymentRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                    newPartialReturnPrepaymentRequest = new NewPartialReturnPrepaymentRequest();
                    newPartialReturnPrepaymentRequest.setSeq(seq);
                    newPartialReturnPrepaymentRequest.setCrn(crn);
                    newPartialReturnPrepaymentRequest.setReturnTicketId(returnTicketId);
                    newPartialReturnPrepaymentRequest.setCashAmountForReturn(cashAmountForReturn);
                    newPartialReturnPrepaymentRequest.setCardAmountForReturn(cardAmountForReturn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String seqString = xmlEvent.asCharacters().getData();
                    try {
                        seq = Integer.parseInt(seqString);
                    } catch (NumberFormatException numberFormatException) {
                        seq = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                    if (newPartialReturnPrepaymentRequest != null) newPartialReturnPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    if (newPartialReturnPrepaymentRequest != null) newPartialReturnPrepaymentRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String returnTicketIdString = xmlEvent.asCharacters().getData();
                    try {
                        returnTicketId = Integer.parseInt(returnTicketIdString);
                    } catch (NumberFormatException numberFormatException) {
                        returnTicketId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                    if (newPartialReturnPrepaymentRequest != null)
                        newPartialReturnPrepaymentRequest.setReturnTicketId(returnTicketId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CASHAMOUNTFORRETURN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String cashAmountForReturnString = xmlEvent.asCharacters().getData();
                    try {
                        double cashAmountForReturnDouble = Double.parseDouble(cashAmountForReturnString);
                        cashAmountForReturn = BigDecimal.valueOf(cashAmountForReturnDouble);
                    } catch (NumberFormatException numberFormatException) {
                        cashAmountForReturn = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CASHAMOUNTFORRETURN)) {
                    if (newPartialReturnPrepaymentRequest != null)
                        newPartialReturnPrepaymentRequest.setCashAmountForReturn(cashAmountForReturn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CARDAMOUNTFORRETURN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String cardAmountForReturnString = xmlEvent.asCharacters().getData();
                    try {
                        double cardAmountForReturnDouble = Double.parseDouble(cardAmountForReturnString);
                        cardAmountForReturn = BigDecimal.valueOf(cardAmountForReturnDouble);
                    } catch (NumberFormatException numberFormatException) {
                        cardAmountForReturn = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CARDAMOUNTFORRETURN)) {
                    if (newPartialReturnPrepaymentRequest != null)
                        newPartialReturnPrepaymentRequest.setCardAmountForReturn(cardAmountForReturn);
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

        if (newPartialReturnPrepaymentRequest == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        if (newPartialReturnPrepaymentRequest.getSeq() == null ||
                newPartialReturnPrepaymentRequest.getCrn() == null ||
                newPartialReturnPrepaymentRequest.getReturnTicketId() == null ||
                newPartialReturnPrepaymentRequest.getCashAmountForReturn() == null ||
                newPartialReturnPrepaymentRequest.getCardAmountForReturn() == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        return newPartialReturnPrepaymentRequest;
    }
}