package com.example.bipapp.ui.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterPagerMiniUsers;
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
    private AdapterRecyclerSkillsNonSelected mAdapterRecyclerSkills;

    public void setProject(Project project) {
        mProject = project;
    }


    public FragmentProjectInfo() {
        mClient = ClientMain.getClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_info, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        AdapterPagerMiniUsers adapterPager = new AdapterPagerMiniUsers(getChildFragmentManager(), getResources(), mProject);
        ViewPager viewPager = view.findViewById(R.id.pager_mini_users);
        viewPager.setAdapter(adapterPager);

        View panelSkills = view.findViewById(R.id.project_skills);
        RecyclerView recyclerSkills = panelSkills.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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
        textName.setText(mProject.getName());

        TextView textDescription = view.findViewById(R.id.text_project_description);
        textDescription.setText(mProject.getDescription());

        TextView textTag = view.findViewById(R.id.text_project_tag);
        textTag.setText(mProject.getTag());

        setInitiator(mProject.getInitiator());

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

    private void setInitiator(User initiator) {
        View view = getView();

        ImageView imageInitiatorPhoto = view.findViewById(R.id.image_initiator_photo);
        imageInitiatorPhoto.setImageBitmap(initiator.getPhoto());

        TextView textInitiatorFullname = view.findViewById(R.id.text_initiator_fullname);
        textInitiatorFullname.setText(initiator.getFullName());
    }

    @SuppressLint("RestrictedApi")
    private void showInitiatorsData(View view) {
        FloatingActionButton fabUpdate = view.findViewById(R.id.fab_update_project);
        fabUpdate.setVisibility(View.VISIBLE);
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.showProjectUpdate(mProject);
            }
        });
    }
}