package BSOFTLABECR.xml.reader.constants;

import BSOFTLABECR.entity.Constants;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ConstantsReaderXML {
    private static final String CONSTANTS = "Constants";
    private static final String CONSTANTS_CRN = "crn";
    private static final String CONSTANTS_IP = "ip";
    private static final String CONSTANTS_PORT = "port";
    private static final String CONSTANTS_PASSWORD = "password";
    private static final String CONSTANTS_CASHIERID = "cashierId";
    private static final String CONSTANTS_CASHIERPASSWORD = "cashierPassword";

    private String fileName = null;

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @SuppressWarnings ({"null" })
    public Constants readFile() throws FileNotFoundException, XMLStreamException {
        String crn = null;
        String ip = null;
        int port = -1;
        String password = null;
        int cashierId = -1;
        String cashierPassword = null;

        Constants constants = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS)) {
                constants = new Constants();
                constants.setCrn(crn);
                constants.setIp(ip);
                constants.setPort(port);
                constants.setPassword(password);
                constants.setCashierId(cashierId);
                constants.setCashierPassword(cashierPassword);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS_CRN)) {
                xmlEvent = xmlEventReader.nextEvent();
                crn = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS_CRN)) {
                if(constants != null) constants.setCrn(crn);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS_IP)) {
                xmlEvent = xmlEventReader.nextEvent();
                ip = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS_IP)) {
                if(constants != null) constants.setIp(ip);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS_PORT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String portString = xmlEvent.asCharacters().getData();
                try {
                    port = Integer.parseInt(portString);
                } catch(NumberFormatException numberFormatException) {
                    port = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS_PORT)) {
                if(constants != null) constants.setPort(port);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS_PASSWORD)) {
                xmlEvent = xmlEventReader.nextEvent();
                password = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS_PASSWORD)) {
                if(constants != null) constants.setPassword(password);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS_CASHIERID)) {
                xmlEvent = xmlEventReader.nextEvent();
                String cashierIdString = xmlEvent.asCharacters().getData();
                try {
                    cashierId = Integer.parseInt(cashierIdString);
                } catch(NumberFormatException numberFormatException) {
                    cashierId = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS_CASHIERID)) {
                if(constants != null) constants.setCashierId(cashierId);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(CONSTANTS_CASHIERPASSWORD)) {
                xmlEvent = xmlEventReader.nextEvent();
                cashierPassword = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS_CASHIERPASSWORD)) {
                if(constants != null) constants.setCashierPassword(cashierPassword);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(CONSTANTS)) {
                if(!((constants != null)
                        && (constants.getCrn() != null)
                        && (constants.getIp() != null)
                        && (constants.getPort() != -1)
                        && (constants.getPassword() != null)
                        && (constants.getCashierId() != -1)
                        && constants.getCashierPassword() != null))
                    constants = null;
                break;
            }
        }
        return constants;
    }
}