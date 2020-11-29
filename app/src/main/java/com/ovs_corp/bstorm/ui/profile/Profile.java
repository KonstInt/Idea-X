package com.ovs_corp.bstorm.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ovs_corp.bstorm.R;
import com.ovs_corp.bstorm.ui.dashboard.DashboardViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private TextView howMachIdeas;
    private TextView userName;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    GridView gvMain;
    ArrayList<String> items = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //gvMain = root.findViewById(R.id.gvMain);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();



        userName = root.findViewById(R.id.header_username);
        //howMachIdeas = root.findViewById(R.id.header_ideas_num);
        userProfileImage = root.findViewById(R.id.header_ava);

        LoadUserData();


     //   GirdAdapterShop adapterShop = new GirdAdapterShop(getContext(), items);
//        gvMain.setAdapter(adapterShop);
       // adjustGridView();
        return root;
    }

    /*private void adjustGridView() {
        gvMain.setNumColumns(2);
        gvMain.setColumnWidth(210);
        gvMain.setVerticalSpacing(45);
        gvMain.setHorizontalSpacing(35);
        gvMain.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

    }*/

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
                    //howMachIdeas.setText(dataSnapshot.child("status").getValue().toString());
                }
                else {

                }


            }      @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
