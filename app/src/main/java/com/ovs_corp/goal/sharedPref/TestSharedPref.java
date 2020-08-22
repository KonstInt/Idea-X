package com.ovs_corp.goal.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.ovs_corp.goal.AppInfo;

public class TestSharedPref {
    SharedPreferences isStart;
    SharedPreferences testPosition;
    SharedPreferences rightAnswers;
    SharedPreferences subject;

    public TestSharedPref(Context context)
    {
        isStart = context.getSharedPreferences("IS_TEST_START",Context.MODE_PRIVATE);
        testPosition = context.getSharedPreferences("CURR_TEST_NUM", Context.MODE_PRIVATE);
        rightAnswers = context.getSharedPreferences("NUM_RIGHT_ANSWERS", Context.MODE_PRIVATE);
        subject = context.getSharedPreferences("TEST_SUBJECT", Context.MODE_PRIVATE);
    }

    public void setSubject(String state) {
        SharedPreferences.Editor editor = subject.edit();
        editor.putString("Subject", state);
        editor.commit();
    }

    public void setTestStart(Boolean state) {
        SharedPreferences.Editor editor = isStart.edit();
        editor.putBoolean("TestStart", state);
        editor.commit();
    }

    public void setTestPosition(int state) {
        SharedPreferences.Editor editor = testPosition.edit();
        editor.putInt("CurrTestPosition", state);
        editor.commit();
    }

    public void setTestAns(int state) {
        SharedPreferences.Editor editor =rightAnswers.edit();
        editor.putInt("RightAns", state);
        editor.commit();
    }




    public String loadTestSubject(){
        String state = subject.getString("Subject", AppInfo.subject);
        return state;
    }
    public Boolean loadTestStart()
    {
        Boolean state = isStart.getBoolean("TestStart", AppInfo.isTestStart);
        return state;
    }

    public Integer loadTestPosition()
    {
        Integer state = testPosition.getInt("CurrTestPosition",AppInfo.testPosition);
        return state;
    }

    public Integer loadRightAns()
    {
        Integer state = rightAnswers.getInt("RightAns",AppInfo.testRightAns);
        return state;
    }



}
