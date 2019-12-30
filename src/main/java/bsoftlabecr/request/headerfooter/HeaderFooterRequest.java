package bsoftlabecr.request.headerfooter;

import bsoftlabecr.entity.HeaderFooter;
import bsoftlabecr.request.general.CashRegisterRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HeaderFooterRequest extends CashRegisterRequest
        implements Comparable<HeaderFooterRequest> {

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
    public int compareTo(HeaderFooterRequest headerFooterRequest) {
        String compared = this.headers.toString() +
                this.footers.toString();
        String comparable = headerFooterRequest.getHeaders().toString() +
                headerFooterRequest.getFooters().toString();
        return compared.compareTo(comparable);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        HeaderFooterRequest headerFooterRequest = (HeaderFooterRequest) object;
        return this.headers.equals(headerFooterRequest.getHeaders()) &&
                this.footers.equals(headerFooterRequest.getFooters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.headers.toString(),
                this.footers.toString());
    }

    @Override
    public String toString() {
        return "[" +
                "headers: " + this.headers.toString() + ", " +
                "footers: " + this.footers.toString() + "]";
    }
}
