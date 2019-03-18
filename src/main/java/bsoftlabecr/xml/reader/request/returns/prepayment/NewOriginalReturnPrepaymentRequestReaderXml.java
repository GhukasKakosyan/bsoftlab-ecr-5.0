package bsoftlabecr.xml.reader.request.returns.prepayment;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.returns.prepayment.NewOriginalReturnPrepaymentRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewOriginalReturnPrepaymentRequestReaderXml {
    private static final String XML_TAG_MAIN = "NewOriginalReturnPrepaymentRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_CRN = "crn";
    private static final String XML_TAG_RETURNTICKETID = "returnTicketId";

    private static final Integer RESPONSE_CODE = ResponseType
            .NEW_ORIGINAL_RETURN_PREPAYMENT_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public NewOriginalReturnPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public NewOriginalReturnPrepaymentRequest read() throws XmlFileReadException {

        Integer returnTicketId = null;
        Integer seq = null;
        String crn = null;

        NewOriginalReturnPrepaymentRequest newOriginalReturnPrepaymentRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    newOriginalReturnPrepaymentRequest = new NewOriginalReturnPrepaymentRequest();
                    newOriginalReturnPrepaymentRequest.setSeq(seq);
                    newOriginalReturnPrepaymentRequest.setCrn(crn);
                    newOriginalReturnPrepaymentRequest.setReturnTicketId(returnTicketId);
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
                    if (newOriginalReturnPrepaymentRequest != null) newOriginalReturnPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    if (newOriginalReturnPrepaymentRequest != null) newOriginalReturnPrepaymentRequest.setCrn(crn);
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
                    if (newOriginalReturnPrepaymentRequest != null)
                        newOriginalReturnPrepaymentRequest.setReturnTicketId(returnTicketId);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (newOriginalReturnPrepaymentRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (newOriginalReturnPrepaymentRequest.getSeq() == null ||
                newOriginalReturnPrepaymentRequest.getCrn() == null ||
                newOriginalReturnPrepaymentRequest.getReturnTicketId() == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return newOriginalReturnPrepaymentRequest;
    }
}