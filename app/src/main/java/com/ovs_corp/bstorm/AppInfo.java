package com.ovs_corp.bstorm;

import com.ovs_corp.bstorm.ui.home.idea_create.IdeaRsDto;
import com.ovs_corp.bstorm.ui.home.news_create.NewsSet;
import com.ovs_corp.bstorm.ui.home.survs.SurveySet;

import java.util.ArrayList;

//#0758DA #0950C3
public class AppInfo {
    public static int currentTask = 0;
    public static int testRightAns = 0;
    public static int  testPosition = 0;
    public static boolean isTestStart = false;
    public static boolean nightModeState = false;

    public static String department = "math";

    public static ArrayList<IdeaRsDto> idea_cards = new ArrayList<>();

    public static ArrayList<NewsSet> news_cards = new ArrayList<>();

    public static ArrayList<SurveySet> survey_cards = new ArrayList<>();

    public static String testID = "33218768";
    public static String subject = "math";
    public static String libLink = "";
    public static String playListLink = "PLMZnOi2R__xNse4X35ICOCJ2CdXj6MFyr";
    //public static ArrayList<TaskBlock> test = new ArrayList<>();

    //public static ArrayList<SubjectInfo> subjectInfoArrayList = new ArrayList<>();
    //public static List<DataModule> dataModuleList;



}
