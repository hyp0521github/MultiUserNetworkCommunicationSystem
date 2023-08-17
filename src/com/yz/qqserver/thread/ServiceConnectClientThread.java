package com.yz.qqserver.thread;

import com.yz.qqcommon.Message;
import com.yz.qqcommon.MsgType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 院长
 * @version 1.0.0
 * 该类用来创建服务端连接客户端线程
 */
@SuppressWarnings({"all"})
public class ServiceConnectClientThread extends Thread {
    private Socket socket;
    private String userId; // 客户端id

    public ServiceConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("接收客户端" + userId + "消息中...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();
                if (msg.getMsgType().equals(MsgType.MESSAGE_GET_ONLINE_USERS)) {
                    System.out.println("客户端" + userId + "查询了在线用户信息");
                    String onlineUsersList = ManageServiceConnectClientThread.getOnlineUsersList(userId);
                    Message retMsg = new Message();
                    retMsg.setReceive(userId);
                    retMsg.setMsgType(MsgType.MESSAGE_RET_ONLINE_USERS);
                    retMsg.setContent(onlineUsersList);
                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(retMsg);
                } else if (msg.getMsgType().equals(MsgType.MESSAGE_POST_EXIT)) {
                    String id = msg.getSender();
                    ServiceConnectClientThread scct = ManageServiceConnectClientThread.getServiceConnectClientThread(id);
                    scct.socket.close();
                    ManageServiceConnectClientThread.removeServiceConnectClientThread(id);
                    System.out.println("客户端" + userId + "已关闭");
                    break;
                } else if (msg.getMsgType().equals(MsgType.MESSAGE_COM_MSG)) {
                    Message reveiveMsg = new Message();
                    String content = "";
                    ObjectOutputStream oos = new ObjectOutputStream(ManageServiceConnectClientThread.getServiceConnectClientThread(msg.getReceive()).getSocket().getOutputStream());
                    if (ManageServiceConnectClientThread.getServiceConnectClientThread(msg.getReceive()) == null) {
                        content = "该用户没有在线";
                    } else {
                        content = msg.getContent();
                    }
                    reveiveMsg.setReceive(msg.getReceive());
                    reveiveMsg.setSender(msg.getSender());
                    reveiveMsg.setContent(msg.getContent());
                    oos.writeObject(msg);
                } else if (msg.getMsgType().equals(MsgType.MESSAGE_TO_ALL_USERS)) {
                    // 1.遍历集合
                    HashMap<String, ServiceConnectClientThread> hm = ManageServiceConnectClientThread.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next().toString();
                        if(!key.equals(msg.getSender())) {
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(key).getSocket().getOutputStream());
                            // 2.创建msg对象
                            Message sendToAllMsg = new Message();
                            sendToAllMsg.setContent(msg.getContent());
                            sendToAllMsg.setMsgType(msg.getMsgType());
                            sendToAllMsg.setTime(msg.getTime());
                            sendToAllMsg.setSender(msg.getSender());
                            oos.writeObject(sendToAllMsg);
                        }
                    }
                } else if (msg.getMsgType().equals(MsgType.MESSAGE_SENDER_FILE)) {
                    System.out.println(msg.getSender()+ "给" + msg.getReceive() + "发送文件: " + msg.getSrcFIlePath() + "到我的的电脑目录" + msg.getDescFIlePath());
                    FileOutputStream fos = new FileOutputStream(msg.getDescFIlePath());
                    fos.write(msg.getFileBytes());
                    fos.close();
                    System.out.println("保存文件OK");
                } else {
                    System.out.println("其他");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
