package com.example.bipapp.adapters;

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
    private int mSelectedTag;

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
        mSelectedTag = -1;
    }

    public AdapterRecyclerProjectTags() {
        mTags = new ArrayList<>();
        mSelectedTag = -1;
    }

    //TODO unselected last selected tag

    @NonNull
    @Override
    public ViewHolderProjectTag onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_project_tag, viewGroup, false);
        ViewHolderProjectTag viewHolderProjectTag = new ViewHolderProjectTag(view) {
            @Override
            public void onClickProjectTag() {
                int i = getAdapterPosition();
                Log.v("Tag", "click " + i);
                if (mSelectedTag == i) {
                    mSelectedTag = -1;
                }else {
                    mSelectedTag = i;
                }
                changeColor(mSelectedTag == i);
            }
        };
        return viewHolderProjectTag;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProjectTag viewHolderProjectTag, int i) {
        viewHolderProjectTag.onBind(mTags.get(i), mSelectedTag == i);
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public String getSelectedTag() {
        if (mSelectedTag < 0) {
            return "";
        } else {
            return mTags.get(mSelectedTag);
        }
    }
}