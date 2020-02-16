package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchToolsList_Pojo {

    @SerializedName("PostedStudentId")
    @Expose
    private Integer postedStudentId;
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
    private Integer toolRating;
    @SerializedName("ToolAvailableTillInDays")
    @Expose
    private Integer toolAvailableTillInDays;
    @SerializedName("ToolAvailableFromInDays")
    @Expose
    private Integer toolAvailableFromInDays;

    public Integer getPostedStudentId() {
        return postedStudentId;
    }

    public void setPostedStudentId(Integer postedStudentId) {
        this.postedStudentId = postedStudentId;
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

    public Integer getToolRating() {
        return toolRating;
    }

    public void setToolRating(Integer toolRating) {
        this.toolRating = toolRating;
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

}