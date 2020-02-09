package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentRegisList_Pojo {

    @SerializedName("StudentId")
    @Expose
    private Integer studentId;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("StudentEmail")
    @Expose
    private String studentEmail;
    @SerializedName("StudentPwd")
    @Expose
    private String studentPwd;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPwd() {
        return studentPwd;
    }

    public void setStudentPwd(String studentPwd) {
        this.studentPwd = studentPwd;
    }

}