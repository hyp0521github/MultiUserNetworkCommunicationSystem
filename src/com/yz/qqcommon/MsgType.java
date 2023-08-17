package com.yz.qqcommon;

/**
 * @author 院长
 * @version 1.0.0
 * 消息类型
 */
public interface MsgType {
    String MESSAGE_LOGIN_SUCCESS = "1"; // 表示登陆成功 接口里的变量默认public final static
    String MESSAGE_LOGIN_FAIL = "2"; // 表示登陆失败
    String MESSAGE_COM_MSG = "3"; // 表示普通信息包
    String MESSAGE_GET_ONLINE_USERS = "4"; // 表示获取在线用户列表
    String MESSAGE_RET_ONLINE_USERS = "5"; // 表示返回在线用户列表
    String MESSAGE_POST_EXIT = "6"; // 表示请求退出
    String MESSAGE_TO_ALL_USERS = "7"; // 表示群发消息
    String MESSAGE_SENDER_FILE = "8"; // 表示发送文件
}
