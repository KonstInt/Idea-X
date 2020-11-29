package com.ovs_corp.bstorm.ui.messages;

public class Messages {
    private String from, message, type, time, name;

    public Messages()
    {

    }

    public Messages(String date, String from, String message, String name, String time, String type) {
        this.from = from;
        this.message = message;
        this.type = type;
        this.time = time;
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
