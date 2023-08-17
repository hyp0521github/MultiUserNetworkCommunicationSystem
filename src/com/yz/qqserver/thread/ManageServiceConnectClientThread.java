package com.yz.qqserver.thread;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 院长
 * @version 1.0.0
 * 该类用来管理服务端线程
 */
@SuppressWarnings({"all"})
public class ManageServiceConnectClientThread {
    private static HashMap<String,ServiceConnectClientThread> hm = new HashMap<>();

    public static void addServiceConnectClientThread(String userId,ServiceConnectClientThread thread) {
        hm.put(userId, thread);
    }
    public static ServiceConnectClientThread getServiceConnectClientThread (String userId) {
        return hm.get(userId);
    }

    public static void removeServiceConnectClientThread(String userId) {
        hm.remove(userId);
    }

    public static HashMap<String, ServiceConnectClientThread> getHm() {
        return hm;
    }

    // 返回客户端在线列表
    public static String getOnlineUsersList(String userId)  {
        Iterator<String> iterator = hm.keySet().iterator();
        StringBuilder stringBuilder = new StringBuilder(1024);
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next() + " ");
        }
        return stringBuilder.toString();
    }
}
