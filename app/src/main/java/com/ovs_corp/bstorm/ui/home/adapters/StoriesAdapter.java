package com.ovs_corp.bstorm.ui.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ovs_corp.bstorm.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<Integer> mViewColors;
    private List<String> mAnimals;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public StoriesAdapter(Context context, List<Integer> colors, List<String> animals) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mAnimals = animals;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.stories_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int color = mViewColors.get(position);
        String animal = mAnimals.get(position);
        //holder.myView.setBackgroundColor(color);
        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mAnimals.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.stories_mini_image);
            myTextView = itemView.findViewById(R.id.header_stories_tag);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mAnimals.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}