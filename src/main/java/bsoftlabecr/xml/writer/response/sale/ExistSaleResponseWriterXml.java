package bsoftlabecr.xml.writer.response.sale;

import bsoftlabecr.entity.ResponseType;
import bsoftlabecr.exception.XmlFileWriteException;
import bsoftlabecr.response.receipt.sale.ExistSaleResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class ExistSaleResponseWriterXml {
    private static final String ENTER_KEY = Character.toString((char)13);
    private static final String TAB_KEY = Character.toString((char)9);
    private static final String TOTALSNULL = "null";

    private static final String XML_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String XML_TAG_OPEN = "<ExistSaleResponse>";
    private static final String XML_TAG_CLOSE = "</ExistSaleResponse>";
    private static final String XML_TAG_CID_OPEN = "<cid>";
    private static final String XML_TAG_CID_CLOSE = "</cid>";
    private static final String XML_TAG_TIME_OPEN = "<time>";
    private static final String XML_TAG_TIME_CLOSE = "</time>";
    private static final String XML_TAG_TA_OPEN = "<ta>";
    private static final String XML_TAG_TA_CLOSE = "</ta>";
    private static final String XML_TAG_CASH_OPEN = "<cash>";
    private static final String XML_TAG_CASH_CLOSE = "</cash>";
    private static final String XML_TAG_CARD_OPEN = "<card>";
    private static final String XML_TAG_CARD_CLOSE = "</card>";
    private static final String XML_TAG_PPA_OPEN = "<ppa>";
    private static final String XML_TAG_PPA_CLOSE = "</ppa>";
    private static final String XML_TAG_PPU_OPEN = "<ppu>";
    private static final String XML_TAG_PPU_CLOSE = "</ppu>";
    private static final String XML_TAG_TYPE_OPEN = "<type>";
    private static final String XML_TAG_TYPE_CLOSE = "</type>";
    private static final String XML_TAG_REF_OPEN = "<ref>";
    private static final String XML_TAG_REF_CLOSE = "</ref>";
    private static final String XML_TAG_REFCRN_OPEN = "<refcrn>";
    private static final String XML_TAG_REFCRN_CLOSE = "</refcrn>";
    private static final String XML_TAG_SALETYPE_OPEN = "<saleType>";
    private static final String XML_TAG_SALETYPE_CLOSE = "</saleType>";
    private static final String XML_TAG_RCODE_OPEN = "<rcode>";
    private static final String XML_TAG_RCODE_CLOSE = "</rcode>";

    private static final String XML_TAG_TOTALS_OPEN = "<totals>";
    private static final String XML_TAG_TOTALS_CLOSE = "</totals>";
    
    private static final String XML_TAG_SUBTOTAL_OPEN = "<subtotal>";
    private static final String XML_TAG_SUBTOTAL_CLOSE = "</subtotal>";
    private static final String XML_TAG_SUBTOTAL_DID_OPEN = "<did>";
    private static final String XML_TAG_SUBTOTAL_DID_CLOSE = "</did>";
    private static final String XML_TAG_SUBTOTAL_DT_OPEN = "<dt>";
    private static final String XML_TAG_SUBTOTAL_DT_CLOSE = "</dt>";
    private static final String XML_TAG_SUBTOTAL_DTM_OPEN = "<dtm>";
    private static final String XML_TAG_SUBTOTAL_DTM_CLOSE = "</dtm>";
    private static final String XML_TAG_SUBTOTAL_T_OPEN = "<t>";
    private static final String XML_TAG_SUBTOTAL_T_CLOSE = "</t>";
    private static final String XML_TAG_SUBTOTAL_TT_OPEN = "<tt>";
    private static final String XML_TAG_SUBTOTAL_TT_CLOSE = "</tt>";
    private static final String XML_TAG_SUBTOTAL_GC_OPEN = "<gc>";
    private static final String XML_TAG_SUBTOTAL_GC_CLOSE = "</gc>";
    private static final String XML_TAG_SUBTOTAL_GN_OPEN = "<gn>";
    private static final String XML_TAG_SUBTOTAL_GN_CLOSE = "</gn>";
    private static final String XML_TAG_SUBTOTAL_QTY_OPEN = "<qty>";
    private static final String XML_TAG_SUBTOTAL_QTY_CLOSE = "</qty>";
    private static final String XML_TAG_SUBTOTAL_P_OPEN = "<p>";
    private static final String XML_TAG_SUBTOTAL_P_CLOSE = "</p>";
    private static final String XML_TAG_SUBTOTAL_ADG_OPEN = "<adg>";
    private static final String XML_TAG_SUBTOTAL_ADG_CLOSE = "</adg>";
    private static final String XML_TAG_SUBTOTAL_MU_OPEN = "<mu>";
    private static final String XML_TAG_SUBTOTAL_MU_CLOSE = "</mu>";
    private static final String XML_TAG_SUBTOTAL_DSC_OPEN = "<dsc>";
    private static final String XML_TAG_SUBTOTAL_DSC_CLOSE = "</dsc>";
    private static final String XML_TAG_SUBTOTAL_ADSC_OPEN = "<adsc>";
    private static final String XML_TAG_SUBTOTAL_ADSC_CLOSE = "</adsc>";
    private static final String XML_TAG_SUBTOTAL_DSCT_OPEN = "<dsct>";
    private static final String XML_TAG_SUBTOTAL_DSCT_CLOSE = "</dsct>";
    private static final String XML_TAG_SUBTOTAL_RPID_OPEN = "<rpid>";
    private static final String XML_TAG_SUBTOTAL_RPID_CLOSE = "</rpid>";

    private ExistSaleResponse existSaleResponse;
    private String fileName;

    public ExistSaleResponseWriterXml(
            ExistSaleResponse existSaleResponse, String fileName) {
        this.existSaleResponse = existSaleResponse;
        this.fileName = fileName;
    }

    public ExistSaleResponse getExistSaleResponse() {
        return this.existSaleResponse;
    }
    public String getFileName() {
        return this.fileName;
    }

    public void write() throws XmlFileWriteException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName);
            Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(XML_TITLE + ENTER_KEY);
            writer.write(XML_TAG_OPEN + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CID_OPEN +
                    this.existSaleResponse.getCid() + XML_TAG_CID_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TIME_OPEN +
                    this.existSaleResponse.getTime() + XML_TAG_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TA_OPEN +
                    this.existSaleResponse.getTa() + XML_TAG_TA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CASH_OPEN +
                    this.existSaleResponse.getCash() + XML_TAG_CASH_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_CARD_OPEN +
                    this.existSaleResponse.getCard() + XML_TAG_CARD_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_PPA_OPEN +
                    this.existSaleResponse.getPpa() + XML_TAG_PPA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_PPU_OPEN +
                    this.existSaleResponse.getPpu() + XML_TAG_PPU_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_TYPE_OPEN +
                    this.existSaleResponse.getType() + XML_TAG_TYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_REF_OPEN +
                    this.existSaleResponse.getRef() + XML_TAG_REF_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_REFCRN_OPEN +
                    this.existSaleResponse.getRefcrn() + XML_TAG_REFCRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_SALETYPE_OPEN +
                    this.existSaleResponse.getSaleType() + XML_TAG_SALETYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + XML_TAG_RCODE_OPEN +
                    this.existSaleResponse.getResponseCode() + XML_TAG_RCODE_CLOSE + ENTER_KEY);

            if (existSaleResponse.totals != null && existSaleResponse.totals.length > 0) {
                writer.write(TAB_KEY + XML_TAG_TOTALS_OPEN + ENTER_KEY);
                for (ExistSaleResponse.SubTotal subTotal : existSaleResponse.totals) {
                    writer.write(TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_OPEN + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_DID_OPEN +
                            subTotal.getDid() + XML_TAG_SUBTOTAL_DID_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_DT_OPEN +
                            subTotal.getDt() + XML_TAG_SUBTOTAL_DT_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_DTM_OPEN +
                            subTotal.getDtm() + XML_TAG_SUBTOTAL_DTM_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_T_OPEN +
                            subTotal.getT() + XML_TAG_SUBTOTAL_T_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_TT_OPEN +
                            subTotal.getTt() + XML_TAG_SUBTOTAL_TT_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_GC_OPEN +
                            subTotal.getGc() + XML_TAG_SUBTOTAL_GC_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_GN_OPEN +
                            subTotal.getGn() + XML_TAG_SUBTOTAL_GN_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_QTY_OPEN +
                            subTotal.getQty() + XML_TAG_SUBTOTAL_QTY_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_P_OPEN +
                            subTotal.getP() + XML_TAG_SUBTOTAL_P_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_ADG_OPEN +
                            subTotal.getAdg() + XML_TAG_SUBTOTAL_ADG_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_MU_OPEN +
                            subTotal.getMu() + XML_TAG_SUBTOTAL_MU_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_DSC_OPEN +
                            subTotal.getDsc() + XML_TAG_SUBTOTAL_DSC_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_ADSC_OPEN +
                            subTotal.getAdsc() + XML_TAG_SUBTOTAL_ADSC_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_DSCT_OPEN +
                            subTotal.getDsct() + XML_TAG_SUBTOTAL_DSCT_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_RPID_OPEN +
                            subTotal.getRpid() + XML_TAG_SUBTOTAL_RPID_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + XML_TAG_SUBTOTAL_CLOSE + ENTER_KEY);
                }
                writer.write(TAB_KEY + XML_TAG_TOTALS_CLOSE + ENTER_KEY);
            } else {
                writer.write(TAB_KEY + XML_TAG_TOTALS_OPEN + TOTALSNULL + XML_TAG_TOTALS_CLOSE + ENTER_KEY);
            }
            writer.write(XML_TAG_CLOSE + ENTER_KEY);
            writer.close();

        } catch (IOException ioException) {
            throw new XmlFileWriteException(ResponseType
                    .EXIST_SALE_RESPONSE_XML_FILE_WRITE_ERROR.getCode());
        }
    }
}