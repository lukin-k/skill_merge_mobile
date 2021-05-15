package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;


import com.example.bipapp.models.Project;

import java.util.ArrayList;

public class AdapterRecyclerProjects extends RecyclerView.Adapter<ViewHolderProject> {
    private ArrayList<Project> mProjects;

    public void setProjects(ArrayList<Project> projects) {
        mProjects = projects;
    }

    public AdapterRecyclerProjects() {
        mProjects = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderProject onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_project, viewGroup, false);
        ViewHolderProject viewHolderProject = new ViewHolderProject(view) {
            @Override
            public void onClickProject() {
                int i = getAdapterPosition();
                Log.v("Project", "click " + i);
            }
        };
        return viewHolderProject;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProject viewHolderProject, int i) {
        viewHolderProject.onBind(mProjects.get(i));
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }
}
