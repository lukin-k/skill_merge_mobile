package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerProjects;
import com.example.bipapp.client.ClientMain;


public class FragmentMyProjects extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerProjects mAdapterRecyclerProjects;

    public void setClient(ClientMain client) {
        mClient = client;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_projects, container, false);
        RecyclerView recyclerProjects = view.findViewById(R.id.recycler_projects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerProjects.setLayoutManager(layoutManager);
        mAdapterRecyclerProjects = new AdapterRecyclerProjects();
        recyclerProjects.setAdapter(mAdapterRecyclerProjects);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showProjects();
    }

    public void showProjects() {
        mAdapterRecyclerProjects.setProjects(mClient.getFindProjects());
        mAdapterRecyclerProjects.notifyDataSetChanged();
    }
}