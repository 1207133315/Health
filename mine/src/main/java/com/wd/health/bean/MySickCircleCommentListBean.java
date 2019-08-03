package com.wd.health.bean;

import java.util.List;

public class MySickCircleCommentListBean {


    /**
     * result : {"adoptionSickCircleComment":{"commentId":438,"commentTime":1564562485000,"commentUserId":58,"content":"身体不舒服，就多休息一会。","headPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","nickName":"n3_SAWFB","opinion":0,"opposeNum":0,"supportNum":0,"whetherDoctor":2},"otherSickCircleCommentList":[{"commentId":438,"commentTime":1564562485000,"commentUserId":58,"content":"身体不舒服，就多休息一会。","headPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","nickName":"n3_SAWFB","opinion":0,"opposeNum":0,"supportNum":0,"whetherDoctor":2},{"commentId":434,"commentTime":1564561955000,"commentUserId":58,"content":"感冒了，就喝感冒灵。","headPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","nickName":"n3_SAWFB","opinion":0,"opposeNum":0,"supportNum":0,"whetherDoctor":2}]}
     * message : 查询成功
     * status : 0000
     */


        /**
         * adoptionSickCircleComment : {"commentId":438,"commentTime":1564562485000,"commentUserId":58,"content":"身体不舒服，就多休息一会。","headPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","nickName":"n3_SAWFB","opinion":0,"opposeNum":0,"supportNum":0,"whetherDoctor":2}
         * otherSickCircleCommentList : [{"commentId":438,"commentTime":1564562485000,"commentUserId":58,"content":"身体不舒服，就多休息一会。","headPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","nickName":"n3_SAWFB","opinion":0,"opposeNum":0,"supportNum":0,"whetherDoctor":2},{"commentId":434,"commentTime":1564561955000,"commentUserId":58,"content":"感冒了，就喝感冒灵。","headPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","nickName":"n3_SAWFB","opinion":0,"opposeNum":0,"supportNum":0,"whetherDoctor":2}]
         */

        private AdoptionSickCircleCommentBean adoptionSickCircleComment;
        private List<OtherSickCircleCommentListBean> otherSickCircleCommentList;

        public AdoptionSickCircleCommentBean getAdoptionSickCircleComment() {
            return adoptionSickCircleComment;
        }

        public void setAdoptionSickCircleComment(AdoptionSickCircleCommentBean adoptionSickCircleComment) {
            this.adoptionSickCircleComment = adoptionSickCircleComment;
        }

        public List<OtherSickCircleCommentListBean> getOtherSickCircleCommentList() {
            return otherSickCircleCommentList;
        }

        public void setOtherSickCircleCommentList(List<OtherSickCircleCommentListBean> otherSickCircleCommentList) {
            this.otherSickCircleCommentList = otherSickCircleCommentList;
        }

        public static class AdoptionSickCircleCommentBean {
            /**
             * commentId : 438
             * commentTime : 1564562485000
             * commentUserId : 58
             * content : 身体不舒服，就多休息一会。
             * headPic : http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg
             * nickName : n3_SAWFB
             * opinion : 0
             * opposeNum : 0
             * supportNum : 0
             * whetherDoctor : 2
             */

            private int commentId;
            private long commentTime;
            private int commentUserId;
            private String content;
            private String headPic;
            private String nickName;
            private int opinion;
            private int opposeNum;
            private int supportNum;
            private int whetherDoctor;

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

        public static class OtherSickCircleCommentListBean {
            /**
             * commentId : 438
             * commentTime : 1564562485000
             * commentUserId : 58
             * content : 身体不舒服，就多休息一会。
             * headPic : http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg
             * nickName : n3_SAWFB
             * opinion : 0
             * opposeNum : 0
             * supportNum : 0
             * whetherDoctor : 2
             */

            private int commentId;
            private long commentTime;
            private int commentUserId;
            private String content;
            private String headPic;
            private String nickName;
            private int opinion;
            private int opposeNum;
            private int supportNum;
            private int whetherDoctor;

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
    }

