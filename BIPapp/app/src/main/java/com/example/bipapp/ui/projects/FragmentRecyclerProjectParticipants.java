package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerParticipants;
import com.example.bipapp.models.Project;

public class FragmentRecyclerProjectParticipants extends Fragment {

    private boolean mIsInitiator;
    private Project mProject;

    FragmentRecyclerProjectParticipants(boolean isInitiator, Project project)
    {
        mIsInitiator = isInitiator;
        mProject = project;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recycler_project_participants, container, false);

        Log.v("participants", "onCreateView started");
        RecyclerView recyclerParticipants = rootView.findViewById(R.id.recycler_project_participants);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerParticipants.setLayoutManager(layoutManager);
        AdapterRecyclerParticipants mAdapterRecyclerParticipants = new AdapterRecyclerParticipants(mIsInitiator, mProject.getId());
        recyclerParticipants.setAdapter(mAdapterRecyclerParticipants);
        mAdapterRecyclerParticipants.setUsers(mProject.getParticipants());
        mAdapterRecyclerParticipants.notifyDataSetChanged();
        Log.v("participants", "onCreateView ended");

        return rootView;
    }
}

