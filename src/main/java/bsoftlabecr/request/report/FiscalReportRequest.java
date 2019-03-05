package bsoftlabecr.request.report;

import bsoftlabecr.request.general.SequenceRequest;

import java.util.Date;

public class FiscalReportRequest extends SequenceRequest {
    private Date startDate;                 // Ժամանակահատվածի սկիզբ
    private Date endDate;                   // Ժամանակահատվածի վերջ
    private int reportType;                 // Հաշվետվության տեսակի ընտրություն: 1 - X հաշվետվություն; 2 - Z հաշվետվություն:
    private Integer cashierId;              // Գանձապահի ընտրություն
    private Integer deptId;                 // Բաժնի ընտրություն
    private Integer transactionTypeId;      // Վճարման եղանակ՝ 1 - առձեռն, 2 - անկանխիկ, null - բոլորը:

    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
    public int getReportType() {
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
    public void setReportType(int reportType) {this.reportType = reportType;}
    public void setCashierId(Integer cashierId) {this.cashierId = cashierId;}
    public void setDeptId(Integer deptId) {this.deptId = deptId;}
    public void setTransactionTypeId(Integer transactionTypeId) {this.transactionTypeId = transactionTypeId;}
}
