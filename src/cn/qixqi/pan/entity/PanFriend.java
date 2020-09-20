package cn.qixqi.pan.entity;

import java.util.Date;

public class PanFriend{
    private int uid;
    private String username;
    private char sex;
    private String phoneNum;
    private String avatar;
    private Date birthday;
    private Date registerTime;
    private Date lastLoginTime;
    private Date relationTime;

    public PanFriend(int uid, String username, char sex, String phoneNum, String avatar, Date birthday, Date registerTime, Date lastLoginTime, Date relationTime){
        this.uid = uid;
        this.username = username;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.avatar = avatar;
        this.birthday = birthday;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.relationTime = relationTime;
    }

    public int getUid(){
        return this.uid;
    }

    public void setUid(int uid){
        this.uid = uid;
    }

    public String getUserName(){
        return this.username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public char getSex(){
        return this.sex;
    }

    public void setSex(char sex){
        this.sex = sex;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getAvatar(){
        return this.avatar;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public Date getBirthday(){
        return this.birthday;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }

    public Date getRegisterTime(){
        return this.registerTime;
    }

    public void setRegisterTime(Date registerTime){
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime(){
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public Date getRelationTime(){
        return this.relationTime;
    }

    public void setRelationTime(Date relationTime){
        this.relationTime = relationTime;
    }

    @Override
    public String toString(){
        return "Friend{" +
            "uid=" + uid +
            ", username='" + username + "'" +
            ", sex='" + sex + "'" +
            ", phoneNum=" + phoneNum +
            ", avatar=" + avatar +
            ", birthday=" + birthday +
            ", registerTime=" + registerTime +
            ", lastLoginTime=" + lastLoginTime +
            ", relationTime=" + relationTime +
            "}";
    }
}