package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToolsList_Pojo {

    @SerializedName("ToolId")
    @Expose
    private Integer toolId;
    @SerializedName("ToolName")
    @Expose
    private String toolName;
    @SerializedName("ToolDesc")
    @Expose
    private String toolDesc;
    @SerializedName("ToolImg")
    @Expose
    private String toolImg;
    @SerializedName("ReturnDate")
    @Expose
    private String returnDate;
    @SerializedName("ToolAvailability")
    @Expose
    private Integer toolAvailability;
    @SerializedName("ToolReturned")
    @Expose
    private Integer toolReturned;
    @SerializedName("ToolRating")
    @Expose
    private float toolRating;
    @SerializedName("ToolPenalty")
    @Expose
    private float toolPenalty;
    @SerializedName("PostedStudentId")
    @Expose
    private Integer postedStudentId;
    @SerializedName("BorrowStudentId")
    @Expose
    private Integer borrowStudentId;
    @SerializedName("ToolOrderId")
    @Expose
    private Integer toolOrderId;

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getToolAvailability() {
        return toolAvailability;
    }

    public void setToolAvailability(Integer toolAvailability) {
        this.toolAvailability = toolAvailability;
    }

    public Integer getToolReturned() {
        return toolReturned;
    }

    public void setToolReturned(Integer toolReturned) {
        this.toolReturned = toolReturned;
    }

    public float getToolRating() {
        return toolRating;
    }

    public void setToolRating(float toolRating) {
        this.toolRating = toolRating;
    }


    public float getToolPenalty() {
        return toolPenalty;
    }

    public void setToolPenalty(float toolPenalty) {
        this.toolPenalty = toolPenalty;
    }

    public Integer getPostedStudentId() {
        return postedStudentId;
    }

    public void setPostedStudentId(Integer postedStudentId) {
        this.postedStudentId = postedStudentId;
    }

    public Integer getBorrowStudentId() {
        return borrowStudentId;
    }

    public void setBorrowStudentId(Integer borrowStudentId) {
        this.borrowStudentId = borrowStudentId;
    }

    public Integer getToolOrderId() {
        return toolOrderId;
    }

    public void setToolOrderId(Integer toolOrderId) {
        this.toolOrderId = toolOrderId;
    }

}