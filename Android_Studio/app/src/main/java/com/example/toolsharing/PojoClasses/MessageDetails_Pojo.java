package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageDetails_Pojo {

    @SerializedName("MDMessageId")
    @Expose
    private Integer mDMessageId;
    @SerializedName("MDFromStudentId")
    @Expose
    private Integer mDFromStudentId;
    @SerializedName("MDMessage")
    @Expose
    private String mDMessage;
    @SerializedName("MDToStudentId")
    @Expose
    private Integer mDToStudentId;

    public Integer getMDMessageId() {
        return mDMessageId;
    }

    public void setMDMessageId(Integer mDMessageId) {
        this.mDMessageId = mDMessageId;
    }

    public Integer getMDFromStudentId() {
        return mDFromStudentId;
    }

    public void setMDFromStudentId(Integer mDFromStudentId) {
        this.mDFromStudentId = mDFromStudentId;
    }

    public String getMDMessage() {
        return mDMessage;
    }

    public void setMDMessage(String mDMessage) {
        this.mDMessage = mDMessage;
    }

    public Integer getMDToStudentId() {
        return mDToStudentId;
    }

    public void setMDToStudentId(Integer mDToStudentId) {
        this.mDToStudentId = mDToStudentId;
    }

}