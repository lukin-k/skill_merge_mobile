package com.example.bipapp.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.models.Skill;

import java.util.ArrayList;

public class AdapterRecyclerSkills extends RecyclerView.Adapter<ViewHolderSkill> {
    private ArrayList<Skill> mSkills;
    private boolean[] mSelectedSkills;

    public void setSkills(ArrayList<Skill> skills) {
        mSkills = skills;
        mSelectedSkills = new boolean[skills.size()];
    }

    public void setSelectedSkills(ArrayList<Skill> selectedSkills){
        //TODO fill mSelectedSkills
    }

    public AdapterRecyclerSkills() {
        mSkills = new ArrayList<>();
        mSelectedSkills = new boolean[0];
    }

    @NonNull
    @Override
    public ViewHolderSkill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_skill, viewGroup, false);
        ViewHolderSkill viewHolderSkill = new ViewHolderSkill(view) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickSkill() {
                int i = getAdapterPosition();
                Log.v("Skill", "click " + i);
                mSelectedSkills[i] = !mSelectedSkills[i];
                changeColor(mSelectedSkills[i]);
            }
        };
        return viewHolderSkill;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSkill viewHolderSkill, int i) {
        viewHolderSkill.onBind(mSkills.get(i), mSelectedSkills[i]);
    }

    @Override
    public int getItemCount() {
        return mSkills.size();
    }

    public boolean[] getSelectedSkills() {
        return mSelectedSkills;
    }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }
}
