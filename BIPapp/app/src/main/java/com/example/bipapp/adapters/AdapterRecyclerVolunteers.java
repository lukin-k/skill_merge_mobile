package com.example.bipapp.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.view_holders.ViewHolderMiniUsers;
import com.example.bipapp.client.ClientMain;

public class AdapterRecyclerVolunteers extends AdapterRecyclerParticipants {
    public AdapterRecyclerVolunteers(boolean isInitiator, String projectId) {
        super(isInitiator, projectId);
    }

    @NonNull
    @Override
    public ViewHolderMiniUsers onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_mini_user, viewGroup, false);
        ViewHolderMiniUsers viewHolderMiniUsers = new ViewHolderMiniUsers(view, isInitiator) {
            @Override
            protected void onClickPositive() {
                ClientMain.getClient().acceptVolunteer(mProjectId, mUser.getUserName());
            }

            @Override
            protected void onClickNegative() {
                ClientMain.getClient().deleteVolunteer(mProjectId, mUser.getUserName());
            }
        };

        if (isInitiator) {
            viewHolderMiniUsers.setVisiblePositive();
            viewHolderMiniUsers.setTextPositive(view.getContext().getResources().getString(R.string.button_accept));
            viewHolderMiniUsers.setVisibleNegative();
            viewHolderMiniUsers.setTextNegative(view.getContext().getResources().getString(R.string.button_delete));
        }
        return viewHolderMiniUsers;
    }
}
