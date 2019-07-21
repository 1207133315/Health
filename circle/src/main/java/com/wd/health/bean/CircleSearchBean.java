package com.wd.health.bean;

public class CircleSearchBean {


    public long id;
    public int type;
    public int sickCircleId;
    public String title ;
    public String detail ;
    public int amount;
    public int collectionNum;
    public int commentNum;
    public long releaseTime;

    public CircleSearchBean(long id, int type, int sickCircleId, String title, String detail, int amount, int collectionNum, int commentNum, long releaseTime) {
        this.id = id;
        this.type = type;
        this.sickCircleId = sickCircleId;
        this.title = title;
        this.detail = detail;
        this.amount = amount;
        this.collectionNum = collectionNum;
        this.commentNum = commentNum;
        this.releaseTime = releaseTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSickCircleId() {
        return sickCircleId;
    }

    public void setSickCircleId(int sickCircleId) {
        this.sickCircleId = sickCircleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }
}
