package com.ovs_corp.bstorm.ui.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ovs_corp.bstorm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> MessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef, userRef;
    MessageAdapter(List<Messages> MessagesList){
        this.MessagesList = MessagesList;
    }



    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView my_message, their_message, my_date, their_date, name;
        CircleImageView their_ava;
        ImageView my_image, their_image;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            my_message = itemView.findViewById(R.id.my_messsage_text);
            their_message = itemView.findViewById(R.id.their_message_text);
            their_ava = itemView.findViewById(R.id.message_profile_image);
            my_date = itemView.findViewById(R.id.my_date);
            their_date = itemView.findViewById(R.id.their_date);
            name = itemView.findViewById(R.id.user_name_in_chat_room);
            my_image = itemView.findViewById(R.id.message_sender_image_view);
            their_image = itemView.findViewById(R.id.their_message_image_view);

        }
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);

        mAuth = FirebaseAuth.getInstance();
       // RootRef = FirebaseDatabase.getInstance().getReference();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {

        String messageSenderID = mAuth.getCurrentUser().getUid();


        final Messages messages = MessagesList.get(position);
        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("image"))
                {
                    String pic = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(pic).placeholder(R.drawable.ava).into(holder.their_ava);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.their_ava.setVisibility(View.GONE);
        holder.their_message.setVisibility(View.GONE);
        holder.name.setVisibility(View.GONE);
        holder.their_date.setVisibility(View.GONE);
        holder.my_message.setVisibility(View.GONE);
        holder.my_date.setVisibility(View.GONE);
        holder.my_image.setVisibility(View.GONE);
        holder.their_image.setVisibility(View.GONE);
        if (fromMessageType.equals("text"))
        {

            if (fromUserID.equals(messageSenderID))
            {
                holder.my_message.setVisibility(View.VISIBLE);
                holder.my_date.setVisibility(View.VISIBLE);
                holder.my_message.setBackgroundResource(R.drawable.my_message);
                holder.my_message.setText(messages.getMessage());
                holder.my_date.setText(messages.getTime());
            }
            else
            {
                holder.their_ava.setVisibility(View.VISIBLE);
                holder.their_message.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);
                holder.their_date.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);
                holder.their_date.setText(messages.getTime());
                holder.name.setText(messages.getName());
                holder.their_message.setBackgroundResource(R.drawable.their_message);
                holder.their_message.setText(messages.getMessage());
            }

        }
        else if (fromMessageType.equals("image"))
        {
            if (fromUserID.equals(messageSenderID))
            {
               holder.my_image.setVisibility(View.VISIBLE);
               Picasso.get().load(messages.getMessage()).into(holder.my_image);

            }
            else
            {
                holder.their_ava.setVisibility(View.VISIBLE);
                holder.their_image.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(holder.their_image);
            }

        }
    }

    @Override
    public int getItemCount() {
        return MessagesList.size();
    }

}