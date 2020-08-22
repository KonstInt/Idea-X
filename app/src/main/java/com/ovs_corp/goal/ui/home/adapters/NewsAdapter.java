package com.ovs_corp.goal.ui.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.goal.R;
import com.ovs_corp.goal.ui.home.NewsFullscreen;
import com.ovs_corp.goal.ui.home.news_create.NewsSet;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsSet> ideasList;
    private LayoutInflater mInflater;
    private NewsAdapter.ItemClickListener mClickListener;
    Context mc;

    // data is passed into the constructor
    public NewsAdapter(Context context, ArrayList<NewsSet> ideasList) {
        mc = context;
        this.mInflater = LayoutInflater.from(context);
        this.ideasList = ideasList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.news_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String title = ideasList.get(position).title;

        boolean rd = ideasList.get(position).show;
        int g = ideasList.get(position).newsStatus.id;


        holder.title.setText(title);
        if(g == 1)
        {
            holder.isReady.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.inProc.setVisibility(View.VISIBLE);
        }



    }


    // total number of rows
    @Override
    public int getItemCount() {
        return ideasList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int ready;
        TextView title;
        ImageView inProc;
        ImageView isReady;

        ViewHolder(final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.newsTitle);
            inProc = itemView.findViewById(R.id.in_process);
            isReady = itemView.findViewById(R.id.relized);

            inProc.setVisibility(View.GONE);
            isReady.setVisibility(View.GONE);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    Intent g = new Intent(mc, NewsFullscreen.class);
                    g.putExtra("nfs", i);
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

