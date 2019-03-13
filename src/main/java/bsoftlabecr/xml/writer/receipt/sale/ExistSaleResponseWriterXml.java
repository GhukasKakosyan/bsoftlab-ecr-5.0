package bsoftlabecr.xml.writer.receipt.sale;

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

    private static final String RESPONSE_TITLE = "<?xml version" + " = " + '"' + "1.0" + '"'
            + " encoding" + " = " + '"' + "UTF-8" + '"' + " standalone" + " = " + '"' + "yes" + '"' + "?>";

    private static final String RESPONSE_OPEN = "<ExistSaleResponse>";
    private static final String RESPONSE_CLOSE = "</ExistSaleResponse>";
    private static final String RESPONSE_CID_OPEN = "<cid>";
    private static final String RESPONSE_CID_CLOSE = "</cid>";
    private static final String RESPONSE_TIME_OPEN = "<time>";
    private static final String RESPONSE_TIME_CLOSE = "</time>";
    private static final String RESPONSE_TA_OPEN = "<ta>";
    private static final String RESPONSE_TA_CLOSE = "</ta>";
    private static final String RESPONSE_CASH_OPEN = "<cash>";
    private static final String RESPONSE_CASH_CLOSE = "</cash>";
    private static final String RESPONSE_CARD_OPEN = "<card>";
    private static final String RESPONSE_CARD_CLOSE = "</card>";
    private static final String RESPONSE_PPA_OPEN = "<ppa>";
    private static final String RESPONSE_PPA_CLOSE = "</ppa>";
    private static final String RESPONSE_PPU_OPEN = "<ppu>";
    private static final String RESPONSE_PPU_CLOSE = "</ppu>";
    private static final String RESPONSE_TYPE_OPEN = "<type>";
    private static final String RESPONSE_TYPE_CLOSE = "</type>";
    private static final String RESPONSE_REF_OPEN = "<ref>";
    private static final String RESPONSE_REF_CLOSE = "</ref>";
    private static final String RESPONSE_REFCRN_OPEN = "<refcrn>";
    private static final String RESPONSE_REFCRN_CLOSE = "</refcrn>";
    private static final String RESPONSE_SALETYPE_OPEN = "<saleType>";
    private static final String RESPONSE_SALETYPE_CLOSE = "</saleType>";
    private static final String RESPONSE_CODE_OPEN = "<rcode>";
    private static final String RESPONSE_CODE_CLOSE = "</rcode>";

    private static final String RESPONSE_TOTALS_OPEN = "<totals>";
    private static final String RESPONSE_TOTALS_CLOSE = "</totals>";
    
    private static final String RESPONSE_SUBTOTAL_OPEN = "<subtotal>";
    private static final String RESPONSE_SUBTOTAL_CLOSE = "</subtotal>";
    private static final String RESPONSE_SUBTOTAL_DID_OPEN = "<did>";
    private static final String RESPONSE_SUBTOTAL_DID_CLOSE = "</did>";
    private static final String RESPONSE_SUBTOTAL_DT_OPEN = "<dt>";
    private static final String RESPONSE_SUBTOTAL_DT_CLOSE = "</dt>";
    private static final String RESPONSE_SUBTOTAL_DTM_OPEN = "<dtm>";
    private static final String RESPONSE_SUBTOTAL_DTM_CLOSE = "</dtm>";
    private static final String RESPONSE_SUBTOTAL_T_OPEN = "<t>";
    private static final String RESPONSE_SUBTOTAL_T_CLOSE = "</t>";
    private static final String RESPONSE_SUBTOTAL_TT_OPEN = "<tt>";
    private static final String RESPONSE_SUBTOTAL_TT_CLOSE = "</tt>";
    private static final String RESPONSE_SUBTOTAL_GC_OPEN = "<gc>";
    private static final String RESPONSE_SUBTOTAL_GC_CLOSE = "</gc>";
    private static final String RESPONSE_SUBTOTAL_GN_OPEN = "<gn>";
    private static final String RESPONSE_SUBTOTAL_GN_CLOSE = "</gn>";
    private static final String RESPONSE_SUBTOTAL_QTY_OPEN = "<qty>";
    private static final String RESPONSE_SUBTOTAL_QTY_CLOSE = "</qty>";
    private static final String RESPONSE_SUBTOTAL_P_OPEN = "<p>";
    private static final String RESPONSE_SUBTOTAL_P_CLOSE = "</p>";
    private static final String RESPONSE_SUBTOTAL_ADG_OPEN = "<adg>";
    private static final String RESPONSE_SUBTOTAL_ADG_CLOSE = "</adg>";
    private static final String RESPONSE_SUBTOTAL_MU_OPEN = "<mu>";
    private static final String RESPONSE_SUBTOTAL_MU_CLOSE = "</mu>";
    private static final String RESPONSE_SUBTOTAL_DSC_OPEN = "<dsc>";
    private static final String RESPONSE_SUBTOTAL_DSC_CLOSE = "</dsc>";
    private static final String RESPONSE_SUBTOTAL_ADSC_OPEN = "<adsc>";
    private static final String RESPONSE_SUBTOTAL_ADSC_CLOSE = "</adsc>";
    private static final String RESPONSE_SUBTOTAL_DSCT_OPEN = "<dsct>";
    private static final String RESPONSE_SUBTOTAL_DSCT_CLOSE = "</dsct>";
    private static final String RESPONSE_SUBTOTAL_RPID_OPEN = "<rpid>";
    private static final String RESPONSE_SUBTOTAL_RPID_CLOSE = "</rpid>";

    private String fileName = null;

    public ExistSaleResponseWriterXml() {}

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void writeFile(ExistSaleResponse existSaleResponse)
            throws XmlFileWriteException {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.getFileName());
            Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(RESPONSE_TITLE + ENTER_KEY);
            writer.write(RESPONSE_OPEN + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CID_OPEN + existSaleResponse.getCid() + RESPONSE_CID_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TIME_OPEN + existSaleResponse.getTime() + RESPONSE_TIME_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TA_OPEN + existSaleResponse.getTa() + RESPONSE_TA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CASH_OPEN + existSaleResponse.getCash() + RESPONSE_CASH_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CARD_OPEN + existSaleResponse.getCard() + RESPONSE_CARD_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_PPA_OPEN + existSaleResponse.getPpa() + RESPONSE_PPA_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_PPU_OPEN + existSaleResponse.getPpu() + RESPONSE_PPU_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_TYPE_OPEN + existSaleResponse.getType() + RESPONSE_TYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_REF_OPEN + existSaleResponse.getRef() + RESPONSE_REF_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_REFCRN_OPEN + existSaleResponse.getRefcrn() + RESPONSE_REFCRN_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_SALETYPE_OPEN + existSaleResponse.getSaleType() + RESPONSE_SALETYPE_CLOSE + ENTER_KEY);
            writer.write(TAB_KEY + RESPONSE_CODE_OPEN + existSaleResponse.getResponseCode() + RESPONSE_CODE_CLOSE + ENTER_KEY);

            if (existSaleResponse.totals != null && existSaleResponse.totals.length > 0) {
                writer.write(TAB_KEY + RESPONSE_TOTALS_OPEN + ENTER_KEY);
                for (ExistSaleResponse.SubTotal subTotal : existSaleResponse.totals) {
                    writer.write(TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_OPEN + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_DID_OPEN + subTotal.getDid() + RESPONSE_SUBTOTAL_DID_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_DT_OPEN + subTotal.getDt() + RESPONSE_SUBTOTAL_DT_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_DTM_OPEN + subTotal.getDtm() + RESPONSE_SUBTOTAL_DTM_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_T_OPEN + subTotal.getT() + RESPONSE_SUBTOTAL_T_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_TT_OPEN + subTotal.getTt() + RESPONSE_SUBTOTAL_TT_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_GC_OPEN + subTotal.getGc() + RESPONSE_SUBTOTAL_GC_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_GN_OPEN + subTotal.getGn() + RESPONSE_SUBTOTAL_GN_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_QTY_OPEN + subTotal.getQty() + RESPONSE_SUBTOTAL_QTY_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_P_OPEN + subTotal.getP() + RESPONSE_SUBTOTAL_P_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_ADG_OPEN + subTotal.getAdg() + RESPONSE_SUBTOTAL_ADG_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_MU_OPEN + subTotal.getMu() + RESPONSE_SUBTOTAL_MU_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_DSC_OPEN + subTotal.getDsc() + RESPONSE_SUBTOTAL_DSC_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_ADSC_OPEN + subTotal.getAdsc() + RESPONSE_SUBTOTAL_ADSC_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_DSCT_OPEN + subTotal.getDsct() + RESPONSE_SUBTOTAL_DSCT_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_RPID_OPEN + subTotal.getRpid() + RESPONSE_SUBTOTAL_RPID_CLOSE + ENTER_KEY);
                    writer.write(TAB_KEY + TAB_KEY + RESPONSE_SUBTOTAL_CLOSE + ENTER_KEY);
                }
                writer.write(TAB_KEY + RESPONSE_TOTALS_CLOSE + ENTER_KEY);
            } else {
                writer.write(TAB_KEY + RESPONSE_TOTALS_OPEN + TOTALSNULL + RESPONSE_TOTALS_CLOSE + ENTER_KEY);
            }
            writer.write(RESPONSE_CLOSE + ENTER_KEY);
            writer.close();
        } catch (IOException ioException) {
            throw new XmlFileWriteException(ioException.getMessage());
        }
    }
}