package com.ovs_corp.goal.ui.home.survs;

public class SurveySet {
    public int id;
    public String title;
    public String description;
    public String vote1Text;
    public String vote2Text;
    public int vote1Count;
    public int vote2Count;
    public String dateProposed;
    public String dataEnd;
    public boolean active;
    public String userId;
    public String fullName;

    public SurveySet(int id, String title, String description, String vote1Text, String vote2Text, int vote1Count, int vote2Count, String dateProposed, String dataEnd, boolean active, String userId, String fullName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.vote1Text = vote1Text;
        this.vote2Text = vote2Text;
        this.vote1Count = vote1Count;
        this.vote2Count = vote2Count;
        this.dateProposed = dateProposed;
        this.dataEnd = dataEnd;
        this.active = active;
        this.userId = userId;
        this.fullName = fullName;
    }
}
