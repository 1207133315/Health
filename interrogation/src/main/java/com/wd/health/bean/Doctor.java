package com.wd.health.bean;

public class Doctor {

    /**
     * badNum : 0
     * doctorId : 12
     * doctorName : 陈小
     * imagePic : http://172.17.8.100/images/health/doctor/system_image_pic/system_image7.jpg
     * inauguralHospital : 北京第一骨科医院
     * jobTitle : 主治医师
     * praise : 0.00%
     * praiseNum : 0
     * serverNum : 0
     * servicePrice : 500
     */
    private boolean isSelect;
    private int badNum;
    private int doctorId;
    private String doctorName;
    private String imagePic;
    private String inauguralHospital;
    private String jobTitle;
    private String praise;
    private int praiseNum;
    private int serverNum;
    private int servicePrice;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getBadNum() {
        return badNum;
    }

    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getImagePic() {
        return imagePic;
    }

    public void setImagePic(String imagePic) {
        this.imagePic = imagePic;
    }

    public String getInauguralHospital() {
        return inauguralHospital;
    }

    public void setInauguralHospital(String inauguralHospital) {
        this.inauguralHospital = inauguralHospital;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getServerNum() {
        return serverNum;
    }

    public void setServerNum(int serverNum) {
        this.serverNum = serverNum;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Doctor(boolean isSelect, int badNum, int doctorId, String doctorName, String imagePic, String inauguralHospital, String jobTitle, String praise, int praiseNum, int serverNum, int servicePrice) {
        this.isSelect = isSelect;
        this.badNum = badNum;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.imagePic = imagePic;
        this.inauguralHospital = inauguralHospital;
        this.jobTitle = jobTitle;
        this.praise = praise;
        this.praiseNum = praiseNum;
        this.serverNum = serverNum;
        this.servicePrice = servicePrice;
    }
}
