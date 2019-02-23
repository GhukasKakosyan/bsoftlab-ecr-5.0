package BSOFTLABECR.xml.reader.receipt.returns.sale;

import BSOFTLABECR.request.receipt.returns.sale.NewORSaleRequest;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import java.io.*;

public class NewORSaleRequestReaderXML {
    private static final String REQUEST = "NewORSaleRequest";
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

    public NewORSaleRequest readFile() throws FileNotFoundException, XMLStreamException {
        int returnTicketId = -1;
        int seq = -1;
        String crn = null;

        NewORSaleRequest NewORSaleRequest = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                NewORSaleRequest = new NewORSaleRequest();
                NewORSaleRequest.setSeq(seq);
                NewORSaleRequest.setCrn(crn);
                NewORSaleRequest.setReturnTicketId(returnTicketId);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                xmlEvent = xmlEventReader.nextEvent();
                String seqString = xmlEvent.asCharacters().getData();
                try {
                    seq = Integer.parseInt(seqString);
                } catch (NumberFormatException numberFormatException) {
                    seq = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                if(NewORSaleRequest != null) NewORSaleRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                if(NewORSaleRequest != null) NewORSaleRequest.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                xmlEvent = xmlEventReader.nextEvent();
                String returnTicketIdString = xmlEvent.asCharacters().getData();
                try {
                    returnTicketId = Integer.parseInt(returnTicketIdString);
                } catch (NumberFormatException numberFormatException) {
                    returnTicketId = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                if(NewORSaleRequest != null) NewORSaleRequest.setReturnTicketId(returnTicketId);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if (!((NewORSaleRequest != null)
                        && (NewORSaleRequest.getSeq() != -1)
                        && (NewORSaleRequest.getCrn() != null)
                        && (NewORSaleRequest.getReturnTicketId() != -1)))
                    NewORSaleRequest = null;
                break;
            }
        }
        return NewORSaleRequest;
    }
}