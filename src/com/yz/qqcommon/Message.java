package com.yz.qqcommon;

import java.io.Serializable;

/**
 * @author 院长
 * @version 1.0.0
 * 消息信息
 */
public class Message implements Serializable {
    private String sender; // 发送者
    private String receive; // 接收者
    private String time; // 发送时间
    private String content; // 发送内容
    private String msgType; // 消息类型

    private String srcFIlePath; // 文件发送路径
    private String descFIlePath; // 文件接收路径

    private byte[] fileBytes; // 文件字节数组
    private static final long serialVersionUID = 1L; // 反序列化id

    public Message() {
    }

    public Message(String sender, String receive, String time, String content, String msgType) {
        this.sender = sender;
        this.receive = receive;
        this.time = time;
        this.content = content;
        this.msgType = msgType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSrcFIlePath() {
        return srcFIlePath;
    }

    public void setSrcFIlePath(String srcFIlePath) {
        this.srcFIlePath = srcFIlePath;
    }

    public String getDescFIlePath() {
        return descFIlePath;
    }

    public void setDescFIlePath(String descFIlePath) {
        this.descFIlePath = descFIlePath;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
