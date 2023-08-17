package com.yz.qqcommon;

import java.io.Serializable;

/**
 * @author 院长
 * @version 1.0.0
 * 用户信息
 */
public class User implements Serializable {
    private String userId; // 用户id
    private String password; // 用户密码

    private static final long serialVersionUID = 1L; // 反序列化id

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
