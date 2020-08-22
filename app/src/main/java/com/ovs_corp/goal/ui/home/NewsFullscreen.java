package com.ovs_corp.goal.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ovs_corp.goal.AppInfo;
import com.ovs_corp.goal.R;
import com.ovs_corp.goal.ui.home.news_create.NewsSet;


public class NewsFullscreen extends AppCompatActivity {

    TextView header, title, main_body, text_relized, green, yellow;
    NewsSet ns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_fullscreen);
        header = findViewById(R.id.newsNumHeaderAll);
        title = findViewById(R.id.news_header);
        main_body = findViewById(R.id.news_test_body_all);
        text_relized = findViewById(R.id.relized);
        green = findViewById(R.id.gr_ye);
        yellow = findViewById(R.id.yl_ye);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int i = getIntent().getIntExtra("nfs", 0);
        ns = AppInfo.news_cards.get(i);
        header.setText("Новость №"+ ns.id);
        title.setText(ns.title);
        main_body.setText(ns.fullNews);

        green.setVisibility(View.GONE);
        yellow.setVisibility(View.GONE);

        if(ns.newsStatus.id == 1)
        {
            text_relized.setText("Реализованно");
            text_relized.setTextColor(Color.GREEN);
            green.setVisibility(View.VISIBLE);
            yellow.setVisibility(View.GONE);
        }
        else {
            text_relized.setText("В работе");
            text_relized.setTextColor(Color.parseColor("#FFD54F"));
            green.setVisibility(View.GONE);
            yellow.setVisibility(View.VISIBLE);

        }


    }

    public void back_from_news_fc(View view) {
        super.onBackPressed();
    }
}
