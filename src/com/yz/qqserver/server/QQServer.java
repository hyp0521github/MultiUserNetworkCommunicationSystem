package com.yz.qqserver.server;

import com.yz.qqcommon.Message;
import com.yz.qqcommon.MsgType;
import com.yz.qqcommon.User;
import com.yz.qqserver.thread.ManageServiceConnectClientThread;
import com.yz.qqserver.thread.SenderNewsToClient;
import com.yz.qqserver.thread.ServiceConnectClientThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * @author 院长
 * @version 1.0.0
 * 该类用来启动服务端
 */
@SuppressWarnings({"all"})
public class QQServer {
    private ServerSocket ss;
    public static HashMap<String, User> validUsers = new HashMap<>(); // 有效用户

    static  {
        validUsers.put("1", new User("1", "1"));
        validUsers.put("2", new User("2", "2"));
        validUsers.put("3", new User("3", "3"));
        validUsers.put("4", new User("4", "4"));
        validUsers.put("小院长", new User("小院长", "5"));
        validUsers.put("大院长", new User("大院长", "6"));
    }

    public boolean validateLogin(String userId, String pas) {
        if(!validUsers.containsKey(userId)) {
            System.out.println("没有该用户");
            return false;
        }
        if(!validUsers.get(userId).getPassword().equals(pas)){
            System.out.println("密码输入错误");
            return false;
        }
        return true;
    }

    public QQServer() {
        try {
            ss = new ServerSocket(9999);
            System.out.println("服务端9999开启中...");
            while (true) {
                // 开启服务器推送新闻线程
                new Thread(new SenderNewsToClient()).start();
                Socket socket = ss.accept();
                // 1.接收client端传过来的数据
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();
                // 2.判断账号密码是否正确
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String time = dtf.format(LocalDateTime.now());
                String content = "登陆失败";
                String msgType = MsgType.MESSAGE_LOGIN_FAIL;
                Message msg;
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                if(validateLogin(user.getUserId(), user.getPassword())) {
                    msgType = MsgType.MESSAGE_LOGIN_SUCCESS;
                    content = "登陆成功";
                    msg = new Message(user.getUserId(), "server", time, content, msgType);
                    oos.writeObject(msg);
                    // 2.1登陆成功创建线程
                    ServiceConnectClientThread scct = new ServiceConnectClientThread(socket, user.getUserId());
                    scct.start();
                    // 2.2将socket添加进map数组
                    ManageServiceConnectClientThread.addServiceConnectClientThread(user.getUserId(), scct);
                } else {
                    System.out.println("用户" + user.getUserId() + "密码" + user.getPassword() + "登陆失败");
                    msg = new Message(user.getUserId(), "server", time, content, msgType);
                    oos.writeObject(msg);
                    socket.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 3.close
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
