package com.ovs_corp.bstorm.ui.home.adapters;

import java.util.ArrayList;

public class Idea {

    String header, mainText, user;
    ArrayList<String> problems;
    int likes;


    public Idea(String header, String mainText, String user, ArrayList<String> problems, int likes) {
        this.header = header;
        this.mainText = mainText;
        this.user = user;
        this.problems = problems;
        this.likes = likes;
    }
}
