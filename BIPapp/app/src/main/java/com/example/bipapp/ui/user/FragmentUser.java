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

//TODO back onece request exit? second go to singin activity
public class FragmentUser extends Fragment {
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
                        .replace(R.id.frame_container_user, fragmentUserEdit)
                        .commit();
            }
        });

        return view;
    }

    private void setFragmentUserInfo() {
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                mFab.setVisibility(View.VISIBLE);
            }
        });
        mFragmentManager.beginTransaction()
                .replace(R.id.frame_container_user, mFragmentUserInfo)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mClient.isUserExist()) {
            mClient.getUserInfo();
        } else {
            showUserInfo();
        }
    }

    public void showUserInfo() {
        setFragmentUserInfo();
    }
}