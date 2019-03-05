package bsoftlabecr.xml.reader.receipt.returns.prepayment;

import bsoftlabecr.request.receipt.returns.prepayment.ExistRPrepaymentRequest;

import java.io.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistRPrepaymentRequestReaderXML {
    private static final String REQUEST = "ExistRPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RECEIPTID = "receiptId";

    private String fileName = null;

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ExistRPrepaymentRequest readFile() throws FileNotFoundException, XMLStreamException {
        int seq = -1;
        String crn = null;
        String receiptId = null;
        ExistRPrepaymentRequest existRPrepaymentRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                existRPrepaymentRequest = new ExistRPrepaymentRequest();
                existRPrepaymentRequest.setSeq(seq);
                existRPrepaymentRequest.setCrn(crn);
                existRPrepaymentRequest.setReceiptId(receiptId);
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
                if(existRPrepaymentRequest != null) existRPrepaymentRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(existRPrepaymentRequest != null) existRPrepaymentRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                xmlEvent = xmlEventReader.nextEvent();
                receiptId = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                if(existRPrepaymentRequest != null) existRPrepaymentRequest.setReceiptId(receiptId);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((existRPrepaymentRequest != null)
                        && (existRPrepaymentRequest.getSeq() != -1)
                        && (existRPrepaymentRequest.getCrn() != null)
                        && (existRPrepaymentRequest.getReceiptId() != null)))
                    existRPrepaymentRequest = null;
                break;
            }
        }
        return existRPrepaymentRequest;
    }
}