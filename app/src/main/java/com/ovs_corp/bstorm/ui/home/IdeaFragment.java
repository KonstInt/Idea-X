package com.ovs_corp.bstorm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.bstorm.AppInfo;
import com.ovs_corp.bstorm.R;
import com.ovs_corp.bstorm.ui.dashboard.DashboardViewModel;
import com.ovs_corp.bstorm.ui.home.adapters.IdeasAdapter;
import com.ovs_corp.bstorm.ui.home.idea_create.BaseResponse;
import com.ovs_corp.bstorm.ui.home.idea_create.IdeaRsDto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IdeaFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    IdeasAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_idea, container, false);

        /*ArrayList<Topic> topics = new ArrayList<>();
        Topic topic = new Topic();
        topic.id = 4;
        topic.topic = "ttt";

        topics.add(topic);

        ShortUser shortUser = new ShortUser();
        shortUser.userName = "dd";
        shortUser.userId = 111;
        shortUser.firstName = "ff";

        IdeaSet ideaSet = new IdeaSet(222, 15, 10, "19.09.2002", "19.09.2020", true, shortUser, "test", "tested", "f", "Rostov","rrr", 4900, 900, topics);
        AppInfo.idea_cards.clear();

        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);*/

        final RecyclerView ideasRecycler = root.findViewById(R.id.teams_ideas);
        LinearLayoutManager horizontalLayoutManagerIdeas
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ideasRecycler.setLayoutManager(horizontalLayoutManagerIdeas);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://84.201.178.142:8080/ideas/api/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderIder jsonPlaceHolder = retrofit.create(JsonPlaceHolderIder.class);
        final Call<BaseResponse<ArrayList<IdeaRsDto>>> get_ideas = jsonPlaceHolder.getIdeas();

        get_ideas.enqueue(new Callback<BaseResponse<ArrayList<IdeaRsDto>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<IdeaRsDto>>> call,  Response<BaseResponse<ArrayList<IdeaRsDto>>> response) {
                if(!response.isSuccessful())
                    return;


                //InputSpec is = (InputSpec) response.body();
                BaseResponse<ArrayList<IdeaRsDto>> h= (BaseResponse<ArrayList<IdeaRsDto>>) response.body();
                AppInfo.idea_cards = h.getPayload();

                adapter = new IdeasAdapter(getContext(),  AppInfo.idea_cards);
                ideasRecycler.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<IdeaRsDto>>> call, Throwable t){
                int i = 10;
               // i = i/0;
            }
        });
        adapter = new IdeasAdapter(getContext(), AppInfo.idea_cards);
        ideasRecycler.setAdapter(adapter);
        return root;
    }


}
