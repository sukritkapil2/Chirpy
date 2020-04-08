package com.dream.chirpy;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class Message {
    String userName;
    String message;
    Timestamp timeSent;

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    public Message() {

    }

    public Message(String userName, String message, Timestamp timeSent) {
        this.userName = userName;
        this.message = message;
        this.timeSent = timeSent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
