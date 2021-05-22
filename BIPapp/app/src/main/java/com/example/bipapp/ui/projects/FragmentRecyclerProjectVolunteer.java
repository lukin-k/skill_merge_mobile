package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerVolunteers;
import com.example.bipapp.models.Project;

public class FragmentRecyclerProjectVolunteer extends Fragment {

    private boolean mIsInitiator;
    private Project mProject;

    FragmentRecyclerProjectVolunteer(boolean isInitiator, Project project)
    {
        mIsInitiator = isInitiator;
        mProject = project;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recycler_project_volunteer, container, false);

        RecyclerView mRecyclerVolunteer = rootView.findViewById(R.id.recycler_project_volunteer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerVolunteer.setLayoutManager(layoutManager);
        AdapterRecyclerVolunteers mAdapterRecyclerVolunteers = new AdapterRecyclerVolunteers(mIsInitiator, mProject.getId());
        mRecyclerVolunteer.setAdapter(mAdapterRecyclerVolunteers);
        mAdapterRecyclerVolunteers.setUsers(mProject.getVolunteer());
        mAdapterRecyclerVolunteers.notifyDataSetChanged();

        return rootView;
    }
}

