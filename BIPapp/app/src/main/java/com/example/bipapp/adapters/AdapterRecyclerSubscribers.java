package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderSubscriber;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

import java.util.ArrayList;

public class AdapterRecyclerSubscribers extends RecyclerView.Adapter<ViewHolderSubscriber> {
    private ArrayList<Project> mSubscribersProjects;
    private ArrayList<User> mSubscribers;

    public AdapterRecyclerSubscribers() {
        mSubscribers = new ArrayList<>();
        mSubscribersProjects = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderSubscriber onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_subscribers, viewGroup, false);
        ViewHolderSubscriber viewHolderSubscriber = new ViewHolderSubscriber(view);
        return viewHolderSubscriber;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSubscriber viewHolderSubscriber, int i) {
        viewHolderSubscriber.onBind(mSubscribersProjects.get(i), mSubscribers.get(i));
    }

    @Override
    public int getItemCount() {
        return mSubscribers.size();
    }

    public void setSubscribers(ArrayList<Project> subscribersProjects, ArrayList<User> subscribers) {
        mSubscribersProjects = subscribersProjects;
        mSubscribers = subscribers;
    }
}
