package com.example.bipapp.ui.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerParticipants;
import com.example.bipapp.adapters.AdapterRecyclerSkillsNonSelected;
import com.example.bipapp.adapters.AdapterRecyclerVolunteers;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

import java.util.ArrayList;

public class FragmentProjectInfo extends Fragment {
    private final ClientMain mClient;
    private Project mProject;

    public void setProject(Project project) {
        mProject = project;
    }

    private AdapterRecyclerParticipants mAdapterRecyclerParticipants;
    private AdapterRecyclerVolunteers mAdapterRecyclerVolunteers;

    private RecyclerView mRecyclerVolunteer;
    private AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;

    public FragmentProjectInfo() {
        mClient = ClientMain.getClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_info, container, false);

        boolean isInitiator = mClient.getUser().getUserName().equals(mProject.getInitiator().getUserName());
        RecyclerView recyclerParticipants = view.findViewById(R.id.recycler_project_participants);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerParticipants.setLayoutManager(layoutManager);
        mAdapterRecyclerParticipants = new AdapterRecyclerParticipants(isInitiator, mProject.getId());
        recyclerParticipants.setAdapter(mAdapterRecyclerParticipants);

        mRecyclerVolunteer = view.findViewById(R.id.recycler_project_volunteer);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerVolunteer.setLayoutManager(layoutManager);
        mAdapterRecyclerVolunteers = new AdapterRecyclerVolunteers(isInitiator, mProject.getId());
        mRecyclerVolunteer.setAdapter(mAdapterRecyclerVolunteers);

        View panelSkills = view.findViewById(R.id.project_skills);
        RecyclerView recyclerSkills = panelSkills.findViewById(R.id.recycler_skills);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);
        mAdapterRecyclerSkills = new AdapterRecyclerSkillsNonSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showProject();
    }

    private void showProject() {
        String userName = mClient.getUser().getUserName();
        View view = getView();

        TextView textName = view.findViewById(R.id.text_project_name);
        TextView textDescription = view.findViewById(R.id.text_project_description);
        View viewInitiator = view.findViewById(R.id.project_initiator);
        TextView textTag = view.findViewById(R.id.text_project_tag);

        textName.setText(mProject.getName());
        textDescription.setText(mProject.getDescription());
        setInitiator(viewInitiator, mProject.getInitiator());
        mAdapterRecyclerParticipants.setUsers(mProject.getParticipants());
        mAdapterRecyclerParticipants.notifyDataSetChanged();

        Button buttonAction = view.findViewById(R.id.button_project_action);
        if (userName.equals(mProject.getInitiator().getUserName())) {
            showInitiatorsData(view);
            buttonAction = view.findViewById(R.id.button_project_action);
            buttonAction.setText(getResources().getText(R.string.button_delete));
            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClient.deleteProject(mProject.getId());
                }
            });
        } else if (isParticipant(userName)) {
            buttonAction.setText(getResources().getText(R.string.button_leave));
            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClient.leaveProject(mProject.getId());
                }
            });
        } else if (isVolunteer(userName)) {
            buttonAction.setText(getResources().getText(R.string.button_unsubscribe));
            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClient.unsubscribeProject(mProject.getId());
                }
            });
        } else {
            buttonAction.setText(getResources().getText(R.string.button_subscribe));
            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClient.subscribeProject(mProject.getId());
                }
            });
        }

        mAdapterRecyclerSkills.setSkills(mProject.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
        textTag.setText(mProject.getTag());
    }

    private boolean isVolunteer(String userName) {
        ArrayList<User> volunteers = mProject.getVolunteer();
        for (User volunteer : volunteers) {
            if (volunteer.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isParticipant(String userName) {
        ArrayList<User> participants = mProject.getParticipants();
        for (User participant : participants) {
            if (participant.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private void setInitiator(View viewInitiator, User initiator) {
        //TODO set all fields
        TextView textFullname = viewInitiator.findViewById(R.id.text_fullname);

        textFullname.setText(initiator.getFullName());
    }

    @SuppressLint("RestrictedApi")
    private void showInitiatorsData(View view) {
        mRecyclerVolunteer.setVisibility(View.VISIBLE);
        mAdapterRecyclerVolunteers.setUsers(mProject.getVolunteer());
        mAdapterRecyclerVolunteers.notifyDataSetChanged();

        FloatingActionButton fabUpdate = view.findViewById(R.id.fab_update_project);
        fabUpdate.setVisibility(View.VISIBLE);
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add update project
                Log.v("Project", "Update");
            }
        });
    }
}