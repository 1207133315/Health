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
    private String pwd;
    @Generated(hash = 826566765)
    public LoginBean(int age, String email, boolean islogin, String headPic, int height, Long id, String invitationCode, String jiGuangPwd, String nickName, String sessionId, int sex,
            String userName, int weight, int whetherBingWeChat, String pwd) {
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
        this.pwd = pwd;
    }
    @Generated(hash = 1112702939)
    public LoginBean() {
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean getIslogin() {
        return this.islogin;
    }
    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getInvitationCode() {
        return this.invitationCode;
    }
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
    public String getJiGuangPwd() {
        return this.jiGuangPwd;
    }
    public void setJiGuangPwd(String jiGuangPwd) {
        this.jiGuangPwd = jiGuangPwd;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWhetherBingWeChat() {
        return this.whetherBingWeChat;
    }
    public void setWhetherBingWeChat(int whetherBingWeChat) {
        this.whetherBingWeChat = whetherBingWeChat;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
