package com.wd.health.bean;

import java.util.List;

public class CircleUserInfoBean {

    /**
     * amount : 0
     * collectionNum : 1
     * commentNum : 2
     * detail : 详情
     * releaseTime : 1555948800000
     * sickCircleId : 4
     * title : 急寻：面神经炎的治疗方法
     */
    public int type;
    public int amount;
    public int collectionNum;
    public int commentNum;
    public String detail;
    public long releaseTime;
    public int sickCircleId;
    public String title;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
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
}

