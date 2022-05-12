package com.example.whatsappclone.models;

public class User {
    String profile_picture,name,email,password,userid,last_msg;

    public User() {
    }

    public User(String profile_picture, String name, String email, String password, String userid, String last_msg) {
        this.profile_picture = profile_picture;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.last_msg = last_msg;
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid(String key) {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }
}
