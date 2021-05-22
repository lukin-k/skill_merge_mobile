package com.example.bipapp.ui.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.ui.IFragmentHost;

import java.util.List;

public class FragmentUser extends Fragment implements IFragmentHost {
    //TODO add refresh layout to get new user info
    private ClientMain mClient;
    private FragmentUserInfo mFragmentUserInfo;
    private FragmentManager mFragmentManager;
    private FloatingActionButton mFab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mClient = ClientMain.getClient();

        mFragmentUserInfo = new FragmentUserInfo();
        mFragmentManager = getChildFragmentManager();

        mFab = view.findViewById(R.id.fab_edit_user);
        mFab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                FragmentUserEdit fragmentUserEdit = new FragmentUserEdit();
                mFab.setVisibility(View.GONE);

                mFragmentManager.beginTransaction()
                        .add(R.id.frame_container_user, fragmentUserEdit)
                        .commit();
            }
        });

        mFragmentManager.beginTransaction()
                .replace(R.id.frame_container_user, mFragmentUserInfo)
                .commit();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!mClient.isUserExist()) {
            mClient.getUserInfo();
        } else {
            if (!isEditOnTop()) {
                showUserInfo();
            }
        }
    }

    public void showUserInfo() {
        if (isEditOnTop()) {
            popLastFragment();
        }
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                mFab.setVisibility(View.VISIBLE);
                mFragmentUserInfo.showUserInfo();
            }
        });
    }

    @Override
    public boolean popLastFragment() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments.size() > 0) {
            Fragment fragment = fragments.get(fragments.size() - 1);
            mFragmentManager.beginTransaction().remove(fragment).commit();
            if (fragments.size() >= 2) {
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void run() {
                        mFab.setVisibility(View.VISIBLE);
                    }
                });
            }
            return true;
        }

        return false;
    }

    private boolean isEditOnTop() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments.size() > 0) {
            Fragment fragment = fragments.get(fragments.size() - 1);
            if (fragment instanceof FragmentUserEdit) {
                return true;
            }
        }
        return false;
    }
}