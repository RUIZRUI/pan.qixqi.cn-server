package cn.qixqi.pan.entity;

public class Friend{
    private int userId;
    private String username;
    private char sex;
    private String phone_num;
    private String icon;
    private String birthday;        // 1999-04-22
    private String register_time;
    private String last_login_time;
    private String relation_time;

    public Friend(int userId, String username, char sex, String phone_num, String icon, String birthday, String register_time, String last_login_time, String relation_time){
        this.userId = userId;
        this.username = username;
        this.sex = sex;
        this.phone_num = phone_num;
        this.icon = icon;
        this.birthday = birthday;
        this.register_time = register_time;
        this.last_login_time = last_login_time;
        this.relation_time = relation_time;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
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
        return this.phone_num;
    }

    public void setPhoneNum(String phone_num){
        this.phone_num = phone_num;
    }

    public String getIcon(){
        return this.icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getBirthday(){
        return this.birthday;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public String getRegisterTime(){
        return this.register_time;
    }

    public void setRegisterTime(String register_time){
        this.register_time = register_time;
    }

    public String getLastLoginTime(){
        return this.last_login_time;
    }

    public void setLastLoginTime(String last_login_time){
        this.last_login_time = last_login_time;
    }

    public String getRelationTime(){
        return this.relation_time;
    }

    public void setRelationTime(String relation_time){
        this.relation_time = relation_time;
    }

    @Override
    public String toString(){
        return "Friend{" +
            "userId=" + userId +
            ", username='" + username + "'" +
            ", sex='" + sex + "'" +
            ", phone_num=" + phone_num +
            ", icon=" + icon +
            ", birthday=" + birthday +
            ", register_time=" + register_time +
            ", last_login_time=" + last_login_time +
            ", relation_time=" + relation_time +
            "}";
    }
}