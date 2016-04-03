package org.example.androidsdk.letsgo.model;


public class Review {
    private String userId,username,comment,picUrl;
    public Review(){}
    public void setUserId(String userId){this.userId = userId;}
    public String getUserId(){return userId;}
    public void setUsername(String username){this.username = username;}
    public String getUsername(){return username;}
    public void setComment(String comment){this.comment = comment;}
    public String getComment(){return comment;}
    public void setPicUrl(String picUrl){this.picUrl = picUrl;}
    public String getPicUrl(){return picUrl;}
}
