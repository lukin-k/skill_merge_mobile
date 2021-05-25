package com.example.bipapp.adapters.view_holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerSkillsNonSelected;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

public abstract class ViewHolderProject extends RecyclerView.ViewHolder {
    private final TextView mTextName;
    private final TextView mTextTag;
    private final ImageView mImageInitiatorPhoto;
    private final TextView mTextInitiatorFullname;
    private final AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;


    public ViewHolderProject(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProject();
            }
        });

        mTextName = itemView.findViewById(R.id.text_project_name);
        mTextTag = itemView.findViewById(R.id.text_project_tag);
        mImageInitiatorPhoto = itemView.findViewById(R.id.image_initiator_photo);
        mTextInitiatorFullname = itemView.findViewById(R.id.text_initiator_fullname);

        View view = itemView.findViewById(R.id.project_skills);
        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);
    }

    abstract public void onClickProject();


    public void onBind(Project project, String userName) {
        mTextName.setText(project.getName());
        setInitiator(project.getInitiator());
        mAdapterRecyclerSkills.setSkills(project.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
        mTextTag.setText(project.getTag());
    }

    private void setInitiator(User initiator) {
        mImageInitiatorPhoto.setImageBitmap(initiator.getPhoto());
        mTextInitiatorFullname.setText(initiator.getFullName());
    }
}
