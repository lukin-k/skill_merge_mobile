package com.example.bipapp.ui.user;

import android.os.Bundle;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mClient = ClientMain.getClient();

        mFragmentUserInfo = new FragmentUserInfo();
        mFragmentManager = getChildFragmentManager();

        setFragmentUserInfo();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!mClient.isUserExist()) {
            mClient.getUserInfo();
        } else {
            if (isInfoOnTop()) {
                showUserInfo();
            }
        }
    }

    public void showUserInfo() {
        setFragmentUserInfo();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
            return true;
        }
        return false;
    }

    private boolean isInfoOnTop() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments.size() > 0) {
            Fragment fragment = fragments.get(fragments.size() - 1);
            if (fragment instanceof FragmentUserInfo) {
                return true;
            }
        }
        return false;
    }

    public void showUserEdit() {
        FragmentUserEdit fragmentUserEdit = new FragmentUserEdit();
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container_user, fragmentUserEdit)
                .commit();
    }

    private void setFragmentUserInfo() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof FragmentUserInfo) {
                return;
            }
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }

        mFragmentManager.beginTransaction()
                .replace(R.id.frame_container_user, mFragmentUserInfo)
                .commit();
    }
}