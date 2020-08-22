package com.ovs_corp.goal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ovs_corp.goal.login.Login;
import com.ovs_corp.goal.sharedPref.NightSharedPref;
import com.ovs_corp.goal.ui.home.VerticalScrollNews;
import com.ovs_corp.goal.ui.home.idea_create.NewIdea;
import com.ovs_corp.goal.ui.home.user.UserProfile;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView groupImage;
    private StorageReference GroupImagesRef;
    private ProgressDialog loadingBar;
    private String Gname;
    private Button create_group;
    private EditText groupName;
    private TextView groupMessage;
    private TextView photoMessage;
    private Button groupCreateFinish;
    private Dialog dialog;

    Button btn;
    private FirebaseUser currentUser;
    protected FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private Toast backToast;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        DataLoad();

        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        GroupImagesRef = FirebaseStorage.getInstance().getReference().child("Group Images");

        final BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_ideas, R.id.navigation_messages).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(MainActivity.this, NewIdea.class);
                startActivity(g);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
            return;
        }

        if(currentUser == null) {
            Intent g = new Intent(MainActivity.this, Login.class);
            g.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(g);
            finish();
        }
        else {
            VerifyUserExistance();
        }

    }

    private void VerifyUserExistance() {
        String curId = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(curId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").exists()) {

                } else {
                    Intent g = new Intent(MainActivity.this, UserProfile.class);
                    g.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    g.putExtra("FirstLogin", true);
                    startActivity(g);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



        @Override
        public void onBackPressed() {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                Intent g = new Intent(MainActivity.this, MainActivity.class);
                g.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                g.putExtra("EXIT", true);
                startActivity(g);
                finish();
                return;
            } else {
                backToast = Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }


        @Override
    protected void onDestroy() {
        super.onDestroy();
        NightSharedPref n_mode = new NightSharedPref(this);
        n_mode.setNightModeState(AppInfo.nightModeState);




        //OtherInfoSharedPref tst = new OtherInfoSharedPref(this);
        //tst.setDepartment(AppInfo.department);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            Uri ImageUri = data.getData();


            CropImage.activity(ImageUri)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                loadingBar.setTitle("Set Group Image");
                loadingBar.setMessage("Please wait, your group image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri=result.getUri();//This contains the cropped image

                final StorageReference filePath= GroupImagesRef.child(Gname + ".jpg");//This way we link the userId with image. This is the file name of the image stored in firebase database.

                UploadTask uploadTask=filePath.putFile(resultUri);
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Toast.makeText(MainActivity.this, Gname + " успешно создана", Toast.LENGTH_SHORT).show();
                            if (downloadUri != null) {

                                String downloadUrl = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                                RootRef.child("Groups").child(AppInfo.department).child(Gname).child("Info").child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        if(!task.isSuccessful()){
                                            String error=task.getException().toString();
                                            Toast.makeText(MainActivity.this,"Error : "+error,Toast.LENGTH_LONG).show();
                                        }else{

                                        }
                                    }
                                });
                            }

                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }


                    }
                });
            }
        }
    }

    private void CreateNewGroup() {
        Gname = groupName.getText().toString();
        RootRef.child("Groups").child(AppInfo.department).child(Gname).child("messages").setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    HashMap<String, String> group = new HashMap<>();
                    group.put("name", Gname);
                    group.put("last_message", "");
                    RootRef.child("Groups").child(AppInfo.department).child(Gname).child("Info").setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {

                            }
                            else
                            {
                                String ex = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, "Ошибка "+ex, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });


                    groupName.setFocusable(false);
                    create_group.setVisibility(View.GONE);
                }
            }
        });
    }


    public void show(View view) {
        loadingBar = new ProgressDialog(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.create_group_card);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView back = dialog.findViewById(R.id.group_create_x);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        groupMessage = dialog.findViewById(R.id.group_create_message);
        photoMessage = dialog.findViewById(R.id.group_photo_text);
        groupName = dialog.findViewById(R.id.set_group_name);
        groupCreateFinish = dialog.findViewById(R.id.group_finish);
        groupImage = dialog.findViewById(R.id.set_group_image);
        create_group = dialog.findViewById(R.id.group_create);

        groupMessage.setVisibility(View.VISIBLE);
        photoMessage.setVisibility(View.GONE);
        groupName.setVisibility(View.VISIBLE);
        groupCreateFinish.setVisibility(View.GONE);
        groupImage.setVisibility(View.GONE);
        create_group.setVisibility(View.VISIBLE);


        groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,1);
            }
        });





        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewGroup();
                groupMessage.setVisibility(View.GONE);
                photoMessage.setVisibility(View.VISIBLE);
                groupName.setVisibility(View.GONE);
                groupCreateFinish.setVisibility(View.VISIBLE);
                groupImage.setVisibility(View.VISIBLE);
                create_group.setVisibility(View.GONE);

            }
        });


        groupCreateFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName.setText("");
                dialog.dismiss();
            }
        });



        dialog.show();

    }


    public void DataLoad() {
        NightSharedPref n_mode = new NightSharedPref(this);
        AppInfo.nightModeState = n_mode.loadNightModeState();


        //OtherInfoSharedPref tst = new OtherInfoSharedPref(this);
        //AppInfo.department = tst.loadDepartment();

    }



    public void OpenMessages(View view) {
        //Intent g = new Intent(this, ChatActivity.class);
        //startActivity(g);
    }






    public void openProfile(View view) {
        Intent g = new Intent(this, UserProfile.class);
        startActivity(g);
    }


    public void openNews(View view) {
        Intent g = new Intent(this, VerticalScrollNews.class);
        startActivity(g);
    }
}
