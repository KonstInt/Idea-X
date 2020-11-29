package com.ovs_corp.bstorm.ui.home;


import com.ovs_corp.bstorm.ui.home.idea_create.BaseResponse;
import com.ovs_corp.bstorm.ui.home.idea_create.IdeaRsDto;
import com.ovs_corp.bstorm.ui.home.news_create.NewsSet;
import com.ovs_corp.bstorm.ui.home.survs.SurveySet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderIder {

    @GET("idea/all")
    Call<BaseResponse<ArrayList<IdeaRsDto>>> getIdeas();

    @GET("news/all_short")
    Call<List<NewsSet>> getNews();

    @GET("poll/all_user_short")
    Call<List<SurveySet>> getPoll();





}
