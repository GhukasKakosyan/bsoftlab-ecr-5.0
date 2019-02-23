package BSOFTLABECR.xml.reader.receipt.sale;

import BSOFTLABECR.request.receipt.sale.ExistSaleRequest;

import java.io.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistSaleRequestReaderXML {
    private static final String REQUEST = "ExistSaleRequest";
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

    public ExistSaleRequest readFile() throws FileNotFoundException, XMLStreamException {

        int seq = -1;
        String crn = null;
        String receiptId = null;

        ExistSaleRequest existSaleRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                existSaleRequest = new ExistSaleRequest();
                existSaleRequest.setSeq(seq);
                existSaleRequest.setCrn(crn);
                existSaleRequest.setReceiptId(receiptId);
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
                if(existSaleRequest != null) existSaleRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(existSaleRequest != null) existSaleRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                xmlEvent = xmlEventReader.nextEvent();
                receiptId = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                if(existSaleRequest != null) existSaleRequest.setReceiptId(receiptId);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((existSaleRequest != null)
                        && (existSaleRequest.getSeq() != -1)
                        && (existSaleRequest.getCrn() != null)
                        && (existSaleRequest.getReceiptId() != null)))
                    existSaleRequest = null;
                break;
            }
        }
        return existSaleRequest;
    }
}