package com.example.bipapp.ui.projects;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentProjects extends Fragment {
    private ClientMain mClient;
    private FragmentManager mFragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        mClient = ((MainActivity) getActivity()).getClientMain();

        mFragmentManager = getChildFragmentManager();
        //TODO gone fab when create and visible when info
        FloatingActionButton fab = view.findViewById(R.id.fab_create_project);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentProjectCreate fragmentProjectCreate = new FragmentProjectCreate();
                fragmentProjectCreate.setClient(mClient);

                mFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_user, fragmentProjectCreate)
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
        for (Fragment fragment : mFragmentManager.getFragments()) {
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }
        //TODO show projects
        //TODO create adapter to projects
        //TODO create adapter to mini users
        mClient.getFindProjects();
        Log.v("Projs", "showProjects");
    }
}