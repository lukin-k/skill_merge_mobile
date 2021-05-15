package com.example.bipapp.ui.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerProjects;
import com.example.bipapp.client.ClientMain;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentProjects extends Fragment {
    private ClientMain mClient;
    private FragmentMyProjects mFragmentMyProjects;
    private FragmentManager mFragmentManager;
    private FloatingActionButton mFab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        mClient = ((MainActivity) getActivity()).getClientMain();


        mFragmentMyProjects = new FragmentMyProjects();
        mFragmentMyProjects.setClient(mClient);
        mFragmentManager = getChildFragmentManager();

        mFab = view.findViewById(R.id.fab_create_project);
        mFab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                FragmentProjectCreate fragmentProjectCreate = new FragmentProjectCreate();
                fragmentProjectCreate.setClient(mClient);
                mFab.setVisibility(View.GONE);

                mFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_projects, fragmentProjectCreate)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("initiator", mClient.getUser().getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClient.getMyProjects(jsonObject);
    }

    public void showProjects() {
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                mFab.setVisibility(View.VISIBLE);
            }
        });
        mFragmentManager.beginTransaction()
                .replace(R.id.frame_container_projects, mFragmentMyProjects)
                .commit();
    }
}