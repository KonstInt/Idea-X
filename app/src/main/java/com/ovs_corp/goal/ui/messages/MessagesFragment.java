package com.ovs_corp.goal.ui.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ovs_corp.goal.AppInfo;
import com.ovs_corp.goal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesFragment extends Fragment {




    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> group_list = new ArrayList<>();
    private DatabaseReference GroupRef2;
    private RecyclerView chatList;
    private DatabaseReference ChatsRef;
    private FirebaseAuth mAuth;
    private String CurrentUserID;
    private TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        GroupRef2 = FirebaseDatabase.getInstance().getReference().child("Groups").child(AppInfo.subject);
        //m_list = root.findViewById(R.id.group_list);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, group_list);
        tv = root.findViewById(R.id.chats_subj);

        chatList = root.findViewById(R.id.chat_list);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));


        mAuth = FirebaseAuth.getInstance();
        CurrentUserID = mAuth.getCurrentUser().getUid();
        ChatsRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(AppInfo.subject);






        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<DataChat> options = new FirebaseRecyclerOptions.Builder<DataChat>().setQuery(ChatsRef, DataChat.class).build();
        FirebaseRecyclerAdapter<DataChat, ChatsViewHolder> adapter = new FirebaseRecyclerAdapter<DataChat, ChatsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsViewHolder holder, int position, @NonNull DataChat model) {
                    final String GroupIDs = getRef(position).getKey();
                    final String[] Image = {"default_image"};

                    GroupRef2.child(GroupIDs).child("Info").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           if(dataSnapshot.exists()) {
                               if (dataSnapshot.hasChild("image")) {
                                   Image[0] = dataSnapshot.child("image").getValue().toString();
                                   Picasso.get().load(Image[0]).into(holder.chatImage);
                               }

                               final String ChatName;

                               if (dataSnapshot.hasChild("name")) {
                                   ChatName = dataSnapshot.child("name").getValue().toString();
                               } else {
                                   ChatName = " ";
                               }

                               final String LastMessage;
                               if (dataSnapshot.hasChild("last_message")) {
                                   LastMessage = dataSnapshot.child("last_message").getValue().toString();
                               } else {
                                   LastMessage = "Нет сообщений";
                               }
                               holder.lastMessage.setText(LastMessage);
                               holder.chatName.setText(ChatName);

                               holder.itemView.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent chatIntent = new Intent(getContext(), Chat.class);
                                       chatIntent.putExtra("chat_name", ChatName);
                                       chatIntent.putExtra("chat_image", Image[0]);
                                       startActivity(chatIntent);
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
            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list, parent, false);
                return new ChatsViewHolder(view);

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




    public static class  ChatsViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView chatImage;
        TextView lastMessage, chatName;


        public ChatsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            chatImage = itemView.findViewById(R.id.chat_room_image);
            lastMessage = itemView.findViewById(R.id.last_message);
            chatName = itemView.findViewById(R.id.chat_name);
        }
    }
}