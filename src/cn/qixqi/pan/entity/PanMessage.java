package cn.qixqi.pan.entity;

import java.util.Date;

public class PanMessage{
    private int msgId;      // 消息唯一id，服务器或客户端生成，7位随机数
    private int uid;     // 所属者，多用户登录
    private int uid1;    // 发送人id
    private String username1;   // 发送人名称
    private String avatar1;   // 发送人头像
    private int receiverId;       // 接收者id
    private char sessionType;  // 会话类型(群组'r'/好友'f')
    private char msgType;   // 消息类型(文字'w'/图片'p'/文佳你'f'/音乐'a')
    private String msg;     // 消息内容
    private Date msgTime;
    private char msgStatus;    // 发送状态(发送中'i', 发送完成'h', 发送失败'e')

    public PanMessage(){
        super();
    }

    public PanMessage( int uid, int uid1, String username1, String avatar1, int receiverId, char sessionType, char msgType, String msg, Date msgTime, char msgStatus) {
        this.uid = uid;
        this.uid1 = uid1;
        this.username1 = username1;
        this.avatar1 = avatar1;
        this.receiverId = receiverId;
        this.sessionType = sessionType;
        this.msgType = msgType;
        this.msg = msg;
        this.msgTime = msgTime;
        this.msgStatus = msgStatus;
    }

    public PanMessage(int msgId, int uid, int uid1, String username1, String avatar1, int receiverId, char sessionType, char msgType, String msg, Date msgTime, char msgStatus) {
        this.msgId = msgId;
        this.uid = uid;
        this.uid1 = uid1;
        this.username1 = username1;
        this.avatar1 = avatar1;
        this.receiverId = receiverId;
        this.sessionType = sessionType;
        this.msgType = msgType;
        this.msg = msg;
        this.msgTime = msgTime;
        this.msgStatus = msgStatus;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid1() {
        return uid1;
    }

    public void setUid1(int uid1) {
        this.uid1 = uid1;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getAvatar1() {
        return avatar1;
    }

    public void setAvatar1(String avatar1) {
        this.avatar1 = avatar1;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public char getSessionType() {
        return sessionType;
    }

    public void setSessionType(char sessionType) {
        this.sessionType = sessionType;
    }

    public char getMsgType() {
        return msgType;
    }

    public void setMsgType(char msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public char getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(char msgStatus) {
        this.msgStatus = msgStatus;
    }

    @Override
    public String toString() {
        return "Message [sessionType=" + sessionType + ", msg=" + msg + ", msgId=" + msgId + ", msgType=" + msgType
                + ", msgStatus=" + msgStatus + ", msgTime=" + msgTime + ", receiverId=" + receiverId + ", avatar1="
                + avatar1 + ", uid=" + uid + ", uid1=" + uid1 + ", username1=" + username1 + "]";
    }

}
