package bsoftlabecr.xml.reader.receipt.returns.prepayment;

import bsoftlabecr.request.receipt.returns.prepayment.NewPRPrepaymentRequest;

import java.io.*;
import java.math.BigDecimal;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewPRPrepaymentRequestReaderXML {
    private static final String REQUEST = "NewPRPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RETURNTICKETID = "returnTicketId";
    private static final String REQUEST_CASHAMOUNTFORRETURN = "cashAmountForReturn";
    private static final String REQUEST_CARDAMOUNTFORRETURN = "cardAmountForReturn";

    private String fileName = null;

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public NewPRPrepaymentRequest readFile() throws FileNotFoundException, XMLStreamException {
        int returnTicketId = -1;
        int seq = -1;
        String crn = null;
        BigDecimal cashAmountForReturn = null;
        BigDecimal cardAmountForReturn = null;

        NewPRPrepaymentRequest NewPRPrepaymentRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                NewPRPrepaymentRequest = new NewPRPrepaymentRequest();
                NewPRPrepaymentRequest.setSeq(seq);
                NewPRPrepaymentRequest.setCrn(crn);
                NewPRPrepaymentRequest.setReturnTicketId(returnTicketId);
                NewPRPrepaymentRequest.setCashAmountForReturn(cashAmountForReturn);
                NewPRPrepaymentRequest.setCardAmountForReturn(cardAmountForReturn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                xmlEvent = xmlEventReader.nextEvent();
                String seqString = xmlEvent.asCharacters().getData();
                try {
                    seq = Integer.parseInt(seqString);
                } catch(NumberFormatException numberFormatException) {
                    seq = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                if(NewPRPrepaymentRequest != null) NewPRPrepaymentRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(NewPRPrepaymentRequest != null) NewPRPrepaymentRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement () && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                xmlEvent = xmlEventReader.nextEvent();
                String returnTicketIdString = xmlEvent.asCharacters().getData();
                try {
                    returnTicketId = Integer.parseInt(returnTicketIdString);
                } catch(NumberFormatException numberFormatException) {
                    returnTicketId = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                if(NewPRPrepaymentRequest != null) NewPRPrepaymentRequest.setReturnTicketId(returnTicketId);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CASHAMOUNTFORRETURN)) {
                xmlEvent = xmlEventReader.nextEvent();
                String cashAmountForReturnString = xmlEvent.asCharacters().getData();
                try {
                    double cashAmountForReturnDouble = Double.parseDouble(cashAmountForReturnString);
                    cashAmountForReturn = BigDecimal.valueOf(cashAmountForReturnDouble);
                } catch(NumberFormatException numberFormatException) {
                    cashAmountForReturn = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CASHAMOUNTFORRETURN)) {
                if(NewPRPrepaymentRequest != null) NewPRPrepaymentRequest.setCashAmountForReturn(cashAmountForReturn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CARDAMOUNTFORRETURN)) {
                xmlEvent = xmlEventReader.nextEvent();
                String cardAmountForReturnString = xmlEvent.asCharacters().getData();
                try {
                    double cardAmountForReturnDouble = Double.parseDouble(cardAmountForReturnString);
                    cardAmountForReturn = BigDecimal.valueOf(cardAmountForReturnDouble);
                } catch(NumberFormatException numberFormatException) {
                    cardAmountForReturn = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CARDAMOUNTFORRETURN)) {
                if(NewPRPrepaymentRequest != null) NewPRPrepaymentRequest.setCardAmountForReturn(cardAmountForReturn);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((NewPRPrepaymentRequest != null)
                        && (NewPRPrepaymentRequest.getSeq() != -1)
                        && (NewPRPrepaymentRequest.getCrn() != null)
                        && (NewPRPrepaymentRequest.getReturnTicketId() != -1)
                        && (NewPRPrepaymentRequest.getCashAmountForReturn() != null)
                        && (NewPRPrepaymentRequest.getCardAmountForReturn() != null)))
                    NewPRPrepaymentRequest = null;
                break;
            }
        }
        return NewPRPrepaymentRequest;
    }
}