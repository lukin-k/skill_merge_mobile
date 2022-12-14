package com.example.bipapp.adapters;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Project;
import com.example.bipapp.ui.FragmentRecyclerAll;

import java.util.ArrayList;

public class AdapterPagerMiniUsers extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragments;
    private final ArrayList<String> mFragmentTitles;

    public AdapterPagerMiniUsers(FragmentManager fragmentManager, Resources resources, Project project) {
        super(fragmentManager);
        mFragments = new ArrayList<>();
        mFragmentTitles = new ArrayList<>();
        createFragments(resources, project);
    }

    private void createFragments(Resources resources, Project project) {
        boolean isInitiator = ClientMain.getClient().getUser().getUserName().equals(project.getInitiator().getUserName());

        FragmentRecyclerAll fragmentRecyclerAll = new FragmentRecyclerAll();
        AdapterRecyclerParticipants adapterRecyclerParticipants = new AdapterRecyclerParticipants(isInitiator, project.getId());
        adapterRecyclerParticipants.setUsers(project.getParticipants());
        fragmentRecyclerAll.setAdapterRecycler(adapterRecyclerParticipants);
        mFragments.add(fragmentRecyclerAll);
        mFragmentTitles.add(resources.getString(R.string.title_participants));

        if (isInitiator) {
            fragmentRecyclerAll = new FragmentRecyclerAll();
            AdapterRecyclerVolunteers adapterRecyclerVolunteers = new AdapterRecyclerVolunteers(isInitiator, project.getId());
            adapterRecyclerVolunteers.setUsers(project.getVolunteer());
            fragmentRecyclerAll.setAdapterRecycler(adapterRecyclerVolunteers);
            mFragments.add(fragmentRecyclerAll);
            mFragmentTitles.add(resources.getString(R.string.title_volunteer));
        }
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
}
