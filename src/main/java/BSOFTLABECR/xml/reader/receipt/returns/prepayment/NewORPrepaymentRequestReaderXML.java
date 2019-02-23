package BSOFTLABECR.xml.reader.receipt.returns.prepayment;

import BSOFTLABECR.request.receipt.returns.prepayment.NewORPrepaymentRequest;

import java.io.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewORPrepaymentRequestReaderXML {
    private static final String REQUEST = "NewORPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RETURNTICKETID = "returnTicketId";

    private String fileName = null;

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public NewORPrepaymentRequest readFile() throws FileNotFoundException, XMLStreamException {
        int returnTicketId = -1;
        int seq = -1;
        String crn = null;

        NewORPrepaymentRequest NewORPrepaymentRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                NewORPrepaymentRequest = new NewORPrepaymentRequest();
                NewORPrepaymentRequest.setSeq(seq);
                NewORPrepaymentRequest.setCrn(crn);
                NewORPrepaymentRequest.setReturnTicketId(returnTicketId);
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
                if(NewORPrepaymentRequest != null) NewORPrepaymentRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(NewORPrepaymentRequest != null) NewORPrepaymentRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement () && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                xmlEvent = xmlEventReader.nextEvent();
                String returnTicketIdString = xmlEvent.asCharacters().getData();
                try {
                    returnTicketId = Integer.parseInt(returnTicketIdString);
                } catch(NumberFormatException numberFormatException) {
                    returnTicketId = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                if(NewORPrepaymentRequest != null) NewORPrepaymentRequest.setReturnTicketId(returnTicketId);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((NewORPrepaymentRequest != null)
                        && (NewORPrepaymentRequest.getSeq() != -1)
                        && (NewORPrepaymentRequest.getCrn() != null)
                        && (NewORPrepaymentRequest.getReturnTicketId() != -1)))
                    NewORPrepaymentRequest = null;
                break;
            }
        }
        return NewORPrepaymentRequest;
    }
}
