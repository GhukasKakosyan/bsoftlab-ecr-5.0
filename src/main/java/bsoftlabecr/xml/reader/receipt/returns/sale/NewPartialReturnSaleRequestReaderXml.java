package bsoftlabecr.xml.reader.receipt.returns.sale;

import bsoftlabecr.entity.ReturnItem;
import bsoftlabecr.exception.XmlFileInvalidException;
import bsoftlabecr.exception.XmlFileNotFoundException;
import bsoftlabecr.request.receipt.returns.sale.NewPartialReturnSaleRequest;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewPartialReturnSaleRequestReaderXml {
    private static final String REQUEST = "NewPartialReturnSaleRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_CRN = "crn";
    private static final String REQUEST_RETURNTICKETID = "returnTicketId";
    private static final String REQUEST_CASHAMOUNTFORRETURN = "cashAmountForReturn";
    private static final String REQUEST_CARDAMOUNTFORRETURN = "cardAmountForReturn";
    private static final String REQUEST_RETURNITEMLIST = "returnItemList";
    private static final String REQUEST_RETURNITEM = "returnItem";
    private static final String REQUEST_RPID = "rpid";
    private static final String REQUEST_QUANTITY = "quantity";

    private String fileName = null;

    public NewPartialReturnSaleRequestReaderXml() {}
    public NewPartialReturnSaleRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public NewPartialReturnSaleRequest readFile()
            throws XmlFileInvalidException, XmlFileNotFoundException {

        Double quantity = null;
        Integer returnTicketId = null;
        Integer seq = null;
        Long rpid = null;
        String crn = null;

        BigDecimal cardAmountForReturn = null;
        BigDecimal cashAmountForReturn = null;
        List<ReturnItem> returnItemList = null;
        ReturnItem returnItem = null;

        NewPartialReturnSaleRequest newPartialReturnSaleRequest = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                    newPartialReturnSaleRequest = new NewPartialReturnSaleRequest();
                    newPartialReturnSaleRequest.setSeq(seq);
                    newPartialReturnSaleRequest.setCrn(crn);
                    newPartialReturnSaleRequest.setReturnTicketId(returnTicketId);
                    newPartialReturnSaleRequest.setCashAmountForReturn(cashAmountForReturn);
                    newPartialReturnSaleRequest.setCardAmountForReturn(cardAmountForReturn);
                    newPartialReturnSaleRequest.setReturnItemList(returnItemList);
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
                    if (newPartialReturnSaleRequest != null) newPartialReturnSaleRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    crn = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CRN)) {
                    if (newPartialReturnSaleRequest != null) newPartialReturnSaleRequest.setCrn(crn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String returnTicketIdString = xmlEvent.asCharacters().getData();
                    try {
                        returnTicketId = Integer.parseInt(returnTicketIdString);
                    } catch (NumberFormatException numberFormatException) {
                        returnTicketId = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNTICKETID)) {
                    if (newPartialReturnSaleRequest != null)
                        newPartialReturnSaleRequest.setReturnTicketId(returnTicketId);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CASHAMOUNTFORRETURN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String cashAmountForReturnString = xmlEvent.asCharacters().getData();
                    try {
                        double cashAmountForReturnDouble = Double.parseDouble(cashAmountForReturnString);
                        cashAmountForReturn = BigDecimal.valueOf(cashAmountForReturnDouble);
                    } catch (NumberFormatException numberFormatException) {
                        cashAmountForReturn = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CASHAMOUNTFORRETURN)) {
                    if (newPartialReturnSaleRequest != null)
                        newPartialReturnSaleRequest.setCashAmountForReturn(cashAmountForReturn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_CARDAMOUNTFORRETURN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String cardAmountForReturnString = xmlEvent.asCharacters().getData();
                    try {
                        double cardAmountForReturnDouble = Double.parseDouble(cardAmountForReturnString);
                        cardAmountForReturn = BigDecimal.valueOf(cardAmountForReturnDouble);
                    } catch (NumberFormatException numberFormatException) {
                        cardAmountForReturn = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_CARDAMOUNTFORRETURN)) {
                    if (newPartialReturnSaleRequest != null)
                        newPartialReturnSaleRequest.setCardAmountForReturn(cardAmountForReturn);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNITEMLIST)) {
                    returnItemList = new ArrayList<>();
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RETURNITEM)) {
                    rpid = null;
                    quantity = null;
                    returnItem = new ReturnItem();
                    returnItem.setRpid(rpid);
                    returnItem.setQuantity(quantity);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_RPID)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String rpidString = xmlEvent.asCharacters().getData();
                    try {
                        rpid = Long.parseLong(rpidString);
                    } catch (NumberFormatException numberFormatException) {
                        rpid = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RPID)) {
                    if (returnItem != null) returnItem.setRpid(rpid);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_QUANTITY)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String quantityString = xmlEvent.asCharacters().getData();
                    try {
                        quantity = Double.parseDouble(quantityString);
                    } catch (NumberFormatException numberFormatException) {
                        quantity = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_QUANTITY)) {
                    if (returnItem != null) returnItem.setQuantity(quantity);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNITEM)) {
                    if (!((returnItem != null) && (returnItem.getRpid() != -1) && (returnItem.getQuantity() != -1)))
                        returnItem = null;
                    if (returnItemList != null) returnItemList.add(returnItem);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_RETURNITEMLIST)) {
                    if ((returnItemList != null) && (returnItemList.size() > 0)) {
                        for (ReturnItem returnGood : returnItemList) {
                            if (returnGood == null) {
                                returnItemList = null;
                                break;
                            }
                        }
                    } else returnItemList = null;
                    if (newPartialReturnSaleRequest != null)
                        newPartialReturnSaleRequest.setReturnItemList(returnItemList);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                    break;
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new XmlFileNotFoundException(this.fileName);
        } catch (XMLStreamException xmlStreamException) {
            throw new XmlFileInvalidException(this.fileName);
        }

        if (newPartialReturnSaleRequest == null) {
            throw new XmlFileInvalidException(this.fileName);
        }
        if (newPartialReturnSaleRequest.getSeq() == null ||
                newPartialReturnSaleRequest.getCrn() == null ||
                newPartialReturnSaleRequest.getReturnTicketId() == null ||
                newPartialReturnSaleRequest.getCashAmountForReturn() == null ||
                newPartialReturnSaleRequest.getCardAmountForReturn() == null ||
                newPartialReturnSaleRequest.getReturnItemList() == null ||
                newPartialReturnSaleRequest.getReturnItemList().isEmpty()) {
            throw new XmlFileInvalidException(this.fileName);
        }
        return newPartialReturnSaleRequest;
    }
}