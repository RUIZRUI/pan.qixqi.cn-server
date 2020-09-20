package cn.qixqi.pan.entity;

import java.util.Date;

/**
 * todo
 * 1. 似乎密码不够安全
 */
public class PanUser{
    private int uid;
    private String username;
    private String password;
    private char sex;
    private String email;
    private String phoneNum;
    private String avatar;
    private Date birthday;        // 1999-04-22
    private Date registerTime;
    private Date lastLoginTime;


    /**
     * 陌生人获取的用户信息
     * @param uid
     * @param username
     * @param sex
     * @param avatar
     * @param registerTime
     */
    public PanUser(int uid, String username, char sex, String avatar, Date registerTime){
        this.uid = uid;
        this.username = username;
        this.sex = sex;
        this.avatar = avatar;
        this.registerTime = registerTime;
    }


    /**
     * 注册构造函数
     * @param username
     * @param password
     * @param email
     * @param phoneNum
     */
    public PanUser(String username, String password, String email, String phoneNum){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
    }


    /**
     * 包含用户的全部信息
     * @param uid
     * @param username
     * @param password
     * @param sex
     * @param phoneNum
     * @param avatar
     * @param birthday
     * @param registerTime
     * @param lastLoginTime
     */
    public PanUser(int uid, String username, String password, char sex, String email, String phoneNum, String avatar, Date birthday, Date registerTime, Date lastLoginTime){
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.phoneNum = phoneNum;
        this.avatar = avatar;
        this.birthday = birthday;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
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

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public char getSex(){
        return this.sex;
    }

    public void setSex(char sex){
        this.sex = sex;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
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

    @Override
    public String toString(){
        return "User{" +
            "uid=" + uid +
            ", username='" + username + "'" +
            ", password=" + password + 
            ", sex='" + sex + "'" +
            ", email='" + email + "'" +
            ", phoneNum=" + phoneNum +
            ", avatar=" + avatar +
            ", birthday=" + birthday +
            ", registerTime=" + registerTime +
            ", lastLoginTime=" + lastLoginTime +
            "}";
    }
}