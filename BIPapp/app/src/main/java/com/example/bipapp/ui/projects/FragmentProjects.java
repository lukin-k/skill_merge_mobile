package com.example.bipapp.ui.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.ui.IFragmentHost;

import java.util.List;

//TODO replace fab to projects fragment
public class FragmentProjects extends Fragment implements IFragmentHost {
    private ClientMain mClient;
    private FragmentShowProjects mFragmentShowProjects;
    private FragmentManager mFragmentManager;
    private FloatingActionButton mFab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        mClient = ClientMain.getClient();

        mFragmentShowProjects = new FragmentShowProjects();
        mFragmentManager = getChildFragmentManager();

        mFab = view.findViewById(R.id.fab_create_project);
        mFab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                FragmentProjectCreate fragmentProjectCreate = new FragmentProjectCreate();
                mFab.setVisibility(View.GONE);

                mFragmentManager.beginTransaction()
                        .add(R.id.frame_container_projects, fragmentProjectCreate)
                        .commit();
            }
        });

        setFragmentShowProjects();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mClient.isSearch()) {
            showProjects();
        } else {
            mClient.getMyProjects();
        }
    }

    public void showProjects() {
        if (!isShowOnTop()) {
            setFragmentShowProjects();
        }
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                mFab.setVisibility(View.VISIBLE);
                mFragmentShowProjects.showProjects();
            }
        });
    }

    public void showProjectInfo(Project project) {
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                mFab.setVisibility(View.GONE);
            }
        });
        FragmentProjectInfo fragmentProjectInfo = new FragmentProjectInfo();
        fragmentProjectInfo.setProject(project);

        mFragmentManager.beginTransaction()
                .add(R.id.frame_container_projects, fragmentProjectInfo)
                .commit();
    }

    @Override
    public boolean popLastFragment() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments.size() > 0) {
            Fragment fragment = fragments.get(fragments.size() - 1);
            mFragmentManager.beginTransaction().remove(fragment).commit();
            return true;
        }

        return false;
    }

    private boolean isShowOnTop() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments.size() > 0) {
            Fragment fragment = fragments.get(fragments.size() - 1);
            if (fragment instanceof FragmentShowProjects) {
                return true;
            }
        }
        return false;
    }

    private void setFragmentShowProjects() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof FragmentShowProjects) {
                return;
            }
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }

        mFragmentManager.beginTransaction()
                .replace(R.id.frame_container_projects, mFragmentShowProjects)
                .commit();
    }


}