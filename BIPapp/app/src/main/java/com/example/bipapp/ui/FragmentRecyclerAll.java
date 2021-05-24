package com.example.bipapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;

public class FragmentRecyclerAll extends Fragment {
    private RecyclerView.Adapter mAdapterRecycler;

    public void setAdapterRecycler(RecyclerView.Adapter adapterRecycler) {
        mAdapterRecycler = adapterRecycler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recycler_all, container, false);

        RecyclerView mRecycler = rootView.findViewById(R.id.recycler_all);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapterRecycler);

        return rootView;
    }
}

