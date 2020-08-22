package com.ovs_corp.goal.ui.messages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ovs_corp.goal.R;
import com.squareup.picasso.Picasso;

public class FullScreenImage extends AppCompatActivity {

    ImageView fullScreen;
    String im = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        fullScreen = findViewById(R.id.image_fullscreen);
        im = getIntent().getExtras().get("IMG").toString();
        if(!im.isEmpty()){
            Picasso.get().load(im).into(fullScreen);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void BackToChatMessages(View view) {
        super.onBackPressed();
        finish();
    }
}
