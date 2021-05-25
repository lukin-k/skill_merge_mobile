package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderSubscription;
import com.example.bipapp.models.Project;

import java.util.ArrayList;

public class AdapterRecyclerSubscriptions extends RecyclerView.Adapter<ViewHolderSubscription> {
    private ArrayList<Project> mProjects;

    public void setProjects(ArrayList<Project> projects) {
        mProjects = projects;
    }

    public AdapterRecyclerSubscriptions() {
        mProjects = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderSubscription onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_subscriptions, viewGroup, false);
        ViewHolderSubscription viewHolderSubscription = new ViewHolderSubscription(view);
        return viewHolderSubscription;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSubscription viewHolderSubscription, int i) {
        viewHolderSubscription.onBind(mProjects.get(i));
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }
}
