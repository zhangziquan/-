package com.example.zhangziquan.personalproject3;

public class User {
    private Integer _id;
    private String username;
    private String password;
    private byte[] avatar;

    public User(Integer _id, String username, String password, byte[] avatar)
    {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    public Integer get_id() {
        return _id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getAvatar() {
        return avatar;
    }
}
