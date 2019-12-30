package bsoftlabecr.xml.reader.request.sale;

import bsoftlabecr.entity.CoderingsConverter;
import bsoftlabecr.entity.Item;
import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileReadException;
import bsoftlabecr.request.receipt.sale.NewSaleRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewSaleRequestReaderXml {
    private static final String XML_TAG_MAIN = "NewSaleRequest";
    private static final String XML_TAG_SEQ = "seq";
    private static final String XML_TAG_PARTNERTIN = "partnerTin";
    private static final String XML_TAG_PAIDAMOUNT = "paidAmount";
    private static final String XML_TAG_PAIDAMOUNTCARD = "paidAmountCard";
    private static final String XML_TAG_PARTIALAMOUNT = "partialAmount";
    private static final String XML_TAG_PREPAYMENTAMOUNT = "prePaymentAmount";
    private static final String XML_TAG_MODE = "mode";
    private static final String XML_TAG_USEEXTPOS = "useExtPOS";
    private static final String XML_TAG_ITEMS = "items";
    private static final String XML_TAG_ITEM = "item";
    private static final String XML_TAG_DEP = "dep";
    private static final String XML_TAG_ADGCODE = "adgCode";
    private static final String XML_TAG_PRODUCTCODE = "productCode";
    private static final String XML_TAG_PRODUCTNAME = "productName";
    private static final String XML_TAG_UNIT = "unit";
    private static final String XML_TAG_QTY = "qty";
    private static final String XML_TAG_PRICE = "price";
    private static final String XML_TAG_DISCOUNT = "discount";
    private static final String XML_TAG_DISCOUNTTYPE = "discountType";
    private static final String XML_TAG_ADDITIONALDISCOUNT = "additionalDiscount";
    private static final String XML_TAG_ADDITIONALDISCOUNTTYPE = "additionalDiscountType";

    private static final Integer RESPONSE_CODE =
            ResponseType.NEW_SALE_REQUEST_XML_FILE_READ_ERROR.getCode();

    private String fileName;

    public NewSaleRequestReaderXml(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public NewSaleRequest read() throws XmlFileReadException {

        NewSaleRequest newSaleRequest = null;

        Boolean useExtPOS = null;
        Double paidAmount = null;
        Double paidAmountCard = null;
        Double partialAmount = null;
        Double prePaymentAmount = null;
        Integer dep = null;
        Integer mode = null;
        Integer seq = null;
        String partnerTin = null;

        Item item = null;
        List<Item> items = null;

        BigDecimal additionalDiscount = null;
        BigDecimal additionalDiscountType = null;
        BigDecimal discount = null;
        BigDecimal discountType = null;
        BigDecimal qty = null;
        BigDecimal price = null;
        String adgCode = null;
        String productCode = null;
        String productName = null;
        String unit = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFileName());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    newSaleRequest = new NewSaleRequest();
                    newSaleRequest.setSeq(seq);
                    newSaleRequest.setPartnerTin(partnerTin);
                    newSaleRequest.setPaidAmount(paidAmount);
                    newSaleRequest.setPaidAmountCard(paidAmountCard);
                    newSaleRequest.setPartialAmount(partialAmount);
                    newSaleRequest.setPrePaymentAmount(prePaymentAmount);
                    newSaleRequest.setMode(mode);
                    newSaleRequest.setUseExtPOS(useExtPOS);
                    newSaleRequest.setItems(items);
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
                    if (newSaleRequest != null) newSaleRequest.setSeq(seq);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PARTNERTIN)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    try {
                        partnerTin = xmlEvent.asCharacters().getData();
                        Integer.parseInt(partnerTin);
                    } catch (NumberFormatException numberFormatException) {
                        partnerTin = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PARTNERTIN)) {
                    if (newSaleRequest != null) newSaleRequest.setPartnerTin(partnerTin);
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
                    if (newSaleRequest != null) newSaleRequest.setPaidAmount(paidAmount);
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
                    if (newSaleRequest != null) newSaleRequest.setPaidAmountCard(paidAmountCard);
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
                    if (newSaleRequest != null) newSaleRequest.setPartialAmount(partialAmount);
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
                    if (newSaleRequest != null) newSaleRequest.setPrePaymentAmount(prePaymentAmount);
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
                    if (newSaleRequest != null) newSaleRequest.setMode(mode);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_USEEXTPOS)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String useExtPOSString = xmlEvent.asCharacters().getData();
                    useExtPOS = Boolean.parseBoolean(useExtPOSString);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_USEEXTPOS)) {
                    if (newSaleRequest != null) newSaleRequest.setUseExtPOS(useExtPOS);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_ITEMS)) {
                    items = new ArrayList<>();
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_ITEM)) {
                    dep = null;
                    adgCode = null;
                    productCode = null;
                    productName = null;
                    unit = null;
                    qty = null;
                    price = null;
                    discount = null;
                    discountType = null;
                    additionalDiscount = null;
                    additionalDiscountType = null;

                    item = new Item();
                    item.setDep(dep);
                    item.setAdgCode(adgCode);
                    item.setProductCode(productCode);
                    item.setProductName(productName);
                    item.setUnit(unit);
                    item.setQty(qty);
                    item.setPrice(price);
                    item.setDiscount(discount);
                    item.setDiscountType(discountType);
                    item.setAdditionalDiscount(additionalDiscount);
                    item.setAdditionalDiscountType(additionalDiscountType);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_DEP)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String depString = xmlEvent.asCharacters().getData();
                    try {
                        dep = Integer.parseInt(depString);
                    } catch (NumberFormatException numberFormatException) {
                        dep = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_DEP)) {
                    if (item != null) item.setDep(dep);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_ADGCODE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    adgCode = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_ADGCODE)) {
                    if (item != null) item.setAdgCode(adgCode);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PRODUCTCODE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    productCode = xmlEvent.asCharacters().getData();
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PRODUCTCODE)) {
                    if (item != null) item.setProductCode(productCode);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PRODUCTNAME)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String productNameCodering = xmlEvent.asCharacters().getData();
                    productName = CoderingsConverter.convert(productNameCodering);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PRODUCTNAME)) {
                    if (item != null) item.setProductName(productName);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_UNIT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String unitCodering = xmlEvent.asCharacters().getData();
                    unit = CoderingsConverter.convert(unitCodering);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_UNIT)) {
                    if (item != null && unit != null) {
                        item.setUnit(unit);
                    }
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_QTY)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String qtyString = xmlEvent.asCharacters().getData();
                    try {
                        double qtyDouble = Double.parseDouble(qtyString);
                        qty = BigDecimal.valueOf(qtyDouble);
                    } catch (NumberFormatException numberFormatException) {
                        qty = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_QTY)) {
                    if (item != null) item.setQty(qty);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_PRICE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String priceString = xmlEvent.asCharacters().getData();
                    try {
                        double priceDouble = Double.parseDouble(priceString);
                        price = BigDecimal.valueOf(priceDouble);
                    } catch (NumberFormatException numberFormatException) {
                        price = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_PRICE)) {
                    if (item != null) item.setPrice(price);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_DISCOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String discountString = xmlEvent.asCharacters().getData();
                    try {
                        double discountDouble = Double.parseDouble(discountString);
                        discount = BigDecimal.valueOf(discountDouble);
                    } catch (NumberFormatException numberFormatException) {
                        discount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_DISCOUNT)) {
                    if (item != null) item.setDiscount(discount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_DISCOUNTTYPE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String discountTypeString = xmlEvent.asCharacters().getData();
                    try {
                        discountType = BigDecimal.valueOf(Integer.parseInt(discountTypeString));
                    } catch (NumberFormatException numberFormatException) {
                        discountType = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_DISCOUNTTYPE)) {
                    if (item != null) item.setDiscountType(discountType);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_ADDITIONALDISCOUNT)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String additionalDiscountString = xmlEvent.asCharacters().getData();
                    try {
                        double additionalDiscountDouble = Double.parseDouble(additionalDiscountString);
                        additionalDiscount = BigDecimal.valueOf(additionalDiscountDouble);
                    } catch (NumberFormatException numberFormatException) {
                        additionalDiscount = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_ADDITIONALDISCOUNT)) {
                    if (item != null) item.setAdditionalDiscount(additionalDiscount);
                    continue;
                }
                if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(XML_TAG_ADDITIONALDISCOUNTTYPE)) {
                    xmlEvent = xmlEventReader.nextEvent();
                    String additionalDiscountTypeString = xmlEvent.asCharacters().getData();
                    try {
                        additionalDiscountType = BigDecimal.valueOf(Integer.parseInt(additionalDiscountTypeString));
                    } catch (NumberFormatException numberFormatException) {
                        additionalDiscountType = null;
                    }
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_ADDITIONALDISCOUNTTYPE)) {
                    if (item != null) item.setAdditionalDiscountType(additionalDiscountType);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_ITEM)) {
                    if (!((item != null) && (item.getDep() != -1) && (item.getProductCode() != null)
                            && (item.getProductName() != null) && (item.getUnit() != null)
                            && (item.getQty() != null) && (item.getPrice() != null))) item = null;
                    if (items != null) items.add(item);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_ITEMS)) {
                    if (items != null && items.size() > 0) {
                        for (Item good : items) {
                            if (good == null) {
                                items = null;
                                break;
                            }
                        }
                    } else items = null;
                    if (newSaleRequest != null) newSaleRequest.setItems(items);
                    continue;
                }
                if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(XML_TAG_MAIN)) {
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException exception) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }

        if (newSaleRequest == null) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        if (newSaleRequest.getSeq() == null ||
                newSaleRequest.getPaidAmount() == null ||
                newSaleRequest.getPaidAmountCard() == null ||
                newSaleRequest.getPartialAmount() == null ||
                newSaleRequest.getPrePaymentAmount() == null ||
                newSaleRequest.getMode() == null ||
                newSaleRequest.getItems() == null ||
                newSaleRequest.getItems().isEmpty()) {
            throw new XmlFileReadException(RESPONSE_CODE);
        }
        return newSaleRequest;
    }
}