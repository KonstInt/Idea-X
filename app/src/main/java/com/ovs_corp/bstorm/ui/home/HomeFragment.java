package com.ovs_corp.bstorm.ui.home;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ovs_corp.bstorm.AppInfo;
import com.ovs_corp.bstorm.R;
import com.ovs_corp.bstorm.ui.home.adapters.IdeasAdapter;
import com.ovs_corp.bstorm.ui.home.adapters.NewsAdapter;
import com.ovs_corp.bstorm.ui.home.adapters.StoriesAdapter;
import com.ovs_corp.bstorm.ui.home.adapters.SurvsAdapter;
import com.ovs_corp.bstorm.ui.home.news_create.NewsSet;
import com.ovs_corp.bstorm.ui.home.survs.SurveySet;
import com.ovs_corp.bstorm.ui.messages.DataChat;

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


    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> group_list = new ArrayList<>();
    private DatabaseReference GroupRef2;
    private RecyclerView chatList;
    private DatabaseReference ChatsRef;
    private String CurrentUserID;
    private TextView tv;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();



        GroupRef2 = FirebaseDatabase.getInstance().getReference().child("Groups").child(AppInfo.subject);
        //m_list = root.findViewById(R.id.group_list);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, group_list);
        //tv = root.findViewById(R.id.chats_subj);

        chatList = root.findViewById(R.id.message_home);
        chatList.setLayoutManager(new GridLayoutManager(root.getContext(), 2, GridLayoutManager.VERTICAL, false));
        chatList.setNestedScrollingEnabled(false);

        ChatsRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(AppInfo.subject);


        /*goto_vertical = root.findViewById(R.id.goto_vertical);
        goto_vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent g = new Intent(getActivity(), VerticalScrollideasActivity.class);
                startActivity(g);

            }
        });*/



        ArrayList<Integer> viewColors = new ArrayList<>();


        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Общайся");
        animalNames.add("Делись");
        animalNames.add("Оценивай");
        animalNames.add("Комментируй");
        animalNames.add("И все");



        // set up the RecyclerView
        /*RecyclerView recyclerView = root.findViewById(R.id.heaer_stories);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new StoriesAdapter(getContext(), viewColors, animalNames);
        recyclerView.setAdapter(adapter);*/




        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://84.201.178.142:8080/ideas/api/v1/").addConverterFactory(GsonConverterFactory.create()).build();
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

        //TODO
        adapter3 = new NewsAdapter(getContext(), AppInfo.news_cards);
        ideasRecycler3.setAdapter(adapter3);
        ideasRecycler3.setLayoutFrozen(true);







        final Call<List<SurveySet>> get_polls = jsonPlaceHolder.getPoll();

        get_polls.enqueue(new Callback<List<SurveySet>>() {
            @Override
            public void onResponse(Call<List<SurveySet>> call, Response<List<SurveySet>> response) {
                if(!response.isSuccessful())
                    return;

                AppInfo.survey_cards = (ArrayList<SurveySet>) response.body();


                adapter4 = new SurvsAdapter(getContext(), AppInfo.survey_cards);
            }

            @Override
            public void onFailure(Call<List<SurveySet>> call, Throwable t){

            }
        });
        adapter4 = new SurvsAdapter(getContext(), AppInfo.survey_cards);


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
        adapter.addFragment(new IdeaFragment(), "Все");
        adapter.addFragment(new IdeaFragment(), "Идеи");
        adapter.addFragment(new IdeaFragment(), "Рац. Предложения");
        adapter.addFragment(new IdeaFragment(), "Цифр. Трансформация");



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
        FirebaseRecyclerOptions<DataChat> options = new FirebaseRecyclerOptions.Builder<DataChat>().setQuery(ChatsRef, DataChat.class).build();
        FirebaseRecyclerAdapter<DataChat,HomeFragment.ChatsViewHolder1> adapter = new FirebaseRecyclerAdapter<DataChat, HomeFragment.ChatsViewHolder1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final HomeFragment.ChatsViewHolder1 holder, int position, @NonNull DataChat model) {
                final String GroupIDs = getRef(position).getKey();
                final String[] Image = {"default_image"};
                holder.linearLayout.setBackgroundColor(getMatColor("100"));
                GroupRef2.child(GroupIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("Info").exists()) {

                            if (dataSnapshot.child("Info").hasChild("image")) {
                                //Image[0] = dataSnapshot.child("image").getValue().toString();
                                //Picasso.get().load(Image[0]).into(holder.chatImage);
                            }

                            final String ChatName;

                            if (dataSnapshot.child("Info").hasChild("name")) {
                                ChatName = dataSnapshot.child("Info").child("name").getValue().toString();

                            } else {
                                ChatName = " ";
                            }

                            if(dataSnapshot.child("users").hasChild(mAuth.getCurrentUser().getUid()))
                            {
                                holder.lastMessage.setText("Присоединены");
                                holder.lastMessage.setTextColor(Color.parseColor("#45cc28"));
                            }
                            else
                            {
                                holder.lastMessage.setText("Присоединиться");
                                holder.lastMessage.setTextColor(Color.BLACK);
                            }

                            final String LastMessage;
                            if (dataSnapshot.child("Info").hasChild("last_message")) {
                                LastMessage = dataSnapshot.child("Info").child("last_message").getValue().toString();
                            } else {
                                LastMessage = "Нет сообщений";
                            }
                            holder.chatName.setText(ChatName);


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!dataSnapshot.child("users").hasChild(mAuth.getCurrentUser().getUid()))
                                    {
                                        RootRef.child("Groups").child(AppInfo.subject).child(GroupIDs).child("users").child(mAuth.getCurrentUser().getUid()).setValue("d", "s");
                                        Toast backToast = Toast.makeText(getContext(), "Вы успешно добавлены!", Toast.LENGTH_SHORT);
                                        backToast.show();
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @NonNull
            @Override
            public HomeFragment.ChatsViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_home, parent, false);
                return new HomeFragment.ChatsViewHolder1(view);

            }
        };


        chatList.setAdapter(adapter);
        adapter.startListening();

       /* switch (AppInfo.subject){

            case  ("math"):
                tv.setText("Чаты (математика)");
                break;
            case ("rus"):
                tv.setText("Чаты (русский язык)");
                break;
            case ("inf"):
                tv.setText("Чаты (информатика)");
                break;
            default:
                tv.setText("Tutor");
                break;

        } */

    }

    public static class  ChatsViewHolder1 extends RecyclerView.ViewHolder
    {
        ImageView chatImage;
        TextView lastMessage, chatName;
        LinearLayout linearLayout;


        public ChatsViewHolder1(@NonNull View itemView)
        {
            super(itemView);

            //chatImage = itemView.findViewById(R.id.chat_room_image_home);
            lastMessage = itemView.findViewById(R.id.last_message_home);
            chatName = itemView.findViewById(R.id.chat_name_home);
            linearLayout = itemView.findViewById(R.id.messages_layout_home);
        }
    }

    private int getMatColor(String typeColor)
    {
        int returnColor = Color.BLACK;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getContext().getPackageName());

        if (arrayId != 0)
        {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

}
