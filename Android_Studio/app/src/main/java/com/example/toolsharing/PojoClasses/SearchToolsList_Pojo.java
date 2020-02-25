package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchToolsList_Pojo {

    @SerializedName("PostedStudentId")
    @Expose
    private Integer postedStudentId;
    @SerializedName("LoggedStudentId")
    @Expose
    private Integer loggedStudentId;
    @SerializedName("PostedToolId")
    @Expose
    private Integer postedToolId;
    @SerializedName("ToolName")
    @Expose
    private String toolName;
    @SerializedName("ToolDesc")
    @Expose
    private String toolDesc;
    @SerializedName("ToolImg")
    @Expose
    private String toolImg;
    @SerializedName("FromDate")
    @Expose
    private Object fromDate;
    @SerializedName("ReturnDate")
    @Expose
    private Object returnDate;
    @SerializedName("ToolRating")
    @Expose
    private float toolRating;
    @SerializedName("StudentToolRating")
    @Expose
    private float studentToolRating;
    @SerializedName("StudentToolComment")
    @Expose
    private String studentToolComment;
    @SerializedName("ToolAvailableTillInDays")
    @Expose
    private Integer toolAvailableTillInDays;
    @SerializedName("ToolAvailableFromInDays")
    @Expose
    private Integer toolAvailableFromInDays;
    @SerializedName("ToolAvailability")
    @Expose
    private Integer toolAvailability;
    @SerializedName("ToolFavorite")
    @Expose
    private Integer toolFavorite;

    public Integer getPostedStudentId() {
        return postedStudentId;
    }

    public void setPostedStudentId(Integer postedStudentId) {
        this.postedStudentId = postedStudentId;
    }

    public Integer getLoggedStudentId() {
        return loggedStudentId;
    }

    public void setLoggedStudentId(Integer loggedStudentId) {
        this.loggedStudentId = loggedStudentId;
    }

    public Integer getPostedToolId() {
        return postedToolId;
    }

    public void setPostedToolId(Integer postedToolId) {
        this.postedToolId = postedToolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolDesc() {
        return toolDesc;
    }

    public void setToolDesc(String toolDesc) {
        this.toolDesc = toolDesc;
    }

    public String getToolImg() {
        return toolImg;
    }

    public void setToolImg(String toolImg) {
        this.toolImg = toolImg;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Object returnDate) {
        this.returnDate = returnDate;
    }

    public float getToolRating() {
        return toolRating;
    }

    public void setToolRating(float toolRating) {
        this.toolRating = toolRating;
    }

    public float getStudentToolRating() {
        return studentToolRating;
    }

    public void setStudentToolRating(float studentToolRating) {
        this.studentToolRating = studentToolRating;
    }

    public String getStudentToolComment() {
        return studentToolComment;
    }

    public void setStudentToolComment(String studentToolComment) {
        this.studentToolComment = studentToolComment;
    }

    public Integer getToolAvailableTillInDays() {
        return toolAvailableTillInDays;
    }

    public void setToolAvailableTillInDays(Integer toolAvailableTillInDays) {
        this.toolAvailableTillInDays = toolAvailableTillInDays;
    }

    public Integer getToolAvailableFromInDays() {
        return toolAvailableFromInDays;
    }

    public void setToolAvailableFromInDays(Integer toolAvailableFromInDays) {
        this.toolAvailableFromInDays = toolAvailableFromInDays;
    }

    public Integer getToolAvailability() {
        return toolAvailability;
    }

    public void setToolAvailability(Integer toolAvailability) {
        this.toolAvailability = toolAvailability;
    }

    public Integer getToolFavorite() {
        return toolFavorite;
    }

    public void setToolFavorite(Integer toolFavorite) {
        this.toolFavorite = toolFavorite;
    }


}