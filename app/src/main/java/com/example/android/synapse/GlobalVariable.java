package com.example.android.synapse;

import android.app.Application;
import android.widget.Toast;

public class GlobalVariable extends Application {

    private String url= "http://d7e21c41.ngrok.io/";
    private String Token;
    //private String userId;

    private String toUserId;
    private String fromUserId;
    private String messgaeBody;

    private String channelName;
    private String channelId;


    public String getSomeVariable() {
        return url;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

/*
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getMessgaeBody() {
        return messgaeBody;
    }

    public void setMessgaeBody(String messgaeBody) {
        this.messgaeBody = messgaeBody;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
