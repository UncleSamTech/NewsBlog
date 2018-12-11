package com.newsapp.samuel.newsblog.Model;

public class NewsBlogObjectClass {

    private String newsHeader;
    private String newsUrlLink;
    private String newsImgUrl;
    private String newsCount;
    private String newsDate;

    public NewsBlogObjectClass(String newsHeader, String newsUrlLink, String newsImgUrl, String newsCount, String newsDate) {
        this.newsHeader = newsHeader;
        this.newsUrlLink = newsUrlLink;
        this.newsImgUrl = newsImgUrl;
        this.newsCount = newsCount;
        this.newsDate = newsDate;
    }

    public NewsBlogObjectClass(String newsHeader, String newsImgUrl, String newsCount, String newsDate) {
        this.newsHeader = newsHeader;
        this.newsImgUrl = newsImgUrl;
        this.newsCount = newsCount;
        this.newsDate = newsDate;
    }

    public NewsBlogObjectClass(){}

    public String getNewsHeader() {
        return newsHeader;
    }

    public String getNewsUrlLink() {
        return newsUrlLink;
    }

    public String getNewsImgUrl() {
        return newsImgUrl;
    }

    public String getNewsCount() {
        return newsCount;
    }

    public String getNewsDate() {
        return newsDate;
    }
}
