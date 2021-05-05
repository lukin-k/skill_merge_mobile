package com.example.bipapp.ui.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipapp.MainActivity;
import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;

public class FragmentUser extends Fragment {
    //TODO add refresh layout to get new user info
    private ClientMain mClient;
    private FragmentUserInfo mFragmentUserInfo;
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mClient = ((MainActivity) getActivity()).getClientMain();

        mFragmentUserInfo = new FragmentUserInfo();
        mFragmentUserInfo.setClient(mClient);
        mFragmentManager = getChildFragmentManager();

        //TODO gone fab when edit and visible when info
        FloatingActionButton fab = view.findViewById(R.id.fab_edit_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUserEdit fragmentUserEdit = new FragmentUserEdit();
                fragmentUserEdit.setClient(mClient);

                mFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_user, fragmentUserEdit)
                        .commit();
            }
        });

        return view;
    }

    private void setFragmentUserInfo() {
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