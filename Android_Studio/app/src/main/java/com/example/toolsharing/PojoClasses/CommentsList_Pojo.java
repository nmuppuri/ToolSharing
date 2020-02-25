package com.example.toolsharing.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsList_Pojo {

    @SerializedName("CommentedStudentId")
    @Expose
    private Integer commentedStudentId;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("GivenRating")
    @Expose
    private Integer givenRating;

    public Integer getCommentedStudentId() {
        return commentedStudentId;
    }

    public void setCommentedStudentId(Integer commentedStudentId) {
        this.commentedStudentId = commentedStudentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getGivenRating() {
        return givenRating;
    }

    public void setGivenRating(Integer givenRating) {
        this.givenRating = givenRating;
    }

}