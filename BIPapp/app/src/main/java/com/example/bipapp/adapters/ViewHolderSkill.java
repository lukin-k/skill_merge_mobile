package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.models.Skill;

public abstract class ViewHolderSkill extends RecyclerView.ViewHolder {
    private TextView mTextType;
    private TextView mTextLevel;
    protected View mView;

    public ViewHolderSkill(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
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


    public void onBind(Skill skill) {
        mTextType.setText(skill.getType());
        mTextLevel.setText(skill.getLevel());
    }
}
