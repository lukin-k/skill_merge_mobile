package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

//TODO https://www.fandroid.info/android-uchebnik-sozdanie-recyclerview-cardview-i-menyu-optsij-dlya-elementa-recyclerview/

public abstract class ViewHolderProject extends RecyclerView.ViewHolder {
    private TextView mTextName;
    private TextView mTextDescription;
    private View mViewInitiator;
    private AdapterRecyclerMiniUsers mAdapterRecyclerMiniUsers;
    private AdapterRecyclerSkills mAdapterRecyclerSkills;
    private TextView mTextTag;


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

        RecyclerView recyclerParticipants = itemView.findViewById(R.id.recycler_project_participants);
        recyclerParticipants.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerParticipants.setLayoutManager(layoutManager);
        mAdapterRecyclerMiniUsers = new AdapterRecyclerMiniUsers();
        recyclerParticipants.setAdapter(mAdapterRecyclerMiniUsers);

        View view = itemView.findViewById(R.id.project_skills);
        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkills();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);
    }

    abstract public void onClickProject();


    public void onBind(Project project) {
        mTextName.setText(project.getName());
        mTextDescription.setText(project.getDescription());
        setInitiator(project.getInitiator());
        mAdapterRecyclerMiniUsers.setUsers(project.getParticipants());
        mAdapterRecyclerMiniUsers.notifyDataSetChanged();
        mAdapterRecyclerSkills.setSkills(project.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();

        mTextTag.setText(project.getTag());
    }

    private void setInitiator(User initiator) {
        //TODO set all fields
        ((TextView) mViewInitiator.findViewById(R.id.text_fullname)).setText(initiator.getFullName());
    }
}
