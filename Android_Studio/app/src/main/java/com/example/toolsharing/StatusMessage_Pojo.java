package com.example.toolsharing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatusMessage_Pojo {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("AdminAccess")
    @Expose
    private String adminaccess;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StudentRegis")
    @Expose
    private List<StudentRegisList_Pojo> studentRegis = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getAdminaccess() {
        return adminaccess;
    }

    public void setAdminaccess(String adminaccess) {
        this.adminaccess = adminaccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StudentRegisList_Pojo> getStudentRegis() {
        return studentRegis;
    }

    public void setStudentRegis(List<StudentRegisList_Pojo> studentRegis) {
        this.studentRegis = studentRegis;
    }

}