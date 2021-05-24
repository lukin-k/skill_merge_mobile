package com.example.bipapp.ui.subscriptions;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterPagerSubscriptions;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.ui.IFragmentHost;

import java.util.ArrayList;

public class FragmentSubscriptions extends Fragment implements IFragmentHost {
    private ClientMain mClient;
    private AdapterPagerSubscriptions mAdapterPagerSubscriptions;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);

        mAdapterPagerSubscriptions = new AdapterPagerSubscriptions(getChildFragmentManager(), getResources());
        ViewPager viewPager = view.findViewById(R.id.pager_subscriptions);
        viewPager.setAdapter(mAdapterPagerSubscriptions);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mClient.getSubscribers();
    }

    //TODO change name

    public void showSubscribers(ArrayList<Project> projects){
        mAdapterPagerSubscriptions.setSubscriptions(projects);
        mAdapterPagerSubscriptions.notifyDataSetChanged();
    }

    @Override
    public boolean popLastFragment() {
        return false;
    }
}