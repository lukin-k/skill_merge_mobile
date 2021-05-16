package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerProjects;
import com.example.bipapp.client.ClientMain;


public class FragmentShowProjects extends Fragment {
    private ClientMain mClient;
    //TODO do new adapter
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
        View view = inflater.inflate(R.layout.fragment_show_projects, container, false);
        RecyclerView recyclerProjects = view.findViewById(R.id.recycler_projects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerProjects.setLayoutManager(layoutManager);
        mAdapterRecyclerProjects = new AdapterRecyclerProjects();
        recyclerProjects.setAdapter(mAdapterRecyclerProjects);

        Button buttonBack = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getChildFragmentManager().beginTransaction().remove(FragmentShowProjects.this).commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapterRecyclerProjects.setProjects(mClient.getFindProjects());
        mAdapterRecyclerProjects.notifyDataSetChanged();
    }
}