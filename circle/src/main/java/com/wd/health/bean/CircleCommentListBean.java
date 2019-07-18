package com.wd.health.bean;

import java.util.List;

public class CircleCommentListBean {


    /**
     * commentId : 165
     * commentTime : 1563354329000
     * commentUserId : 6
     * content : 了来了来了
     * headPic : http://172.17.8.100/images/health/user/head_pic/2019-06-25/20190625144828.jpg
     * nickName : 陈小胖
     * opinion : 0
     * opposeNum : 0
     * supportNum : 0
     * whetherDoctor : 2
     */

    public int type;
    public int commentId;
    public long commentTime;
    public int commentUserId;
    public String content;
    public String headPic;
    public String nickName;
    public int opinion;
    public int opposeNum;
    public int supportNum;
    public int whetherDoctor;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOpinion() {
        return opinion;
    }

    public void setOpinion(int opinion) {
        this.opinion = opinion;
    }

    public int getOpposeNum() {
        return opposeNum;
    }

    public void setOpposeNum(int opposeNum) {
        this.opposeNum = opposeNum;
    }

    public int getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(int supportNum) {
        this.supportNum = supportNum;
    }

    public int getWhetherDoctor() {
        return whetherDoctor;
    }

    public void setWhetherDoctor(int whetherDoctor) {
        this.whetherDoctor = whetherDoctor;
    }
}

