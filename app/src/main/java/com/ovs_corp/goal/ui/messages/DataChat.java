package com.ovs_corp.goal.ui.messages;

public class DataChat {
    public String name, status, image;

    public DataChat()
    {

    }

    public DataChat(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

