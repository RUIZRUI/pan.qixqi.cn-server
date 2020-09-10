package cn.qixqi.pan.entity;

/**
 * todo
 * 1. 似乎密码不够安全
 */
public class User{
    private int userId;
    private String username;
    private String password;
    private char sex;
    private String phone_num;
    private String icon;
    private String birthday;        // 1999-04-22
    private String register_time;
    private String last_login_time;


    /**
     * 陌生人获取的用户信息
     * @param userId
     * @param username
     * @param icon
     * @param register_time
     */
    public User(int userId, String username, String icon, String register_time){
        this.userId = userId;
        this.username = username;
        this.icon = icon;
        this.register_time = register_time;
    }


    /**
     * 不包含注册后扩展信息: icon, birthday
     * 注册时使用
     * @param userId
     * @param username
     * @param password
     * @param sex
     * @param phone_num
     * @param register_time
     * @param last_login_time
     */
    public User(int userId, String username, String password, char sex, String phone_num, String register_time, String last_login_time){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.phone_num = phone_num;
        this.register_time = register_time;
        this.last_login_time = last_login_time;
    }


    /**
     * 包含用户的全部信息
     * @param userId
     * @param username
     * @param password
     * @param sex
     * @param phone_num
     * @param icon
     * @param birthday
     * @param register_time
     * @param last_login_time
     */
    public User(int userId, String username, String password, char sex, String phone_num, String icon, String birthday, String register_time, String last_login_time){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.phone_num = phone_num;
        this.icon = icon;
        this.birthday = birthday;
        this.register_time = register_time;
        this.last_login_time = last_login_time;
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

    @Override
    public String toString(){
        return "User{" +
            "userId=" + userId +
            ", username='" + username + "'" +
            ", password=" + password + 
            ", sex='" + sex + "'" +
            ", phone_num=" + phone_num +
            ", icon=" + icon +
            ", birthday=" + birthday +
            ", register_time=" + register_time +
            ", last_login_time=" + last_login_time +
            "}";
    }
}