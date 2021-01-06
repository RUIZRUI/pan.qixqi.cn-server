package cn.qixqi.pan.entity;

import java.util.Date;

public class PanSession {
    private int uid;     // 所属消息，多用户登录区分
    private int sessionId;
    private int uid1;    // 发送人id
    private int uid2;    // 接收人id
    private String username1;   // 发送人名称（连接qquser表得到）
    private String username2;   // 接收人名称（连接qquser表得到）
    private String avatar1;    // 发送人头像（连接qquser表得到）
    private String avatar2;   // 接受人头像（连接qquser表得到）
    private String lastMsg; // 最后一条消息内容
    private String lastMsgUsername;
    private Date lastMsgTime;
    private char lastMsgType;   // (文字'w'/图片'p'/文佳你'f'/音乐'a')
    private char sessionType;      // (群组'r'/好友'f')
    // private int unreadCount;    // 该会话未读消息数


    public PanSession(int uid, int sessionId, int uid1, int uid2, String username1, String username2, String avatar1, String avatar2, 
        String lastMsg, String lastMsgUsername, Date lastMsgTime, char lastMsgType, char sessionType){
        this.uid = uid;
        this.sessionId = sessionId;   // 暂时设定为7位随机数
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.username1 = username1;
        this.username2 = username2;
        this.avatar1 = avatar1;
        this.avatar2 = avatar2;
        this.lastMsg = lastMsg;
        this.lastMsgUsername = lastMsgUsername;
        this.lastMsgTime = lastMsgTime;
        this.lastMsgType = lastMsgType;
        this.sessionType = sessionType;
        // this.unreadCount = unreadCount;
    }


    /**
     * qqsession表
     * @param uid
     * @param uid1
     * @param uid2
     * @param lastMsg
     * @param lastMsgUsername
     * @param lastMsgTime
     * @param lastMsgType
     * @param sessionType
     */
    public PanSession(int uid, int uid1, int uid2, String lastMsg, String lastMsgUsername,
                   Date lastMsgTime, char lastMsgType, char sessionType){
        this.uid = uid;
        // this.sessionId = sessionId;   // 暂时设定为7位随机数
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.lastMsg = lastMsg;
        this.lastMsgUsername = lastMsgUsername;
        this.lastMsgTime = lastMsgTime;
        this.lastMsgType = lastMsgType;
        this.sessionType = sessionType;
        // this.unreadCount = unreadCount;
    }

    public int getUid() {
        return uid;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getUid1() {
        return uid1;
    }

    public int getUid2() {
        return uid2;
    }

    public String getUsername1(){
        return username1;
    }

    public String getUsername2(){
        return username2;
    }

    public String getAvatar1(){
        return avatar1;
    }

    public String getAvatar2(){
        return avatar2;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public String getLastMsgUsername() {
        return lastMsgUsername;
    }

    public Date getLastMsgTime() {
        return lastMsgTime;
    }

    public char getLastMsgType() {
        return lastMsgType;
    }

    public char getSessionType() {
        return sessionType;
    }

    // public int getUnreadCount() {
    //     return unreadCount;
    // }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setUid1(int uid1) {
        this.uid1 = uid1;
    }

    public void setUid2(int uid2) {
        this.uid2 = uid2;
    }

    public void setUsername1(String username1){
        this.username1 = username1;
    }

    public void setUsername2(String username2){
        this.username2 = username2;
    }

    public void setAvatar1(String avatar1){
        this.avatar1 = avatar1;
    }

    public void setAvatar2(String avatar2){
        this.avatar2 = avatar2;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public void setLastMsgUsername(String lastMsgUsername) {
        this.lastMsgUsername = lastMsgUsername;
    }

    public void setLastMsgTime(Date lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public void setLastMsgType(char lastMsgType) {
        this.lastMsgType = lastMsgType;
    }

    public void setSessionType(char sessionType) {
        this.sessionType = sessionType;
    }

    // public void setUnreadCount(int unreadCount) {
    //     this.unreadCount = unreadCount;
    // }

    @Override
    public String toString() {
        return "Sessions{" +
                "uid=" + uid +
                ", sessionId=" + sessionId +
                ", uid1=" + uid1 +
                ", uid2=" + uid2 +
                ", username1=" + username1 +
                ", username2=" + username2 +
                ", avatar1=" + avatar1 +
                ", avatar2=" + avatar2 +
                ", lastMsg='" + lastMsg + '\'' +
                ", lastMsgUsername='" + lastMsgUsername + '\'' +
                ", lastMsgTime='" + lastMsgTime + '\'' +
                ", lastMsgType=" + lastMsgType +
                ", sessionType=" + sessionType +
                // ", unreadCount=" + unreadCount +
                '}';
    }
}
