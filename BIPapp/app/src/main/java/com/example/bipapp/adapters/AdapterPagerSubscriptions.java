package com.example.bipapp.adapters;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bipapp.R;
import com.example.bipapp.models.Project;
import com.example.bipapp.ui.FragmentRecyclerAll;

import java.util.ArrayList;

public class AdapterPagerSubscriptions extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragments;
    private final ArrayList<String> mFragmentTitles;
    private ArrayList<Project> mSubscriptions;
    private AdapterRecyclerSubscriptions mAdapterRecyclerSubscriptions;

    public AdapterPagerSubscriptions(FragmentManager fragmentManager, Resources resources) {
        super(fragmentManager);
        mFragments = new ArrayList<>();
        mFragmentTitles = new ArrayList<>();
        mSubscriptions = new ArrayList<>();
        createFragments(fragmentManager, resources);
    }

    private void createFragments(FragmentManager fragmentManager, Resources resources) {
        FragmentRecyclerAll fragmentRecyclerAll = new FragmentRecyclerAll();

        //TODO fragmentRecyclerAll.setAdapterRecycler(adapterRecyclerSubscribers);
        mFragments.add(fragmentRecyclerAll);
        mFragmentTitles.add(resources.getString(R.string.title_subscribers));

        fragmentRecyclerAll = new FragmentRecyclerAll();
        mAdapterRecyclerSubscriptions = new AdapterRecyclerSubscriptions();
        mAdapterRecyclerSubscriptions.setFragmentManager(fragmentManager);
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

    public void setSubscriptions(ArrayList<Project> projects) {
        mAdapterRecyclerSubscriptions.setProjects(mSubscriptions);
        mAdapterRecyclerSubscriptions.notifyDataSetChanged();
    }

}
