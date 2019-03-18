package bsoftlabecr.xml.reader.request.returns.sale;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.returns.sale.NewOriginalReturnSaleRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewOriginalReturnSaleRequestReaderXml {
    private static final String XML_TAG_MAIN = "NewOriginalReturnSaleRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_CRN = "crn";
    private static final String XML_TAG_RETURNTICKETID = "returnTicketId";

    private static final Integer RESPONSE_CODE =
            ResponseType.NEW_ORIGINAL_RETURN_SALE_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public NewOriginalReturnSaleRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public NewOriginalReturnSaleRequest read() throws XmlFileReadException {

        Integer returnTicketId = null;
        Integer seq = null;
        String crn = null;

        NewOriginalReturnSaleRequest newOriginalReturnSaleRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    newOriginalReturnSaleRequest = new NewOriginalReturnSaleRequest();
                    newOriginalReturnSaleRequest.setSeq(seq);
                    newOriginalReturnSaleRequest.setCrn(crn);
                    newOriginalReturnSaleRequest.setReturnTicketId(returnTicketId);
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
                    if (newOriginalReturnSaleRequest != null) newOriginalReturnSaleRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    if (newOriginalReturnSaleRequest != null) newOriginalReturnSaleRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_RETURNTICKETID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String returnTicketIdString = xmlEvent.asCharacters().getData();
                    try {
                        returnTicketId = Integer.parseInt(returnTicketIdString);
                    } catch (NumberFormatException numberFormatException) {
                        returnTicketId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_RETURNTICKETID)) {
                    if (newOriginalReturnSaleRequest != null)
                        newOriginalReturnSaleRequest.setReturnTicketId(returnTicketId);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (newOriginalReturnSaleRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (newOriginalReturnSaleRequest.getSeq() == null ||
                newOriginalReturnSaleRequest.getCrn() == null ||
                newOriginalReturnSaleRequest.getReturnTicketId() == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return newOriginalReturnSaleRequest;
    }
}