package com.ovs_corp.goal.ui.home;


import com.ovs_corp.goal.ui.home.idea_create.IdeaSet;
import com.ovs_corp.goal.ui.home.news_create.NewsSet;
import com.ovs_corp.goal.ui.home.survs.SurveySet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderIder {

    @GET("document/all_notnull_user_short")
    Call<List<IdeaSet>> getIdeas();

    @GET("news/all_short")
    Call<List<NewsSet>> getNews();

    @GET("poll/all_user_short")
    Call<List<SurveySet>> getPoll();





}
