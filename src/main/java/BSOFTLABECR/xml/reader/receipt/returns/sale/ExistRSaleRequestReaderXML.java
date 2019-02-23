package BSOFTLABECR.xml.reader.receipt.returns.sale;

import BSOFTLABECR.request.receipt.returns.sale.ExistRSaleRequest;

import java.io.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistRSaleRequestReaderXML {
    private static final String REQUEST = "ExistRSaleRequest";
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

    public ExistRSaleRequest readFile() throws FileNotFoundException, XMLStreamException {

        int seq = -1;
        String crn = null;
        String receiptId = null;
        ExistRSaleRequest existRSaleRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                existRSaleRequest = new ExistRSaleRequest();
                existRSaleRequest.setSeq(seq);
                existRSaleRequest.setCrn(crn);
                existRSaleRequest.setReceiptId(receiptId);
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
                if(existRSaleRequest != null) existRSaleRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(existRSaleRequest != null) existRSaleRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                xmlEvent = xmlEventReader.nextEvent();
                receiptId = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                if(existRSaleRequest != null) existRSaleRequest.setReceiptId(receiptId);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((existRSaleRequest != null)
                        && (existRSaleRequest.getSeq() != -1)
                        && (existRSaleRequest.getCrn() != null)
                        && (existRSaleRequest.getReceiptId() != null)))
                    existRSaleRequest = null;
                break;
            }
        }
        return existRSaleRequest;
    }
}