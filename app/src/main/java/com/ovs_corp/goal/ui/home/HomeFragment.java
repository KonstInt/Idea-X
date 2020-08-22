package com.ovs_corp.goal.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ovs_corp.goal.AppInfo;
import com.ovs_corp.goal.R;
import com.ovs_corp.goal.ui.home.adapters.IdeasAdapter;
import com.ovs_corp.goal.ui.home.adapters.NewsAdapter;
import com.ovs_corp.goal.ui.home.adapters.StoriesAdapter;
import com.ovs_corp.goal.ui.home.adapters.SurvsAdapter;
import com.ovs_corp.goal.ui.home.news_create.NewsSet;
import com.ovs_corp.goal.ui.home.survs.SurveySet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {



    private TextView howMachIdeas, goto_vertical;
    private TextView userName;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    StoriesAdapter adapter;
    IdeasAdapter adapter1;
    NewsAdapter adapter3;
    SurvsAdapter adapter4;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();



        userName = root.findViewById(R.id.header_username);
        howMachIdeas = root.findViewById(R.id.header_ideas_num);
        userProfileImage = root.findViewById(R.id.header_ava);

        /*goto_vertical = root.findViewById(R.id.goto_vertical);
        goto_vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent g = new Intent(getActivity(), VerticalScrollideasActivity.class);
                startActivity(g);

            }
        });*/

        LoadUserData();

        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);
        viewColors.add(Color.WHITE);

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Общайся");
        animalNames.add("Делись");
        animalNames.add("Оценивай");
        animalNames.add("Комментируй");
        animalNames.add("И все");



        // set up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.heaer_stories);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new StoriesAdapter(getContext(), viewColors, animalNames);
        recyclerView.setAdapter(adapter);




        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://digitalcontest2020.eu-central-1.elasticbeanstalk.com/digitalcontest/api/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderIder jsonPlaceHolder = retrofit.create(JsonPlaceHolderIder.class);

        final RecyclerView ideasRecycler3 = root.findViewById(R.id.heaer_news);
        LinearLayoutManager verticalLayoutManagerNews
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ideasRecycler3.setLayoutManager(verticalLayoutManagerNews);


        final Call<List<NewsSet>> get_news = jsonPlaceHolder.getNews();

        get_news.enqueue(new Callback<List<NewsSet>>() {
            @Override
            public void onResponse(Call<List<NewsSet>> call, Response<List<NewsSet>> response) {
                if(!response.isSuccessful())
                    return;

                AppInfo.news_cards = (ArrayList<NewsSet>) response.body();

                adapter3 = new NewsAdapter(getContext(), AppInfo.news_cards);
                ideasRecycler3.setAdapter(adapter3);
                ideasRecycler3.setLayoutFrozen(true);

            }

            @Override
            public void onFailure(Call<List<NewsSet>> call, Throwable t){

            }
        });



        final RecyclerView ideasRecycler4 = root.findViewById(R.id.sv);
        LinearLayoutManager verticalLayoutManagerPolls
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ideasRecycler4.setLayoutManager(verticalLayoutManagerPolls);



        final Call<List<SurveySet>> get_polls = jsonPlaceHolder.getPoll();

        get_polls.enqueue(new Callback<List<SurveySet>>() {
            @Override
            public void onResponse(Call<List<SurveySet>> call, Response<List<SurveySet>> response) {
                if(!response.isSuccessful())
                    return;

                AppInfo.survey_cards = (ArrayList<SurveySet>) response.body();


                adapter4 = new SurvsAdapter(getContext(), AppInfo.survey_cards);
                ideasRecycler4.setAdapter(adapter4);
                ideasRecycler4.setLayoutFrozen(true);
            }

            @Override
            public void onFailure(Call<List<SurveySet>> call, Throwable t){

            }
        });

        final ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) root.findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               String s = tab.getText().toString();
               //TODO





            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new SportIdeaFragment(), "ФК Сочи");
        adapter.addFragment(new SportIdeaFragment(), "Спартак");
        adapter.addFragment(new SportIdeaFragment(), "ЦСКА");
        adapter.addFragment(new SportIdeaFragment(), "ФК Ростов");


        viewPager.setAdapter(adapter);



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public void onStart() {

        super.onStart();




    }


    private void LoadUserData() {

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status") && dataSnapshot.hasChild("image"))
                {
                    userName.setText(dataSnapshot.child("name").getValue().toString());
                    //inputUserName.setText(dataSnapshot.child("name").getValue().toString());
                    //howMachIdeas.setText(dataSnapshot.child("status").getValue().toString());
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(retrieveProfileImage).into(userProfileImage);

                    //ui.ava = dataSnapshot.child("image").getValue().toString();
                }
                else if (dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("status") ){
                    userName.setText(dataSnapshot.child("name").getValue().toString());
                    howMachIdeas.setText(dataSnapshot.child("status").getValue().toString());
                }
                else {

                }


            }      @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
