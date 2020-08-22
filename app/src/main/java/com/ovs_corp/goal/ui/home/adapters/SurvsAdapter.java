package com.ovs_corp.goal.ui.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.goal.R;
import com.ovs_corp.goal.ui.home.survs.SurveySet;

import java.util.ArrayList;

public class SurvsAdapter extends RecyclerView.Adapter<SurvsAdapter.ViewHolder> {

    private ArrayList<SurveySet> ideasList;
    private LayoutInflater mInflater;
    private SurvsAdapter.ItemClickListener mClickListener;
    Context mc;

    // data is passed into the constructor
    public SurvsAdapter(Context context, ArrayList<SurveySet> ideasList) {
        mc = context;
        this.mInflater = LayoutInflater.from(context);
        this.ideasList = ideasList;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public SurvsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.survey_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.topic.setText(ideasList.get(position).title);
       holder.first.setText(ideasList.get(position).vote1Text);
       holder.second.setText(ideasList.get(position).vote2Text);
       holder.mainBody.setText(ideasList.get(position).description);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return ideasList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView topic;
        TextView mainBody;
        Button first, second;


        ViewHolder(final View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.title_main_sur);
            mainBody = itemView.findViewById(R.id.sur_text_body);
            first = itemView.findViewById(R.id.vote_1);
            second = itemView.findViewById(R.id.vote_2);




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

