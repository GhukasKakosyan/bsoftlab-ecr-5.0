package bsoftlabecr.request.report;

import bsoftlabecr.request.general.SequenceRequest;

import java.util.Date;

public class FiscalReportRequest extends SequenceRequest {
    private Date startDate = null;                 // Ժամանակահատվածի սկիզբ
    private Date endDate = null;                   // Ժամանակահատվածի վերջ
    private Integer reportType = null;             // Հաշվետվության տեսակի ընտրություն: 1 - X հաշվետվություն; 2 - Z հաշվետվություն:
    private Integer cashierId = null;              // Գանձապահի ընտրություն
    private Integer deptId = null;                 // Բաժնի ընտրություն
    private Integer transactionTypeId = null;      // Վճարման եղանակ՝ 1 - առձեռն, 2 - անկանխիկ, null - բոլորը:

    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
    public Integer getReportType() {
        return this.reportType;
    }
    public Integer getCashierId() {
        return this.cashierId;
    }
    public Integer getDeptId() {
        return this.deptId;
    }
    public Integer getTransactionTypeId() {
        return this.transactionTypeId;
    }

    public void setStartDate(Date startDate) {this.startDate = startDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}
    public void setReportType(Integer reportType) {this.reportType = reportType;}
    public void setCashierId(Integer cashierId) {this.cashierId = cashierId;}
    public void setDeptId(Integer deptId) {this.deptId = deptId;}
    public void setTransactionTypeId(Integer transactionTypeId) {this.transactionTypeId = transactionTypeId;}
}
