package bsoftlabecr.xml.reader.receipt.returns.prepayment;

import bsoftlabecr.exception.XmlFileInvalidException;
import bsoftlabecr.exception.XmlFileNotFoundException;
import bsoftlabecr.request.receipt.returns.prepayment.ExistReturnPrepaymentRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistReturnPrepaymentRequestReaderXml {
    private static final String REQUEST = "ExistReturnPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RECEIPTID = "receiptId";

    private String fileName = null;

    public ExistReturnPrepaymentRequestReaderXml() {}
    public ExistReturnPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ExistReturnPrepaymentRequest readFile()
            throws XmlFileInvalidException, XmlFileNotFoundException {

        Integer seq = null;
        String crn = null;
        String receiptId = null;
        ExistReturnPrepaymentRequest existReturnPrepaymentRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                    existReturnPrepaymentRequest = new ExistReturnPrepaymentRequest();
                    existReturnPrepaymentRequest.setSeq(seq);
                    existReturnPrepaymentRequest.setCrn(crn);
                    existReturnPrepaymentRequest.setReceiptId(receiptId);
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
                    if (existReturnPrepaymentRequest != null) existReturnPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    if (existReturnPrepaymentRequest != null) existReturnPrepaymentRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    receiptId = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RECEIPTID)) {
                    if (existReturnPrepaymentRequest != null) existReturnPrepaymentRequest.setReceiptId(receiptId);
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

        if (existReturnPrepaymentRequest == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        if (existReturnPrepaymentRequest.getSeq() == null ||
                existReturnPrepaymentRequest.getCrn() == null ||
                existReturnPrepaymentRequest.getReceiptId() == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        return existReturnPrepaymentRequest;
    }
}