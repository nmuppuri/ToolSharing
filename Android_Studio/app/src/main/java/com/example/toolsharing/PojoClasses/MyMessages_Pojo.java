package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyMessages_Pojo {

    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("FromStudentId")
    @Expose
    private Integer fromStudentId;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("SentDate")
    @Expose
    private String sentDate;
    @SerializedName("ToStudentId")
    @Expose
    private Integer toStudentId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getFromStudentId() {
        return fromStudentId;
    }

    public void setFromStudentId(Integer fromStudentId) {
        this.fromStudentId = fromStudentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public Integer getToStudentId() {
        return toStudentId;
    }

    public void setToStudentId(Integer toStudentId) {
        this.toStudentId = toStudentId;
    }
}