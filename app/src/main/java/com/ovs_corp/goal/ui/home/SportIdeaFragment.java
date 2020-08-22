package com.ovs_corp.goal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.goal.AppInfo;
import com.ovs_corp.goal.R;
import com.ovs_corp.goal.ui.dashboard.DashboardViewModel;
import com.ovs_corp.goal.ui.home.adapters.IdeasAdapter;
import com.ovs_corp.goal.ui.home.idea_create.IdeaSet;
import com.ovs_corp.goal.ui.home.idea_create.ShortUser;
import com.ovs_corp.goal.ui.home.idea_create.Topic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SportIdeaFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    IdeasAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sport_idea, container, false);

        ArrayList<Topic> topics = new ArrayList<>();
        Topic topic = new Topic();
        topic.id = 4;
        topic.topic = "ttt";

        topics.add(topic);

        ShortUser shortUser = new ShortUser();
        shortUser.userName = "dd";
        shortUser.userId = 111;
        shortUser.firstName = "ff";

        IdeaSet ideaSet = new IdeaSet(222, 15, 10, "19.09.2002", "19.09.2020", true, shortUser, "test", "tested", "f", "Rostov","rrr", 4900, 900, topics);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);
        AppInfo.idea_cards.add(ideaSet);

        final RecyclerView ideasRecycler = root.findViewById(R.id.sport_teams_ideas);
        LinearLayoutManager horizontalLayoutManagerIdeas
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ideasRecycler.setLayoutManager(horizontalLayoutManagerIdeas);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://digitalcontest2020.eu-central-1.elasticbeanstalk.com/digitalcontest/api/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderIder jsonPlaceHolder = retrofit.create(JsonPlaceHolderIder.class);
        final Call<List<IdeaSet>> get_ideas = jsonPlaceHolder.getIdeas();

        get_ideas.enqueue(new Callback<List<IdeaSet>>() {
            @Override
            public void onResponse(Call<List<com.ovs_corp.goal.ui.home.idea_create.IdeaSet>> call, Response<List<IdeaSet>> response) {
                if(!response.isSuccessful())
                    return;

                //AppInfo.idea_cards = (ArrayList<com.ovs_corp.goal.ui.home.idea_create.IdeaSet>) response.body();

                adapter = new IdeasAdapter(getContext(), AppInfo.idea_cards);
                ideasRecycler.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<com.ovs_corp.goal.ui.home.idea_create.IdeaSet>> call, Throwable t){

            }
        });
        adapter = new IdeasAdapter(getContext(), AppInfo.idea_cards);
        ideasRecycler.setAdapter(adapter);
        return root;
    }


}
