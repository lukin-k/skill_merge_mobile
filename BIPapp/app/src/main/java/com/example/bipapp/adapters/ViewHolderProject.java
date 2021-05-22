package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

public abstract class ViewHolderProject extends RecyclerView.ViewHolder {
    private final TextView mTextName;
    private final TextView mTextDescription;
    private final View mViewInitiator;
    private final AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;
    private final TextView mTextTag;


    public ViewHolderProject(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProject();
            }
        });

        mTextName = itemView.findViewById(R.id.text_project_name);
        mTextDescription = itemView.findViewById(R.id.text_project_description);
        mViewInitiator = itemView.findViewById(R.id.project_initiator);
        mTextTag = itemView.findViewById(R.id.text_project_tag);
        
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
        mTextDescription.setText(project.getDescription());
        setInitiator(project.getInitiator());
        mAdapterRecyclerSkills.setSkills(project.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
        mTextTag.setText(project.getTag());
    }

    private void setInitiator(User initiator) {
        //TODO set all fields
        TextView textFullname = mViewInitiator.findViewById(R.id.text_fullname);

        textFullname.setText(initiator.getFullName());
    }
}
