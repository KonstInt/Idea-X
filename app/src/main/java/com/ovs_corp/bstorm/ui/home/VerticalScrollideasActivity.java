package com.ovs_corp.bstorm.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.bstorm.AppInfo;
import com.ovs_corp.bstorm.R;
import com.ovs_corp.bstorm.ui.home.adapters.VerticalScrollAdapter;


public class VerticalScrollideasActivity extends AppCompatActivity {


    VerticalScrollAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_vertical_scrollideas);


        RecyclerView recyclerView = findViewById(R.id.vertical_ideas);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(VerticalScrollideasActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new VerticalScrollAdapter(this, AppInfo.idea_cards);
        recyclerView.setAdapter(adapter);
    }

    public void GoBackFromerticalIdeas(View view) {
        super.onBackPressed();
    }
}

