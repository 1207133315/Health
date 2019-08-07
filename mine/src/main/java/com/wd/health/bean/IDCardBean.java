package com.wd.health.bean;

public class IDCardBean {
    private int userId;
    private String name;
    private String sex;
    private String nation;
    private String idNumber;

    public IDCardBean(int userId, String name, String sex, String nation, String idNumber) {
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.idNumber = idNumber;
    }

    public IDCardBean() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "IDCardBean{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}
