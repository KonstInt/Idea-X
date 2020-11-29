package com.ovs_corp.bstorm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ovs_corp.bstorm.ui.home.idea_create.IdeaRsDto;

public class IdeaViewAll extends AppCompatActivity {


    TextView ways, main_idea, results, place, predictable_result, cost, bring, title, num, likes;
    int i;

    IdeaRsDto is;

    ImageView up, down;
    TextView Addit;
    LinearLayout linearLayout;


    boolean pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_view_all);

    }



    public void back_from_idea_fc(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        i = getIntent().getIntExtra("Item", 0);
        is = AppInfo.idea_cards.get(i);
        onCompAdd();
        pressed = false;

    }

    public void onCompAdd(){

        ways = findViewById(R.id.items_view_all);
        main_idea = findViewById(R.id.idea_test_body_all);
        results = findViewById(R.id.items_addit_all);
        place = findViewById(R.id.idea_place_all);
        predictable_result = findViewById(R.id.predictions_all);
        //cost = findViewById(R.id.price_all);
        //bring = findViewById(R.id.effect_price_all);
        title = findViewById(R.id.idea_header);
        num = findViewById(R.id.testNumHeaderAll);

        Addit = findViewById(R.id.addit_view_all);
        linearLayout = findViewById(R.id.additional_info_idea);
        linearLayout.setVisibility(View.GONE);
        up = findViewById(R.id.raw_down);
        up.setVisibility(View.VISIBLE);
        down = findViewById(R.id.raw_up);
        down.setVisibility(View.GONE);
        likes = findViewById(R.id.vertical_idea_like);




        //if(is.topics.size() != 0)
        //ways.setText(is.topics.get(0).topic);
        //
        //results.setText(is.);
       // predictable_result.setText(is.getBusinessDirections().get(1));
        //place.setText(is.);
//        cost.setText(is.minusCost.toString());
//        bring.setText(is.plusCost.toString());
        title.setText(is.getTitle());
        main_idea.setText(is.getSummary());
        num.setText("Предложение №"+is.getId());

        likes.setText(String.valueOf(is.getVotesYes()));


    }

    public void AdditionalShow(View view) {

        if(pressed)
        {
            linearLayout.setVisibility(View.GONE);
            up.setVisibility(View.VISIBLE);
            down.setVisibility(View.GONE);
            pressed = false;
        }
        else
            {
                linearLayout.setVisibility(View.VISIBLE);
                up.setVisibility(View.GONE);
                down.setVisibility(View.VISIBLE);
                pressed = true;
            }
    }
}
