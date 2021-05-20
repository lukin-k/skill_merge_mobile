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


public class FragmentShowProjects extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerProjects mAdapterRecyclerProjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_show_projects, container, false);
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
        mAdapterRecyclerProjects.setUserName(mClient.getUser().getUserName());
        mAdapterRecyclerProjects.setProjects(mClient.getFindProjects());
        mAdapterRecyclerProjects.notifyDataSetChanged();
    }

//    buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getParentFragment().getChildFragmentManager().beginTransaction().remove(FragmentShowProjectsNotNeed.this).commit();
//            }
//        });

}