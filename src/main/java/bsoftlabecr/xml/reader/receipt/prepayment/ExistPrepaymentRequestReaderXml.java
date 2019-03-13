package bsoftlabecr.xml.reader.receipt.prepayment;

import bsoftlabecr.exception.XmlFileInvalidException;
import bsoftlabecr.exception.XmlFileNotFoundException;
import bsoftlabecr.request.receipt.prepayment.ExistPrepaymentRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistPrepaymentRequestReaderXml {
    private static final String REQUEST = "ExistPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RECEIPTID = "receiptId";

    private String fileName = null;

    public ExistPrepaymentRequestReaderXml() {}
    public ExistPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {return this.fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public ExistPrepaymentRequest readFile()
            throws XmlFileInvalidException, XmlFileNotFoundException {

        Integer seq = null;
        String crn = null;
        String receiptId = null;
        ExistPrepaymentRequest existPrepaymentRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                    existPrepaymentRequest = new ExistPrepaymentRequest();
                    existPrepaymentRequest.setSeq(seq);
                    existPrepaymentRequest.setCrn(crn);
                    existPrepaymentRequest.setReceiptId(receiptId);
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
                    if (existPrepaymentRequest != null) existPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    if (existPrepaymentRequest != null) existPrepaymentRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    receiptId = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                    if (existPrepaymentRequest != null) existPrepaymentRequest.setReceiptId(receiptId);
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

        if (existPrepaymentRequest == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        if (existPrepaymentRequest.getSeq() == null ||
                existPrepaymentRequest.getCrn() == null ||
                existPrepaymentRequest.getReceiptId() == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        return existPrepaymentRequest;
    }
}