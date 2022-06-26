package com.example.instaclone.Model;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {

    String captiion;
    String comments;
    String date;
    List<String> likes;
    String post_id;
    String post_url;
    String tags;
    String user_id;

    public Post() {
    }

    public Post(List<String> likes) {
        this.likes = likes;
    }

    public Post(String captiion, String comments, String date, List<String> likes, String post_id, String post_url, String tags, String user_id) {
        this.captiion = captiion;
        this.comments = comments;
        this.date = date;
        this.likes = likes;
        this.post_id = post_id;
        this.post_url = post_url;
        this.tags = tags;
        this.user_id = user_id;
    }

    public String getCaptiion() {
        return captiion;
    }

    public void setCaptiion(String captiion) {
        this.captiion = captiion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
