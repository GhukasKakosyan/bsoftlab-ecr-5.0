package bsoftlabecr.xml.reader.request.prepayment;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.prepayment.ExistPrepaymentRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistPrepaymentRequestReaderXml {
    private static final String XML_TAG_MAIN = "ExistPrepaymentRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_CRN = "crn";
    private static final String XML_TAG_RECEIPTID = "receiptId";

    private static final Integer RESPONSE_CODE =
            ResponseType.EXIST_PREPAYMENT_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public ExistPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {return this.fileName;}

    public ExistPrepaymentRequest read() throws XmlFileReadException {

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
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    existPrepaymentRequest = new ExistPrepaymentRequest();
                    existPrepaymentRequest.setSeq(seq);
                    existPrepaymentRequest.setCrn(crn);
                    existPrepaymentRequest.setReceiptId(receiptId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_SEQ)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String seqString = xmlEvent.asCharacters().getData();
                    try {
                        seq = Integer.parseInt(seqString);
                    } catch (NumberFormatException numberFormatException) {
                        seq = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_SEQ)) {
                    if (existPrepaymentRequest != null) existPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    if (existPrepaymentRequest != null) existPrepaymentRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_RECEIPTID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    receiptId = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_RECEIPTID)) {
                    if (existPrepaymentRequest != null) existPrepaymentRequest.setReceiptId(receiptId);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (existPrepaymentRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (existPrepaymentRequest.getSeq() == null ||
                existPrepaymentRequest.getCrn() == null ||
                existPrepaymentRequest.getReceiptId() == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return existPrepaymentRequest;
    }
}