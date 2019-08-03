package com.wd.health.bean;

import java.util.List;

public class BeAdoptedBean {


    /**
     * result : [{"adoptTime":1564588800000,"content":"嗓子疼吃点胖大海。","disease":"喉炎","releaseUserHeadPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","releaseUserId":58,"releaseUserNickName":"n3_SAWFB","title":"嗓子有些疼"},{"adoptTime":1564588800000,"content":"放松一下自己，喝点茶水。","disease":"神经衰弱","releaseUserHeadPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","releaseUserId":58,"releaseUserNickName":"n3_SAWFB","title":"菲菲的精神不好怎么办"},{"adoptTime":1564502400000,"content":"身体不舒服，就多休息一会。","disease":"慢性鼻炎","releaseUserHeadPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","releaseUserId":58,"releaseUserNickName":"n3_SAWFB","title":"感冒了怎么办"},{"adoptTime":1564502400000,"content":"生病了，就多休息，多喝热水，多吃饭。","disease":"偏头疼","releaseUserHeadPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","releaseUserId":58,"releaseUserNickName":"n3_SAWFB","title":"头疼的厉害"},{"adoptTime":1564502400000,"content":"身体不舒服，就多休息一会。","disease":"痛 风","releaseUserHeadPic":"http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg","releaseUserId":58,"releaseUserNickName":"n3_SAWFB","title":"头难受"}]
     * message : 查询成功
     * status : 0000
     */

        /**
         * adoptTime : 1564588800000
         * content : 嗓子疼吃点胖大海。
         * disease : 喉炎
         * releaseUserHeadPic : http://172.17.8.100/images/health/user/head_pic/default/default_head_6.jpg
         * releaseUserId : 58
         * releaseUserNickName : n3_SAWFB
         * title : 嗓子有些疼
         */

        private long adoptTime;
        private String content;
        private String disease;
        private String releaseUserHeadPic;
        private int releaseUserId;
        private String releaseUserNickName;
        private String title;

        public long getAdoptTime() {
            return adoptTime;
        }

        public void setAdoptTime(long adoptTime) {
            this.adoptTime = adoptTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public String getReleaseUserHeadPic() {
            return releaseUserHeadPic;
        }

        public void setReleaseUserHeadPic(String releaseUserHeadPic) {
            this.releaseUserHeadPic = releaseUserHeadPic;
        }

        public int getReleaseUserId() {
            return releaseUserId;
        }

        public void setReleaseUserId(int releaseUserId) {
            this.releaseUserId = releaseUserId;
        }

        public String getReleaseUserNickName() {
            return releaseUserNickName;
        }

        public void setReleaseUserNickName(String releaseUserNickName) {
            this.releaseUserNickName = releaseUserNickName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

