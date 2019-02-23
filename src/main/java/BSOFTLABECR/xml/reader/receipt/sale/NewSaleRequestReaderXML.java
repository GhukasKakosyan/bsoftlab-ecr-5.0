package BSOFTLABECR.xml.reader.receipt.sale;

import BSOFTLABECR.entity.CoderingConverter;
import BSOFTLABECR.entity.Item;
import BSOFTLABECR.request.receipt.sale.NewSaleRequest;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class NewSaleRequestReaderXML {
    private static final String REQUEST = "NewSaleRequest";
    private static final String REQUEST_SEQ = "seq";
    private static final String REQUEST_PAIDAMOUNT = "paidAmount";
    private static final String REQUEST_PAIDAMOUNTCARD = "paidAmountCard";
    private static final String REQUEST_PARTIALAMOUNT = "partialAmount";
    private static final String REQUEST_PREPAYMENTAMOUNT = "prePaymentAmount";
    private static final String REQUEST_MODE = "mode";
    private static final String REQUEST_USEEXTPOS = "useExtPOS";
    private static final String REQUEST_ITEMS = "items";
    private static final String REQUEST_ITEM = "item";
    private static final String REQUEST_DEP = "dep";
    private static final String REQUEST_ADGCODE = "adgCode";
    private static final String REQUEST_PRODUCTCODE = "productCode";
    private static final String REQUEST_PRODUCTNAME = "productName";
    private static final String REQUEST_UNIT = "unit";
    private static final String REQUEST_QTY = "qty";
    private static final String REQUEST_PRICE = "price";
    private static final String REQUEST_DISCOUNT = "discount";
    private static final String REQUEST_DISCOUNTTYPE = "discountType";
    private static final String REQUEST_ADDITIONALDISCOUNT = "additionalDiscount";
    private static final String REQUEST_ADDITIONALDISCOUNTTYPE = "additionalDiscountType";

    private String fileName = null;

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public NewSaleRequest readFile() throws FileNotFoundException, XMLStreamException {
        boolean useExtPOS = false;
        double paidAmount = -1;
        double paidAmountCard = -1;
        double partialAmount = -1;
        double prePaymentAmount = -1;
        int dep = -1;
        int mode = -1;
        int seq = -1;

        Item item = null;
        List<Item> items = null;
        NewSaleRequest newSaleRequest = null;

        BigDecimal additionalDiscount = null;
        BigDecimal discount = null;
        BigDecimal qty = null;
        BigDecimal price = null;
        Integer additionalDiscountType = null;
        Integer discountType = null;
        String adgCode = null;
        String productCode = null;
        String productName = null;
        String unit = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new FileInputStream(this.getFileName());
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while(xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                newSaleRequest = new NewSaleRequest();
                newSaleRequest.setSeq(seq);
                newSaleRequest.setPaidAmount(paidAmount);
                newSaleRequest.setPaidAmountCard(paidAmountCard);
                newSaleRequest.setPartialAmount(partialAmount);
                newSaleRequest.setPrePaymentAmount(prePaymentAmount);
                newSaleRequest.setMode(mode);
                newSaleRequest.setUseExtPOS(useExtPOS);
                newSaleRequest.setItems(items);
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
                if(newSaleRequest != null) newSaleRequest.setSeq(seq);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String paidAmountString = xmlEvent.asCharacters().getData();
                try {
                    paidAmount = Double.parseDouble(paidAmountString);
                } catch(NumberFormatException numberFormatException) {
                    paidAmount = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNT)) {
                if(newSaleRequest != null) newSaleRequest.setPaidAmount(paidAmount);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNTCARD)) {
                xmlEvent = xmlEventReader.nextEvent();
                String paidAmountCardString = xmlEvent.asCharacters().getData();
                try {
                    paidAmountCard = Double.parseDouble(paidAmountCardString);
                } catch(NumberFormatException numberFormatException) {
                    paidAmountCard = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PAIDAMOUNTCARD)) {
                if(newSaleRequest != null) newSaleRequest.setPaidAmountCard(paidAmountCard);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PARTIALAMOUNT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String partialAmountString = xmlEvent.asCharacters().getData();
                try {
                    partialAmount = Double.parseDouble(partialAmountString);
                } catch(NumberFormatException numberFormatException) {
                    partialAmount = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PARTIALAMOUNT)) {
                if(newSaleRequest != null) newSaleRequest.setPartialAmount(partialAmount);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PREPAYMENTAMOUNT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String prePaymentAmountString = xmlEvent.asCharacters().getData();
                try {
                    prePaymentAmount = Double.parseDouble(prePaymentAmountString);
                } catch(NumberFormatException numberFormatException) {
                    prePaymentAmount = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PREPAYMENTAMOUNT)) {
                if(newSaleRequest != null) newSaleRequest.setPrePaymentAmount(prePaymentAmount);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_MODE)) {
                xmlEvent = xmlEventReader.nextEvent();
                String modeString = xmlEvent.asCharacters().getData();
                try {
                    mode = Integer.parseInt(modeString);
                } catch(NumberFormatException numberFormatException) {
                    mode = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_MODE)) {
                if(newSaleRequest != null) newSaleRequest.setMode(mode);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_USEEXTPOS)) {
                xmlEvent = xmlEventReader.nextEvent();
                String useExtPOSString = xmlEvent.asCharacters().getData();
                useExtPOS = Boolean.parseBoolean(useExtPOSString);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_USEEXTPOS)) {
                if(newSaleRequest != null) newSaleRequest.setUseExtPOS(useExtPOS);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_ITEMS)) {
                items = new ArrayList<>();
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_ITEM)) {
                dep = -1;
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
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_DEP)) {
                xmlEvent = xmlEventReader.nextEvent();
                String depString = xmlEvent.asCharacters().getData();
                try {
                    dep = Integer.parseInt(depString);
                } catch (NumberFormatException numberFormatException) {
                    dep = -1;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_DEP)) {
                if(item != null) item.setDep(dep);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_ADGCODE)) {
                xmlEvent = xmlEventReader.nextEvent();
                adgCode = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_ADGCODE)) {
                if(item != null) item.setAdgCode(adgCode);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PRODUCTCODE)) {
                xmlEvent = xmlEventReader.nextEvent();
                productCode = xmlEvent.asCharacters().getData();
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PRODUCTCODE)) {
                if(item != null) item.setProductCode(productCode);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PRODUCTNAME)) {
                xmlEvent = xmlEventReader.nextEvent();
                String productNameCodering = xmlEvent.asCharacters().getData();
                productName = CoderingConverter.convertCoderingStrToUnicodeStr(productNameCodering);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PRODUCTNAME)) {
                if(item != null) item.setProductName(productName);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_UNIT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String unitCodering = xmlEvent.asCharacters().getData();
                unit = CoderingConverter.convertCoderingStrToUnicodeStr(unitCodering);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_UNIT)) {
                if(unit != null) item.setUnit(unit);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_QTY)) {
                xmlEvent = xmlEventReader.nextEvent();
                String qtyString = xmlEvent.asCharacters().getData();
                try {
                    Double qtyDouble = Double.parseDouble(qtyString);
                    qty = BigDecimal.valueOf(qtyDouble);
                } catch (NumberFormatException numberFormatException) {
                    qty = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_QTY)) {
                if(item != null) item.setQty(qty);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_PRICE)) {
                xmlEvent = xmlEventReader.nextEvent();
                String priceString = xmlEvent.asCharacters().getData();
                try {
                    Double priceDouble = Double.parseDouble(priceString);
                    price = BigDecimal.valueOf(priceDouble);
                } catch (NumberFormatException numberFormatException) {
                    price = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_PRICE)) {
                if(item != null) item.setPrice(price);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_DISCOUNT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String discountString = xmlEvent.asCharacters().getData();
                try {
                    Double discountDouble = Double.parseDouble(discountString);
                    discount = BigDecimal.valueOf(discountDouble);
                } catch (NumberFormatException numberFormatException) {
                    discount = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_DISCOUNT)) {
                if(item != null) item.setDiscount(discount);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_DISCOUNTTYPE)) {
                xmlEvent = xmlEventReader.nextEvent();
                String discountTypeString = xmlEvent.asCharacters().getData();
                try {
                    discountType = Integer.parseInt(discountTypeString);
                } catch (NumberFormatException numberFormatException) {
                    discountType = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_DISCOUNTTYPE)) {
                if(item != null) item.setDiscountType(discountType);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_ADDITIONALDISCOUNT)) {
                xmlEvent = xmlEventReader.nextEvent();
                String additionalDiscountString = xmlEvent.asCharacters().getData();
                try {
                    Double additionalDiscountDouble = Double.parseDouble(additionalDiscountString);
                    additionalDiscount = BigDecimal.valueOf(additionalDiscountDouble);
                } catch (NumberFormatException numberFormatException) {
                    additionalDiscount = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_ADDITIONALDISCOUNT)) {
                if(item != null) item.setAdditionalDiscount(additionalDiscount);
                continue;
            }
            if(xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(REQUEST_ADDITIONALDISCOUNTTYPE)) {
                xmlEvent = xmlEventReader.nextEvent();
                String additionalDiscountTypeString = xmlEvent.asCharacters().getData();
                try {
                    additionalDiscountType = Integer.parseInt(additionalDiscountTypeString);
                } catch (NumberFormatException numberFormatException) {
                    additionalDiscountType = null;
                }
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_ADDITIONALDISCOUNTTYPE)) {
                if(item != null) item.setAdditionalDiscountType(additionalDiscountType);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_ITEM)) {
                if(!((item != null) && (item.getDep() != -1) && (item.getProductCode() != null)
                        && (item.getProductName() != null) && (item.getUnit() != null)
                        && (item.getQty() != null) && (item.getPrice() != null))) item = null;
                if(items != null) items.add(item);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST_ITEMS)) {
                if(items != null && items.size() > 0) {
                    for(Item good : items) {
                        if (good == null) {items = null; break;}
                    }
                } else items = null;
                if(newSaleRequest != null) newSaleRequest.setItems(items);
                continue;
            }
            if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(REQUEST)) {
                if(!((newSaleRequest != null)
                        && (newSaleRequest.getSeq() != -1)
                        && (newSaleRequest.getPaidAmount() != -1)
                        && (newSaleRequest.getPaidAmountCard() != -1)
                        && (newSaleRequest.getPartialAmount() != -1)
                        && (newSaleRequest.getPrePaymentAmount() != -1)
                        && (newSaleRequest.getMode() != -1)
                        && (newSaleRequest.getItems() != null))) newSaleRequest = null;
                break;
            }
        }
        return newSaleRequest;
    }
}