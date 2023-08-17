package com.yz.qqserver.thread;

import com.yz.qqcommon.Message;
import com.yz.qqcommon.MsgType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 院长
 * @version 1.0.0
 */
public class SenderNewsToClient implements Runnable {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息[输入exit表示退出推送]");
            String key = scanner.next();
            if (key.equals("exit")) {
                System.out.println("退出了推送新闻线程");
                break;
            }
            // 1.创建msg对象
            Message msg = new Message();
            msg.setSender("服务器");
            msg.setTime(new Date().toString());
            msg.setMsgType(MsgType.MESSAGE_TO_ALL_USERS);
            msg.setContent(key);
            // 2.遍历线程集合
            HashMap<String, ServiceConnectClientThread> hm = ManageServiceConnectClientThread.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String userId = iterator.next().toString();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(hm.get(userId).getSocket().getOutputStream());
                    oos.writeObject(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
