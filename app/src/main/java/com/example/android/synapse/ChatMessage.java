package com.example.android.synapse;

public class ChatMessage
{
    String toid,fromid,message,timeStamp;
    String messageId;

    public ChatMessage()
    {

    }


    public ChatMessage(String messageId,String toid, String fromid, String message, String timeStamp) {
        this.messageId = messageId;
        this.toid = toid;
        this.fromid = fromid;
        this.message = message;
        this.timeStamp = timeStamp;
    }


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


}

/*
package com.example.android.synapse;

public class ChatMessage
{
    String id,message,timeStamp;

    public ChatMessage()
    {

    }

    public ChatMessage(String id, String message, String timeStamp) {
        this.id = id;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


}
*/
