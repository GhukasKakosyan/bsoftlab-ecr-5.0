package bsoftlabecr.xml.reader.request.prepayment;

import bsoftlabecr.entity.Item;
import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.prepayment.NewPrepaymentRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewPrepaymentRequestReaderXml {
    private static final String XML_TAG_MAIN = "NewPrepaymentRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_PARTNERTIN = "partnerTin";
    private static final String XML_TAG_PAIDAMOUNT = "paidAmount";
    private static final String XML_TAG_PAIDAMOUNTCARD = "paidAmountCard";
    private static final String XML_TAG_PARTIALAMOUNT = "partialAmount";
    private static final String XML_TAG_PREPAYMENTAMOUNT = "prePaymentAmount";
    private static final String XML_TAG_MODE = "mode";
    private static final String XML_TAG_USEEXTPOS = "useExtPOS";

    private static final Integer RESPONSE_CODE =
            ResponseType.NEW_PREPAYMENT_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public NewPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public NewPrepaymentRequest read() throws XmlFileReadException {

        NewPrepaymentRequest newPrepaymentRequest = null;

        Boolean useExtPOS = null;
        Double paidAmount = null;
        Double paidAmountCard = null;
        Double partialAmount = null;
        Double prePaymentAmount = null;
        Integer mode = null;
        Integer seq = null;
        List<Item> items = null;
        String partnerTin = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    newPrepaymentRequest = new NewPrepaymentRequest();
                    newPrepaymentRequest.setSeq(seq);
                    newPrepaymentRequest.setPartnerTin(partnerTin);
                    newPrepaymentRequest.setPaidAmount(paidAmount);
                    newPrepaymentRequest.setPaidAmountCard(paidAmountCard);
                    newPrepaymentRequest.setPartialAmount(partialAmount);
                    newPrepaymentRequest.setPrePaymentAmount(prePaymentAmount);
                    newPrepaymentRequest.setMode(mode);
                    newPrepaymentRequest.setUseExtPOS(useExtPOS);
                    newPrepaymentRequest.setItems(items);
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
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PARTNERTIN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    partnerTin = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PARTNERTIN)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPartnerTin(partnerTin);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PAIDAMOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String paidAmountString = xmlEvent.asCharacters().getData();
                    try {
                        paidAmount = Double.parseDouble(paidAmountString);
                    } catch (NumberFormatException numberFormatException) {
                        paidAmount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PAIDAMOUNT)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPaidAmount(paidAmount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PAIDAMOUNTCARD)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String paidAmountCardString = xmlEvent.asCharacters().getData();
                    try {
                        paidAmountCard = Double.parseDouble(paidAmountCardString);
                    } catch (NumberFormatException numberFormatException) {
                        paidAmountCard = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PAIDAMOUNTCARD)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPaidAmountCard(paidAmountCard);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PARTIALAMOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String partialAmountString = xmlEvent.asCharacters().getData();
                    try {
                        partialAmount = Double.parseDouble(partialAmountString);
                    } catch (NumberFormatException numberFormatException) {
                        partialAmount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PARTIALAMOUNT)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPartialAmount(partialAmount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PREPAYMENTAMOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String prePaymentAmountString = xmlEvent.asCharacters().getData();
                    try {
                        prePaymentAmount = Double.parseDouble(prePaymentAmountString);
                    } catch (NumberFormatException numberFormatException) {
                        prePaymentAmount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PREPAYMENTAMOUNT)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPrePaymentAmount(prePaymentAmount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MODE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String modeString = xmlEvent.asCharacters().getData();
                    try {
                        mode = Integer.parseInt(modeString);
                    } catch (NumberFormatException numberFormatException) {
                        mode = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MODE)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setMode(mode);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_USEEXTPOS)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String useExtPOSString = xmlEvent.asCharacters().getData();
                    useExtPOS = Boolean.parseBoolean(useExtPOSString);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_USEEXTPOS)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setUseExtPOS(useExtPOS);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (newPrepaymentRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (newPrepaymentRequest.getSeq() == null ||
                newPrepaymentRequest.getPaidAmount() == null ||
                newPrepaymentRequest.getPaidAmountCard() == null ||
                newPrepaymentRequest.getPartialAmount() == null ||
                newPrepaymentRequest.getPrePaymentAmount() == null ||
                newPrepaymentRequest.isUseExtPOS() == null ||
                newPrepaymentRequest.getMode() == null ||
                newPrepaymentRequest.getItems() != null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return newPrepaymentRequest;
    }
}