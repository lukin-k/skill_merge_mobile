package com.example.bipapp.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;

import java.util.ArrayList;

public class AdapterRecyclerProjectTags extends RecyclerView.Adapter<ViewHolderProjectTag> {
    private ArrayList<String> mTags;
    private boolean[] mSelectedTags;

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
        mSelectedTags = new boolean[tags.size()];
    }

    public AdapterRecyclerProjectTags() {
        mTags = new ArrayList<>();
        mSelectedTags = new boolean[0];
    }

    @NonNull
    @Override
    public ViewHolderProjectTag onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_project_tag, viewGroup, false);
        ViewHolderProjectTag viewHolderProjectTag = new ViewHolderProjectTag(view) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickProjectTag() {
                int i = getAdapterPosition();
                Log.v("Tag", "click " + i);
                mSelectedTags[i] = !mSelectedTags[i];
                changeColor(mSelectedTags[i]);
            }
        };
        return viewHolderProjectTag;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProjectTag viewHolderProjectTag, int i) {
        viewHolderProjectTag.onBind(mTags.get(i), mSelectedTags[i]);
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public boolean[] getSelectedTags() {
        return mSelectedTags;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }
}