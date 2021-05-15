package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.models.User;

import java.util.ArrayList;

public class AdapterRecyclerMiniUsers extends RecyclerView.Adapter<ViewHolderMiniUsers> {
    private ArrayList<User> mUsers;

    public void setUsers(ArrayList<User> users) {
        mUsers = users;
    }

    public AdapterRecyclerMiniUsers() {
        mUsers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderMiniUsers onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_project, viewGroup, false);
        ViewHolderMiniUsers viewHolderMiniUsers = new ViewHolderMiniUsers(view) {
            @Override
            public void onClickUser() {
                int i = getAdapterPosition();
                Log.v("miniUser", "click " + i);
            }
        };
        return viewHolderMiniUsers;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMiniUsers viewHolderMiniUsers, int i) {
        viewHolderMiniUsers.onBind(mUsers.get(i));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
