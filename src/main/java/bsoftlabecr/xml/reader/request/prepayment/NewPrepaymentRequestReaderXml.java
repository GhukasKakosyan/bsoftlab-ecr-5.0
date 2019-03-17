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
    private static final String REQUEST = "NewPrepaymentRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_PAIDAMOUNT = "paidAmount";
    private static final String REQUEST_PAIDAMOUNTCARD = "paidAmountCard";
    private static final String REQUEST_PARTIALAMOUNT = "partialAmount";
    private static final String REQUEST_PREPAYMENTAMOUNT = "prePaymentAmount";
    private static final String REQUEST_MODE = "mode";
    private static final String REQUEST_USEEXTPOS = "useExtPOS";

    private String fileName;

    public NewPrepaymentRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public NewPrepaymentRequest read() throws XmlFileReadException {

        Boolean useExtPOS = null;
        Double paidAmount = null;
        Double paidAmountCard = null;
        Double partialAmount = null;
        Double prePaymentAmount = null;
        Integer mode = null;
        Integer seq = null;
        List<Item> items = null;

        NewPrepaymentRequest newPrepaymentRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                    newPrepaymentRequest = new NewPrepaymentRequest();
                    newPrepaymentRequest.setSeq(seq);
                    newPrepaymentRequest.setPaidAmount(paidAmount);
                    newPrepaymentRequest.setPaidAmountCard(paidAmountCard);
                    newPrepaymentRequest.setPartialAmount(partialAmount);
                    newPrepaymentRequest.setPrePaymentAmount(prePaymentAmount);
                    newPrepaymentRequest.setMode(mode);
                    newPrepaymentRequest.setUseExtPOS(useExtPOS);
                    newPrepaymentRequest.setItems(items);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String seqString = xmlEvent.asCharacters().getData();
                    try {
                        seq = Integer.parseInt(seqString);
                    } catch (NumberFormatException numberFormatException) {
                        seq = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_SEQ)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String paidAmountString = xmlEvent.asCharacters().getData();
                    try {
                        paidAmount = Double.parseDouble(paidAmountString);
                    } catch (NumberFormatException numberFormatException) {
                        paidAmount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNT)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPaidAmount(paidAmount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNTCARD)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String paidAmountCardString = xmlEvent.asCharacters().getData();
                    try {
                        paidAmountCard = Double.parseDouble(paidAmountCardString);
                    } catch (NumberFormatException numberFormatException) {
                        paidAmountCard = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNTCARD)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPaidAmountCard(paidAmountCard);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PARTIALAMOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String partialAmountString = xmlEvent.asCharacters().getData();
                    try {
                        partialAmount = Double.parseDouble(partialAmountString);
                    } catch (NumberFormatException numberFormatException) {
                        partialAmount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PARTIALAMOUNT)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPartialAmount(partialAmount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PREPAYMENTAMOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String prePaymentAmountString = xmlEvent.asCharacters().getData();
                    try {
                        prePaymentAmount = Double.parseDouble(prePaymentAmountString);
                    } catch (NumberFormatException numberFormatException) {
                        prePaymentAmount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PREPAYMENTAMOUNT)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setPrePaymentAmount(prePaymentAmount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_MODE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String modeString = xmlEvent.asCharacters().getData();
                    try {
                        mode = Integer.parseInt(modeString);
                    } catch (NumberFormatException numberFormatException) {
                        mode = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_MODE)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setMode(mode);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_USEEXTPOS)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String useExtPOSString = xmlEvent.asCharacters().getData();
                    useExtPOS = Boolean.parseBoolean(useExtPOSString);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_USEEXTPOS)) {
                    if (newPrepaymentRequest != null) newPrepaymentRequest.setUseExtPOS(useExtPOS);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                    break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(ResponseType
                    .NEW_PREPAYMENT_REQUEST_XML_FILE_READ_ERROR.getCode());
        }

        if (newPrepaymentRequest == null) {
            throw new XmlFileReadException(ResponseType
                    .NEW_PREPAYMENT_REQUEST_XML_FILE_READ_ERROR.getCode());
        }
        if (newPrepaymentRequest.getSeq() == null ||
                newPrepaymentRequest.getPaidAmount() == null ||
                newPrepaymentRequest.getPaidAmountCard() == null ||
                newPrepaymentRequest.getPartialAmount() == null ||
                newPrepaymentRequest.getPrePaymentAmount() == null ||
                newPrepaymentRequest.isUseExtPOS() == null ||
                newPrepaymentRequest.getMode() == null ||
                newPrepaymentRequest.getItems() != null) {
            throw new XmlFileReadException(ResponseType
                    .NEW_PREPAYMENT_REQUEST_XML_FILE_READ_ERROR.getCode());
        }
        return newPrepaymentRequest;
    }
}