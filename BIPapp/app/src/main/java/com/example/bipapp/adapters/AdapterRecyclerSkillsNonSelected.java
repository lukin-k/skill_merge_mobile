package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderSkill;
import com.example.bipapp.models.Skill;

import java.util.ArrayList;

public class AdapterRecyclerSkillsNonSelected extends RecyclerView.Adapter<ViewHolderSkill> {
    private ArrayList<Skill> mSkills;

    public void setSkills(ArrayList<Skill> skills) {
        mSkills = skills;
    }


    public AdapterRecyclerSkillsNonSelected() {
        mSkills = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderSkill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_skill, viewGroup, false);
        ViewHolderSkill viewHolderSkill = new ViewHolderSkill(view) {
            @Override
            public void onClickSkill() {
            }
        };
        return viewHolderSkill;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSkill viewHolderSkill, int i) {
        viewHolderSkill.onBind(mSkills.get(i), false);
    }

    @Override
    public int getItemCount() {
        return mSkills.size();
    }

    public ArrayList<Skill> getSkills() {
        return mSkills;
    }
}
