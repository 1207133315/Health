package com.wd.health.bean;

public class BankCardBean {

    /**
     * bankCardNumber : 622848 0018942802879
     * bankCardType : 1
     * bankName : 农业银行
     * bindTime : 1565020800000
     * userId : 53
     */

    private String bankCardNumber;
    private int bankCardType;
    private String bankName;
    private long bindTime;
    private int userId;

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public int getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(int bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getBindTime() {
        return bindTime;
    }

    public void setBindTime(long bindTime) {
        this.bindTime = bindTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
