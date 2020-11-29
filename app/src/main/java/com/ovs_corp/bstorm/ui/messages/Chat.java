package com.ovs_corp.bstorm.ui.messages;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ovs_corp.bstorm.AppInfo;
import com.ovs_corp.bstorm.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private String chatGroupName, messageReceiverImage, messageSenderID;

    private TextView chatName, usersOnline, my_date, their_date, u_name;
    private ImageView back;
    private CircleImageView chatImage;



    private FirebaseAuth mAuth;
    private DatabaseReference RootRef, GroupMessageKeyRef, GroupNameRef;
    private Button scroll;
    private ImageButton SendMessageButton;
    private EditText MessageInputText;
    private Button SendFilesButton;
    private String  currUserName, currentDate, currentTime;
    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;
    private Uri fileUri;
    private StorageTask uploadTask;
    private String checker = "", myUrl = "";
    private String saveCurrentTime, saveCurrentDate;
    private int scroll_pos;
    private ProgressDialog loadingBar;
    private static final int  GalleryPick = 1;
    private StorageReference UserProfileImagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_group_chat);


        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        LoadUI();
        chatGroupName = getIntent().getExtras().get("chat_name").toString();
        messageReceiverImage = getIntent().getExtras().get("chat_image").toString();

        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(AppInfo.subject).child(chatGroupName).child("messages");
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Chat Images");

        InitializeComponents();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        chatName.setText(chatGroupName);
        Picasso.get().load(messageReceiverImage).placeholder(R.drawable.ava).into(chatImage);


        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendMessageToDataBase();
                MessageInputText.setText("");

            }
        });





        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
            }
        });


        RootRef.child("Groups").child(AppInfo.subject).child(chatGroupName).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages messages = dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();
                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                scroll_pos = userMessagesList.getAdapter().getItemCount();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //DisplayLastSeen();
    }


    private void LoadUI() {
        RootRef.child("Users").child(messageSenderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currUserName = dataSnapshot.child("name").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeComponents() {
        chatName = findViewById(R.id.chat_name);
        chatImage = findViewById(R.id.in_chat_room_image);
        SendMessageButton = findViewById(R.id.send_message);
        MessageInputText = findViewById(R.id.their_message_body);
        userMessagesList = findViewById(R.id.chat_container);
        my_date = findViewById(R.id.my_date);
        their_date = findViewById(R.id.their_date);
        u_name = findViewById(R.id.user_name_in_chat_room);
        back = findViewById(R.id.back_to_chat_list_room);
        scroll = findViewById(R.id.chat_scroll);
        SendFilesButton = findViewById(R.id.files_message);
        loadingBar = new ProgressDialog(this);
        messageAdapter = new MessageAdapter(messagesList);

        Context context;
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);


        SendFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb;
                CharSequence[] options = new CharSequence[]{"Фото", "PDF", ".docx"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this);
                builder.setTitle("Выберите файл");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            if(which == 0)
                                {
                                    checker="image";
                                    Intent gallery = new Intent();
                                    gallery.setAction(Intent.ACTION_GET_CONTENT);
                                    gallery.setType("image/*");
                                    startActivityForResult(gallery,1);
                                }
                            if(which==1)
                                {

                                }

                        if(which==1)
                            {
                                checker="docx";
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Выберите фото"), 228);
                            }


                    }
                });
                builder.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {





                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri= data.getData();//This contains the cropped image
                final String messagekEY = GroupNameRef.push().getKey();
                final StorageReference filePath=UserProfileImagesRef.child(messagekEY+".jpg");

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
                            Toast.makeText(Chat.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                            if (downloadUri != null) {

                                String downloadUrl = downloadUri.toString();
                                String message = MessageInputText.getText().toString();



                                    Calendar calForDate = Calendar.getInstance();
                                    SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    currentDate = currentDateFormat.format(calForDate.getTime());

                                    Calendar calForTime = Calendar.getInstance();
                                    SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
                                    currentTime = currentTimeFormat.format(calForTime.getTime());


                                    HashMap<String, Object> groupMessageKey = new HashMap<>();
                                    GroupNameRef.updateChildren(groupMessageKey);

                                    RootRef.child("Groups").child(AppInfo.subject).child(chatGroupName).child("Info").child("last_message").setValue("Фото");
                                    GroupMessageKeyRef = GroupNameRef.child(messagekEY);

                                    Map<String, Object> messageInfoMap = new HashMap<>();
                                    messageInfoMap.put("from", messageSenderID);
                                    messageInfoMap.put("name", currUserName);
                                    messageInfoMap.put("message", downloadUrl);
                                    messageInfoMap.put("date", currentDate);
                                    messageInfoMap.put("time", currentTime);
                                    messageInfoMap.put("type","image");
                                    GroupMessageKeyRef.updateChildren(messageInfoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        if(!task.isSuccessful()){
                                            String error=task.getException().toString();
                                            Toast.makeText(Chat.this,"Error : "+error,Toast.LENGTH_LONG).show();
                                        }else{

                                        }
                                    }
                                });
                            }

                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(Chat.this,"Error",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }



    private void sendMessageToDataBase() {
        String message = MessageInputText.getText().toString();
        String messagekEY = GroupNameRef.push().getKey();

        if (TextUtils.isEmpty(message))
        {
            return;
        }
        else

        {
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
            currentTime = currentTimeFormat.format(calForTime.getTime());


            HashMap<String, Object> groupMessageKey = new HashMap<>();
            GroupNameRef.updateChildren(groupMessageKey);

            RootRef.child("Groups").child(AppInfo.subject).child(chatGroupName).child("Info").child("last_message").setValue(message);
            GroupMessageKeyRef = GroupNameRef.child(messagekEY);

            Map<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("from", messageSenderID);
            messageInfoMap.put("name", currUserName);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            messageInfoMap.put("type","text");
            GroupMessageKeyRef.updateChildren(messageInfoMap);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        scroll.setVisibility(View.GONE);

    }


}
