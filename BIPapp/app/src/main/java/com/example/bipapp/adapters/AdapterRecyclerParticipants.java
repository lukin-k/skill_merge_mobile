package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderMiniUsers;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.User;

import java.util.ArrayList;

public class AdapterRecyclerParticipants extends RecyclerView.Adapter<ViewHolderMiniUsers> {
    private ArrayList<User> mUsers;
    protected boolean isInitiator;
    protected String mProjectId;

    public void setUsers(ArrayList<User> users) {
        mUsers = users;
    }

    public AdapterRecyclerParticipants(boolean isInitiator, String projectId) {
        mUsers = new ArrayList<>();
        this.isInitiator = isInitiator;
        mProjectId = projectId;
    }

    @NonNull
    @Override
    public ViewHolderMiniUsers onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_mini_user, viewGroup, false);
        ViewHolderMiniUsers viewHolderMiniUsers = new ViewHolderMiniUsers(view) {
            @Override
            protected void onClickPositive() {
                Log.v("Participant", "Positive");
            }

            @Override
            protected void onClickNegative() {
                ClientMain.getClient().deleteParticipant(mProjectId, mUser.getUserName());
            }
        };
        if (isInitiator) {
            viewHolderMiniUsers.setVisibleNegative();
            viewHolderMiniUsers.setTextNegative(view.getContext().getResources().getString(R.string.button_delete));
        }
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
