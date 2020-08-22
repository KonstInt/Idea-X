package com.ovs_corp.goal.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.ovs_corp.goal.AppInfo;

public class TasksInfo {
    SharedPreferences testLink;
    SharedPreferences playlistLink;
    SharedPreferences libLink;
    SharedPreferences taskNum;

    public TasksInfo(Context context)
    {
        testLink = context.getSharedPreferences("TEST_LINK",Context.MODE_PRIVATE);
        playlistLink = context.getSharedPreferences("VIDEO_URL", Context.MODE_PRIVATE);
        libLink = context.getSharedPreferences("LIB_LINK", Context.MODE_PRIVATE);
        taskNum = context.getSharedPreferences("TASK_NUM", Context.MODE_PRIVATE);
    }



    public void setTaskNum(int state) {
        SharedPreferences.Editor editor = taskNum.edit();
        editor.putInt("TaskNum", state);
        editor.commit();
    }

    public void setTestLink(String state) {
        SharedPreferences.Editor editor = testLink.edit();
        editor.putString("TestLink", state);
        editor.commit();
    }

    public void setPlaylistLinkL(String state) {
        SharedPreferences.Editor editor = playlistLink.edit();
        editor.putString("VideoURL", state);
        editor.commit();
    }

    public void setLibLink(String state) {
        SharedPreferences.Editor editor = libLink.edit();
        editor.putString("LibLink", state);
        editor.commit();
    }




    public int loadTaskNum()
    {
        int num = taskNum.getInt("TaskNum", 1);
        return num;
    }


    public String loadTestLink()
    {
        String link = testLink.getString("TestLink", AppInfo.testID);
        return link;
    }

    public String loadPlaylistLink()
    {
        String link = playlistLink.getString("VideoURL", AppInfo.playListLink);
        return link;
    }

    public String loadLibLink()
    {
        String link = libLink.getString("LibLink", AppInfo.libLink);
        return link;
    }



}
