package com.example.android.synapse;

public class Discussion
{
    private String name,message,timeStamp;
    public Discussion()
    {

    }

    public Discussion(String name, String msg, String timeStamp)
    {
        this.name=name;
        message=msg;
        this.timeStamp=timeStamp;
    }

    public String getName()
    {
        return name;
    }

    public void setUserName(String userName)
    {
        this.name = userName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getTimeStamp()
    {
        return timeStamp;
    }
}
