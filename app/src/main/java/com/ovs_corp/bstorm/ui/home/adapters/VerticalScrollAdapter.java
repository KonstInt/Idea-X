package com.ovs_corp.bstorm.ui.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.bstorm.IdeaViewAll;
import com.ovs_corp.bstorm.R;
import com.ovs_corp.bstorm.ui.home.idea_create.IdeaRsDto;

import java.util.ArrayList;

public class VerticalScrollAdapter  extends RecyclerView.Adapter<VerticalScrollAdapter.ViewHolder> {

    private ArrayList<IdeaRsDto> ideasList;
    private LayoutInflater mInflater;
    private IdeasAdapter.ItemClickListener mClickListener;
    Context mc;

// data is passed into the constructor
    public VerticalScrollAdapter(Context context, ArrayList<IdeaRsDto> ideasList) {
        mc = context;
        this.mInflater = LayoutInflater.from(context);
        this.ideasList = ideasList;
        }

// inflates the row layout from xml when needed
        @Override
        @NonNull
        public  VerticalScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.vertical_idea_item, parent, false);

        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String hd;
        //if (ideasList.get(position).topics.size() != 0){
        //hd = ideasList.get(position).topics.get(0).topic;
        //}
        //else
        //{
        //hd = "Газпром";
        //}
       // String body = ideasList.get(position).summary.substring(0, 30);
        int likes = ideasList.get(position).getVotesYes();


       // holder.way.setText(ideasList.get(position).thematics);
        holder.likes.setText(String.valueOf(likes));
       holder.mainBody.setText(ideasList.get(position).getSummary());
        }


// total number of rows
@Override
public int getItemCount() {
        return ideasList.size();
        }

// stores and recycles views as they are scrolled off screen
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView way;
    TextView mainBody;
    TextView likes;
    CardView cv;

    ViewHolder(final View itemView) {
        super(itemView);
        way = itemView.findViewById(R.id.vertical_idea_main_way);
        mainBody = itemView.findViewById(R.id.vertical_idea_text_body);
        likes = itemView.findViewById(R.id.vertical_idea_like);

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = getAdapterPosition();
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = getAdapterPosition();
                Uri uri;
                Intent g = new Intent(mc, IdeaViewAll.class);
                g.putExtra("Item", i);
                mc.startActivity(g);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        ideasList.get(getAdapterPosition());
    }
}

// convenience method for getting data at click position
    /*public String getItem(int id) {
        return mAnimals.get(id);
    }*/


// parent activity will implement this method to respond to click events
public interface ItemClickListener {
    void onItemClick(View view, int position);
}





}

