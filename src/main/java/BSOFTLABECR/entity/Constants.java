package BSOFTLABECR.entity;

public class Constants {
    private String crn = null;
    private String ip = null;
    private int port = 0;
    private String password = null;
    private int cashierId = 0;
    private String cashierPassword = null;

    public String getCrn() {
        return this.crn;
    }
    public String getIp() {
        return this.ip;
    }
    public int getPort() {
        return this.port;
    }
    public String getPassword() {
        return this.password;
    }
    public int getCashierId() {
        return this.cashierId;
    }
    public String getCashierPassword() {
        return this.cashierPassword;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }
    public void setCashierPassword(String cashierPassword) {
        this.cashierPassword = cashierPassword;
    }
}
