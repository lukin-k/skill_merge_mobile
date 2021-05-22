package com.example.bipapp.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.models.Skill;

public abstract class ViewHolderSkill extends RecyclerView.ViewHolder {
    private TextView mTextType;
    private TextView mTextLevel;

    public ViewHolderSkill(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSkill();
            }
        });

        mTextType = itemView.findViewById(R.id.text_skill_type);
        mTextLevel = itemView.findViewById(R.id.text_skill_level);
    }

    abstract public void onClickSkill();


    public void onBind(Skill skill, boolean isSelected) {
        mTextType.setText(skill.getType());
        mTextLevel.setText(skill.getLevel());
        changeColor(isSelected);
    }

    @SuppressLint("ResourceAsColor")
    protected void changeColor(boolean isSelected) {
        int color = itemView.getResources().getColor(R.color.skill_not_selected);
        int textColor = itemView.getResources().getColor(R.color.deep_pipe_green);
        if (isSelected) {
            color = itemView.getResources().getColor(R.color.skill_selected);
            textColor = itemView.getResources().getColor(R.color.orange);
        }
        itemView.setBackgroundColor(color);
        mTextType.setTextColor(textColor);
        mTextLevel.setTextColor(textColor);
    }
}
