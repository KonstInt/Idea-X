package com.ovs_corp.goal.ui.home.news_create;

public class NewsSet {
    public int id;
    public String title;
    public String fullNews;
    public String newsUrl;
    public String newsDate;
    public boolean show;
    public String userId;
    public String fullName;
    public String documentId;
    public NewsStatus newsStatus;

    public NewsSet(int id, String title, String fullNews, String newsUrl, String newsDate, boolean show, String userId, String fullName, String documentId, NewsStatus newsStatus) {
        this.id = id;
        this.title = title;
        this.fullNews = fullNews;
        this.newsUrl = newsUrl;
        this.newsDate = newsDate;
        this.show = show;
        this.userId = userId;
        this.fullName = fullName;
        this.documentId = documentId;
        this.newsStatus = newsStatus;
    }
}
