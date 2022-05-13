package com.example.whatsappclone.models;

public class MessageModel {
    String uID,text;
    Long time;

    public MessageModel(String uID, String text, Long time) {
        this.uID = uID;
        this.text = text;
        this.time = time;
    }

    public MessageModel(String uID, String text) {
        this.uID = uID;
        this.text = text;
    }

    public MessageModel() {
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
