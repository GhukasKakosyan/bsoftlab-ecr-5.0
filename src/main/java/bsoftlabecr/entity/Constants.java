package bsoftlabecr.entity;

public class Constants {
    private String crn = null;
    private String ip = null;
    private Integer port = null;
    private String password = null;
    private Integer cashierId = null;
    private String cashierPassword = null;

    public String getCrn() {
        return this.crn;
    }
    public String getIp() {
        return this.ip;
    }
    public Integer getPort() {
        return this.port;
    }
    public String getPassword() {
        return this.password;
    }
    public Integer getCashierId() {
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
    public void setPort(Integer port) {
        this.port = port;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }
    public void setCashierPassword(String cashierPassword) {
        this.cashierPassword = cashierPassword;
    }

    @Override
    public String toString() {
        return "[" +
                "crn: " + this.crn + ", " +
                "ip: " + this.ip + ", " +
                "port: " + this.port + ", " +
                "password: " + this.password + ", " +
                "cashierId: " + this.cashierId + ", " +
                "cashierPassword: " + this.cashierPassword + "]";
    }
}