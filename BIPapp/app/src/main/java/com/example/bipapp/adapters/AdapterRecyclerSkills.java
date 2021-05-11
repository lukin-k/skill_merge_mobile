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

    public void setSkills(ArrayList<Skill> skills) {
        mSkills = skills;
    }

    public AdapterRecyclerSkills() {
        mSkills = new ArrayList<>();
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
                //TODO set norm color
                ((CardView) mView.findViewById(R.id.cv)).setCardBackgroundColor(Color.WHITE);
            }
        };
        return viewHolderSkill;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSkill viewHolderSkill, int i) {
        viewHolderSkill.onBind(mSkills.get(i));
    }

    @Override
    public int getItemCount() {
        return mSkills.size();
    }
}
