package bsoftlabecr.xml.reader.constants;

import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class ConstantsReaderXml {

    private static final String XML_TAG_MAIN = "Constants";
    private static final String XML_TAG_CRN = "crn";
    private static final String XML_TAG_IP = "ip";
    private static final String XML_TAG_PORT = "port";
    private static final String XML_TAG_PASSWORD = "password";
    private static final String XML_TAG_CASHIERID = "cashierId";
    private static final String XML_TAG_CASHIERPASSWORD = "cashierPassword";

    private static final Integer RESPONSE_CODE =
            ResponseType.CONSTANTS_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public ConstantsReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Constants read() throws XmlFileReadException {

        String crn = null;
        String ip = null;
        Integer port = null;
        String password = null;
        Integer cashierId = null;
        String cashierPassword = null;

        Constants constants = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    constants = new Constants();
                    constants.setCrn(crn);
                    constants.setIp(ip);
                    constants.setPort(port);
                    constants.setPassword(password);
                    constants.setCashierId(cashierId);
                    constants.setCashierPassword(cashierPassword);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CRN)) {
                    if (constants != null) constants.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_IP)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    ip = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_IP)) {
                    if (constants != null) constants.setIp(ip);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PORT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String portString = xmlEvent.asCharacters().getData();
                    try {
                        port = Integer.parseInt(portString);
                    } catch (NumberFormatException numberFormatException) {
                        port = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PORT)) {
                    if (constants != null) constants.setPort(port);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PASSWORD)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    password = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PASSWORD)) {
                    if (constants != null) constants.setPassword(password);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CASHIERID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String cashierIdString = xmlEvent.asCharacters().getData();
                    try {
                        cashierId = Integer.parseInt(cashierIdString);
                    } catch (NumberFormatException numberFormatException) {
                        cashierId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CASHIERID)) {
                    if (constants != null) constants.setCashierId(cashierId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_CASHIERPASSWORD)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    cashierPassword = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_CASHIERPASSWORD)) {
                    if (constants != null) constants.setCashierPassword(cashierPassword);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (constants == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (constants.getCrn() == null ||
                constants.getIp() == null ||
                constants.getPort() == null ||
                constants.getPassword() == null ||
                constants.getCashierId() == null ||
                constants.getCashierPassword() == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return constants;
    }
}