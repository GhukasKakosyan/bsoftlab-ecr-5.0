package BSOFTLABECR.xml.reader.receipt.prepayment;

import BSOFTLABECR.request.receipt.prepayment.ExistPrepaymentRequest;

import java.io.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistPrepaymentRequestReaderXML {
    private static final String REQUEST = "ExistPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RECEIPTID = "receiptId";

    private String fileName = null;

    public String getFileName() {return this.fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public ExistPrepaymentRequest readFile() throws FileNotFoundException, XMLStreamException {
        int seq = -1;
        String crn = null;
        String receiptId = null;
        ExistPrepaymentRequest existPrepaymentRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                existPrepaymentRequest = new ExistPrepaymentRequest();
                existPrepaymentRequest.setSeq(seq);
                existPrepaymentRequest.setCrn(crn);
                existPrepaymentRequest.setReceiptId(receiptId);
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
                if(existPrepaymentRequest != null) existPrepaymentRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(existPrepaymentRequest != null) existPrepaymentRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                xmlEvent = xmlEventReader.nextEvent();
                receiptId = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                if(existPrepaymentRequest != null) existPrepaymentRequest.setReceiptId(receiptId);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((existPrepaymentRequest != null)
                        && (existPrepaymentRequest.getSeq() != -1)
                        && (existPrepaymentRequest.getCrn() != null)
                        && (existPrepaymentRequest.getReceiptId() != null)))
                    existPrepaymentRequest = null;
                break;
            }
        }
        return existPrepaymentRequest;
    }
}