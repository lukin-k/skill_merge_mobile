package com.example.bipapp.adapters;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bipapp.R;
import com.example.bipapp.models.Project;
import com.example.bipapp.models.User;
import com.example.bipapp.ui.FragmentRecyclerAll;

import java.util.ArrayList;

public class AdapterPagerSubscribers extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragments;
    private final ArrayList<String> mFragmentTitles;
    private AdapterRecyclerSubscriptions mAdapterRecyclerSubscriptions;
    private AdapterRecyclerSubscribers mAdapterRecyclerSubscribers;

    public AdapterPagerSubscribers(FragmentManager fragmentManager, Resources resources) {
        super(fragmentManager);
        mFragments = new ArrayList<>();
        mFragmentTitles = new ArrayList<>();
        createFragments(resources);
    }

    private void createFragments(Resources resources) {
        FragmentRecyclerAll fragmentRecyclerAll = new FragmentRecyclerAll();
        mAdapterRecyclerSubscribers = new AdapterRecyclerSubscribers();
        fragmentRecyclerAll.setAdapterRecycler(mAdapterRecyclerSubscribers);
        mFragments.add(fragmentRecyclerAll);
        mFragmentTitles.add(resources.getString(R.string.title_subscribers));

        fragmentRecyclerAll = new FragmentRecyclerAll();
        mAdapterRecyclerSubscriptions = new AdapterRecyclerSubscriptions();
        fragmentRecyclerAll.setAdapterRecycler(mAdapterRecyclerSubscriptions);
        mFragments.add(fragmentRecyclerAll);
        mFragmentTitles.add(resources.getString(R.string.title_subscriptions));
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public void setSubscriptions(ArrayList<Project> subscriptions) {
        mAdapterRecyclerSubscriptions.setProjects(subscriptions);
        mAdapterRecyclerSubscriptions.notifyDataSetChanged();
    }

    public void setSubscribers(ArrayList<Project> subscribersProjects, ArrayList<User> subscribers) {
        mAdapterRecyclerSubscribers.setSubscribers(subscribersProjects, subscribers);
        mAdapterRecyclerSubscribers.notifyDataSetChanged();
    }
}
