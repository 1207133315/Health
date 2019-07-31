package com.wd.health.bean;

import java.util.List;

public class PatientsCircleBean {

    //我的病友圈类

    /**
     * result : [{"amount":10,"collectionNum":0,"commentNum":0,"detail":"疼起来像针扎一样","releaseTime":1564070400000,"sickCircleId":594,"title":"头疼的厉害"}]
     * message : 查询成功
     * status : 0000
     */


        /**
         * amount : 10
         * collectionNum : 0
         * commentNum : 0
         * detail : 疼起来像针扎一样
         * releaseTime : 1564070400000
         * sickCircleId : 594
         * title : 头疼的厉害
         */

        private int amount;
        private int collectionNum;
        private int commentNum;
        private String detail;
        private long releaseTime;
        private int sickCircleId;
        private String title;

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

