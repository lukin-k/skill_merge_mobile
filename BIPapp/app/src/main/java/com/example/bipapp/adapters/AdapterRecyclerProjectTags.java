package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderProjectTag;

import java.util.ArrayList;

public class AdapterRecyclerProjectTags extends RecyclerView.Adapter<ViewHolderProjectTag> {
    private ArrayList<String> mTags;
    private int mSelectedTag;
    private ViewHolderProjectTag mLastSelectedViewHolder;
    private final ArrayList<ViewHolderProjectTag> mViewHolderTagsList;

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
        mSelectedTag = -1;
    }

    public AdapterRecyclerProjectTags() {
        mTags = new ArrayList<>();
        mSelectedTag = -1;
        mLastSelectedViewHolder = null;
        mViewHolderTagsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderProjectTag onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_project_tag, viewGroup, false);
        ViewHolderProjectTag viewHolderProjectTag = new ViewHolderProjectTag(view) {
            @Override
            public void onClickProjectTag() {
                int i = getAdapterPosition();
                if (mSelectedTag == i) {
                    mSelectedTag = -1;
                    mLastSelectedViewHolder = null;
                } else {
                    if (mLastSelectedViewHolder != null && mLastSelectedViewHolder.getAdapterPosition() == mSelectedTag) {
                        mLastSelectedViewHolder.changeColor(false);
                    }

                    mSelectedTag = i;
                    mLastSelectedViewHolder = this;
                }
                changeColor(mSelectedTag == i);
            }
        };
        mViewHolderTagsList.add(viewHolderProjectTag);
        return viewHolderProjectTag;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProjectTag viewHolderProjectTag, int i) {
        viewHolderProjectTag.onBind(mTags.get(i), mSelectedTag == i);
        if (mSelectedTag == i) {
            mLastSelectedViewHolder = viewHolderProjectTag;
        }
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

    public void setSelectedTag(String tag) {
        for (int i = 0; i < mTags.size(); i++) {
            if (mTags.get(i).equals(tag)) {
                mSelectedTag = i;
                return;
            }
        }

        mSelectedTag = -1;
        if (mLastSelectedViewHolder != null) {
            mLastSelectedViewHolder.changeColor(false);
            mLastSelectedViewHolder = null;
        }
    }
}