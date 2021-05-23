package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerParticipants;
import com.example.bipapp.adapters.AdapterRecyclerVolunteers;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;

import java.util.ArrayList;

public class FragmentRecyclerMiniUsers extends Fragment {
    private AdapterRecyclerParticipants mAdapterRecycler;

    public void setAdapterRecycler(AdapterRecyclerParticipants adapterRecycler) {
        mAdapterRecycler = adapterRecycler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recycler_mini_users, container, false);

        RecyclerView mRecycler = rootView.findViewById(R.id.recycler_mini_users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapterRecycler);

        return rootView;
    }
}

