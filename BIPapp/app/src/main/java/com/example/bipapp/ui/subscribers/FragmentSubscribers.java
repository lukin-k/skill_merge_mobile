package com.example.bipapp.ui.subscribers;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterPagerMiniUsers;
import com.example.bipapp.adapters.AdapterPagerSubscribers;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;
import com.example.bipapp.ui.IFragmentHost;

import java.util.ArrayList;

public class FragmentSubscribers extends Fragment implements IFragmentHost {
    private ClientMain mClient;
    private AdapterPagerSubscribers mAdapterPagerSubscribers;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_subscribers, container, false);

        mAdapterPagerSubscribers = new AdapterPagerSubscribers(getChildFragmentManager(), getResources());
        ViewPager viewPager = view.findViewById(R.id.pager_subscribers);
        viewPager.setAdapter(mAdapterPagerSubscribers);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mClient.getSubscribers();
    }

    public void showSubscribers(ArrayList<Project> subscribersProjects, ArrayList<User> subscribers, ArrayList<Project> subscriptions){
        View view = getView();
//        mAdapterPagerSubscribers = new AdapterPagerSubscribers(getChildFragmentManager(), getResources(), subscribersProjects, subscribers);
//        ViewPager viewPager = view.findViewById(R.id.pager_subscribers);
//        AdapterPagerMiniUsers adapterPager = new AdapterPagerMiniUsers(getChildFragmentManager(), getResources(), subscribersProjects.get(0));
//        ViewPager viewPager = view.findViewById(R.id.pager_mini_users);
//        viewPager.setAdapter(adapterPager);
//        viewPager.setAdapter(mAdapterPagerSubscribers);
        mAdapterPagerSubscribers.notifyDataSetChanged();
        mAdapterPagerSubscribers.setSubscribers(subscribersProjects, subscribers);
        mAdapterPagerSubscribers.setSubscriptions(subscriptions);
        mAdapterPagerSubscribers.notifyDataSetChanged();
    }

    @Override
    public boolean popLastFragment() {
        return false;
    }
}