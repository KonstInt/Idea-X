package com.ovs_corp.bstorm.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test_table")
public class TestDB {
    // TODO: 6/8/2020 Normal Database (It will be added at 6/10/2020)
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String question;
    private String solution;
    private String answer;

    public TestDB(String question, String solution, String answer) {
        this.question = question;
        this.solution = solution;
        this.answer = answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getSolution() {
        return solution;
    }

    public String getAnswer() {
        return answer;
    }
}
