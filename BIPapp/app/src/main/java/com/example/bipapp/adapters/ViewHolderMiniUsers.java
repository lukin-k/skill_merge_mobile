package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.models.User;

public abstract class ViewHolderMiniUsers extends RecyclerView.ViewHolder {
    private ImageView mImagePhoto;
    private TextView mTextFullname;
    private TextView mTextAge;
    private AdapterRecyclerSkills mAdapterRecyclerSkills;

    public ViewHolderMiniUsers(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUser();
            }
        });

        mImagePhoto = itemView.findViewById(R.id.image_photo);
        mTextFullname = itemView.findViewById(R.id.text_fullname);
        mTextAge = itemView.findViewById(R.id.text_age);

        RecyclerView recyclerSkills = itemView.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkills();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);
    }

    abstract public void onClickUser();

    public void onBind(User user) {
        mTextFullname.setText(user.getFullName());
        mTextAge.setText("" + user.getAge());
        mAdapterRecyclerSkills.setSkills(user.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
    }
}
