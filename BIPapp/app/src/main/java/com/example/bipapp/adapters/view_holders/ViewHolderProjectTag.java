package com.example.bipapp.adapters.view_holders;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bipapp.R;

public abstract class ViewHolderProjectTag extends RecyclerView.ViewHolder {
    private final TextView mTextTag;

    public ViewHolderProjectTag(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProjectTag();
            }
        });

        mTextTag = itemView.findViewById(R.id.text_project_tag);
    }

    abstract public void onClickProjectTag();


    public void onBind(String tag, boolean isSelected) {
        mTextTag.setText(tag);
        changeColor(isSelected);
    }

    @SuppressLint("ResourceAsColor")
    public void changeColor(boolean isSelected) {
        int color = itemView.getResources().getColor(R.color.project_tag_not_selected);
        int textColor = itemView.getResources().getColor(R.color.deep_pipe_green);
        if (isSelected) {
            color = itemView.getResources().getColor(R.color.project_tag_selected);
            textColor = itemView.getResources().getColor(R.color.orange);
        }
        itemView.setBackgroundColor(color);
        mTextTag.setTextColor(textColor);
    }
}
