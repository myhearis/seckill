package com.su.jsekill_project.pojo;

import java.io.Serializable;

/**
 * @Classname User
 * @author: 我心
 * @Description:
 * @Date 2023/1/12 23:00
 * @Created by Lenovo
 */
public class User implements Serializable {
    //序列化id
    private static final long serialVersionUID = -6849794470754667732L;
    private int userId;
    private String userName;
    private String password;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
