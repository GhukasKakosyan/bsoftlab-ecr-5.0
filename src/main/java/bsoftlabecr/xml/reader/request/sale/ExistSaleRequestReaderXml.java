package bsoftlabecr.xml.reader.request.sale;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.sale.ExistSaleRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistSaleRequestReaderXml {
    private static final String XML_TAG_MAIN = "ExistSaleRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_CRN = "crn";
    private static final String XML_TAG_RECEIPTID = "receiptId";

    private static final Integer RESPONSE_CODE =
            ResponseType.EXIST_SALE_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public ExistSaleRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public ExistSaleRequest read() throws XmlFileReadException {

        Integer seq = null;
        String crn = null;
        String receiptId = null;

        ExistSaleRequest existSaleRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    existSaleRequest = new ExistSaleRequest();
                    existSaleRequest.setSeq(seq);
                    existSaleRequest.setCrn(crn);
                    existSaleRequest.setReceiptId(receiptId);
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
                    if (existSaleRequest != null) existSaleRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    if (existSaleRequest != null) existSaleRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_RECEIPTID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    receiptId = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_RECEIPTID)) {
                    if (existSaleRequest != null) existSaleRequest.setReceiptId(receiptId);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (existSaleRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (existSaleRequest.getSeq() == null ||
                existSaleRequest.getCrn() == null ||
                existSaleRequest.getReceiptId() == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return existSaleRequest;
    }
}