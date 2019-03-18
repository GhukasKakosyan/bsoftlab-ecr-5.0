package bsoftlabecr.xml.reader.request.returns.sale;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.returns.sale.ExistReturnSaleRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ExistReturnSaleRequestReaderXml {
    private static final String XML_TAG_MAIN = "ExistReturnSaleRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_CRN = "crn";
    private static final String XML_TAG_RECEIPTID = "receiptId";

    private static final Integer RESPONSE_CODE =
            ResponseType.EXIST_RETURN_SALE_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public ExistReturnSaleRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public ExistReturnSaleRequest read() throws XmlFileReadException {

        Integer seq = null;
        String crn = null;
        String receiptId = null;
        ExistReturnSaleRequest existReturnSaleRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    existReturnSaleRequest = new ExistReturnSaleRequest();
                    existReturnSaleRequest.setSeq(seq);
                    existReturnSaleRequest.setCrn(crn);
                    existReturnSaleRequest.setReceiptId(receiptId);
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
                    if (existReturnSaleRequest != null) existReturnSaleRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    if (existReturnSaleRequest != null) existReturnSaleRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_RECEIPTID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    receiptId = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_RECEIPTID)) {
                    if (existReturnSaleRequest != null) existReturnSaleRequest.setReceiptId(receiptId);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (existReturnSaleRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (existReturnSaleRequest.getSeq() == null ||
                existReturnSaleRequest.getCrn() == null ||
                existReturnSaleRequest.getReceiptId() == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return existReturnSaleRequest;
    }
}