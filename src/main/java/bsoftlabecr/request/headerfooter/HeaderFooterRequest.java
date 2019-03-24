package bsoftlabecr.request.headerfooter;

import bsoftlabecr.entity.HeaderFooter;
import bsoftlabecr.request.general.CashRegisterRequest;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterRequest extends CashRegisterRequest {
    private List<HeaderFooter> headers = new ArrayList<>();
    private List<HeaderFooter> footers = new ArrayList<>();

    public List<HeaderFooter> getHeaders() {
        return this.headers;
    }
    public List<HeaderFooter> getFooters() {
        return this.footers;
    }

    public void setHeaders(List<HeaderFooter> headers) {
        this.headers = headers;
    }
    public void setFooters(List<HeaderFooter> footers) {
        this.footers = footers;
    }

    @Override
    public String toString() {
        return "[" +
                this.headers + ", " +
                this.footers + "]";
    }
}
