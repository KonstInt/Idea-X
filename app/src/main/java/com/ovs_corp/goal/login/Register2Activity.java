package com.ovs_corp.goal.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ovs_corp.goal.AppInfo;
import com.ovs_corp.goal.MainActivity;
import com.ovs_corp.goal.R;
import com.ovs_corp.goal.UserInfo;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register2Activity extends AppCompatActivity {

    private Button UpdateAccoutSettings;
    private Switch myswich;
    private TextView back;
    private EditText userStatus;
    private TextView userName, header;
    private EditText inputUserName;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private UserInfo ui;
    private static final int  GalleryPick = 1;
    private StorageReference UserProfileImagesRef;
    private ProgressDialog loadingBar;
    private boolean firstLogIn;
    private Dialog dialog;
    private Button logout;
    private TextView x_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        setContentView(R.layout.activity_register2);


        userProfileImage = findViewById(R.id.set_profile_image_f);
        loadingBar = new ProgressDialog(this);
        inputUserName = findViewById(R.id.input_user_name_f);


        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,GalleryPick);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        //header.setText("Профиль");






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
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
                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri=result.getUri();//This contains the cropped image

                final StorageReference filePath=UserProfileImagesRef.child(currentUserID+".jpg");

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
                            Toast.makeText(Register2Activity.this, "Successfully uploaded!!!", Toast.LENGTH_SHORT).show();

                            if (downloadUri != null) {

                                String downloadUrl = downloadUri.toString();
                                RootRef.child("Users").child(currentUserID).child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        if(!task.isSuccessful()){
                                            String error=task.getException().toString();
                                            Toast.makeText(Register2Activity.this,"Error : "+error,Toast.LENGTH_LONG).show();
                                        }else{

                                        }
                                    }
                                });
                            }

                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(Register2Activity.this,"Error",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void FinishRegister(View view) {
        String setUserName = inputUserName.getText().toString();
        String setUserStatus = " ";

        if(TextUtils.isEmpty(setUserStatus))
        {
            setUserStatus = " ";
        }
        if(TextUtils.isEmpty(setUserName))
        {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, String> profile = new HashMap<>();
            profile.put("uid", currentUserID);
            profile.put("name", setUserName);
            profile.put("status", setUserStatus);
            RootRef.child("Users").child(currentUserID).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(Register2Activity.this, "Успешно!", Toast.LENGTH_SHORT).show();
                        Intent g = new Intent(Register2Activity.this, MainActivity.class);
                        startActivity(g);
                    }

                    else {
                        String ex = task.getException().getMessage();
                        Toast.makeText(Register2Activity.this, "Ошибка " + ex, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }
}
