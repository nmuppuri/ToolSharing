package com.example.toolsharing.PojoClasses;

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
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("StudentRegis")
    @Expose
    private List<StudentRegisList_Pojo> studentRegis = null;
    @SerializedName("ToolsList")
    @Expose
    private List<ToolsList_Pojo> toolsList = null;
    @SerializedName("SearchToolsList")
    @Expose
    private List<SearchToolsList_Pojo> searchToolsList = null;
    @SerializedName("SchoolId")
    @Expose
    private Integer schoolId;
    @SerializedName("Passwd")
    @Expose
    private String passwd;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Phone")
    @Expose
    private Long phone;
    @SerializedName("MyMessages")
    @Expose
    private List<MyMessages_Pojo> myMessages = null;
    @SerializedName("MessageDetails")
    @Expose
    private List<MessageDetails_Pojo> messageDetails = null;
    @SerializedName("CommentsList")
    @Expose
    private List<CommentsList_Pojo> commentsList = null;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<StudentRegisList_Pojo> getStudentRegis() {
        return studentRegis;
    }

    public void setStudentRegis(List<StudentRegisList_Pojo> studentRegis) {
        this.studentRegis = studentRegis;
    }

    public List<ToolsList_Pojo> getToolsList() {
        return toolsList;
    }

    public void setToolsList(List<ToolsList_Pojo> toolsList) {
        this.toolsList = toolsList;
    }

    public List<SearchToolsList_Pojo> getSearchToolsList() {
        return searchToolsList;
    }

    public void setSearchToolsList(List<SearchToolsList_Pojo> searchToolsList) {
        this.searchToolsList = searchToolsList;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public List<MyMessages_Pojo> getMyMessages() {
        return myMessages;
    }

    public void setMyMessages(List<MyMessages_Pojo> myMessages) {
        this.myMessages = myMessages;
    }

    public List<MessageDetails_Pojo> getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(List<MessageDetails_Pojo> messageDetails) {
        this.messageDetails = messageDetails;
    }

    public List<CommentsList_Pojo> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentsList_Pojo> commentsList) {
        this.commentsList = commentsList;
    }



}
