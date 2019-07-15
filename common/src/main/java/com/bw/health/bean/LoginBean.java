package com.bw.health.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LoginBean {

    /**
     * age : 20
     * email : 1510621690@qq.com
     * headPic : http://172.17.8.100/images/health/user/head_pic/2019-04-02/20190402112521.jpg
     * height : 180
     * id : 1
     * invitationCode : RTTNASJBQI
     * jiGuangPwd : R+0jdN3P4MXHPMFVe9cX5MbX5ulIXHJkfigPLKEeTBY5lUgxJWUNg0js1oGtbsKiLFL4ScqdmUbtHXIfrgQnWrwTNjf09OJLycbeJ+ka4+CV7I1eEqG8DtZPnQoCyxjoYMjO4soDl6EX9YgqaZp3DlUH4pXrYHYz58YyFkSeJEk=
     * nickName : 天气不错
     * sessionId : 15542598914641
     * sex : 2
     * userName : hIB3Qt1510621690
     * weight : 140
     * whetherBingWeChat : 2
     */
    private int age;
    private String email;
    public boolean islogin;
    private String headPic;
    private int height;
    @Id
    private Long id;
    private String invitationCode;
    private String jiGuangPwd;
    private String nickName;
    private String sessionId;
    private int sex;
    private String userName;
    private int weight;
    private int whetherBingWeChat;

    @Generated(hash = 295282713)
    public LoginBean(int age, String email, boolean islogin, String headPic, int height, Long id, String invitationCode, String jiGuangPwd, String nickName, String sessionId, int sex,
            String userName, int weight, int whetherBingWeChat) {
        this.age = age;
        this.email = email;
        this.islogin = islogin;
        this.headPic = headPic;
        this.height = height;
        this.id = id;
        this.invitationCode = invitationCode;
        this.jiGuangPwd = jiGuangPwd;
        this.nickName = nickName;
        this.sessionId = sessionId;
        this.sex = sex;
        this.userName = userName;
        this.weight = weight;
        this.whetherBingWeChat = whetherBingWeChat;
    }

    @Generated(hash = 1112702939)
    public LoginBean() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getJiGuangPwd() {
        return jiGuangPwd;
    }

    public void setJiGuangPwd(String jiGuangPwd) {
        this.jiGuangPwd = jiGuangPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWhetherBingWeChat() {
        return whetherBingWeChat;
    }

    public void setWhetherBingWeChat(int whetherBingWeChat) {
        this.whetherBingWeChat = whetherBingWeChat;
    }

    public boolean getIslogin() {
        return this.islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }
}
