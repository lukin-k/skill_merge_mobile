package com.example.bipapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bipapp.api.API;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.client.IClientMainCallback;
import com.example.bipapp.models.Project;
import com.example.bipapp.ui.IFragmentHost;
import com.example.bipapp.ui.projects.FragmentProjects;
import com.example.bipapp.ui.user.FragmentUser;


//TODO add subscribersFragment
public class MainActivity extends AppCompatActivity implements IClientMainCallback {
    private ClientMain mClient;

    private View mViewNavigationProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(API.PREFERENCES_NAME, Context.MODE_PRIVATE);
        API.setPreferences(preferences);

        ClientMain.createClient(this);
        mClient = ClientMain.getClient();
        if (mClient == null) {
            Log.e("ClientMain", "No IClientMainCallback");
            finish();
        }
        mClient.getAllSkillsRequest();
        mClient.getAllProjectTagsRequest();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mViewNavigationProjects = navView.findViewById(R.id.navigation_projects);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search_project, R.id.navigation_subscriptions, R.id.navigation_projects, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.stopClient();
    }

    @Override
    public void showMessage(String title, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private Fragment getNowFragment() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        return fragment;
    }

    @Override
    public void showUserInfo() {
        Fragment fragment = getNowFragment();
        if (fragment instanceof FragmentUser) {
            ((FragmentUser) fragment).showUserInfo();
        }
    }

    @Override
    public void showSearchResult() {
        Fragment fragment = getNowFragment();
        if (fragment instanceof FragmentProjects) {
            ((FragmentProjects) fragment).showProjects();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mViewNavigationProjects.performClick();
                }
            });
        }
    }

    @Override
    public void showProject(Project project) {
        Fragment fragment = getNowFragment();
        if (fragment instanceof FragmentProjects) {
            ((FragmentProjects) fragment).showProjectInfo(project);
        }
    }

    @Override
    public void showUserEdit() {
        Fragment fragment = getNowFragment();
        if (fragment instanceof FragmentUser) {
            ((FragmentUser) fragment).showUserEdit();
        }
    }

    @Override
    public void showProjectCreate() {
        Fragment fragment = getNowFragment();
        if (fragment instanceof FragmentProjects) {
            ((FragmentProjects) fragment).showProjectCreate();
        }
    }

    @Override
    public void showProjectUpdate(Project project) {
        Fragment fragment = getNowFragment();
        if (fragment instanceof FragmentProjects) {
            ((FragmentProjects) fragment).showProjectUpdate(project);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getNowFragment();
        if (fragment instanceof IFragmentHost) {
            IFragmentHost fragmentHost = (IFragmentHost) fragment;
            if (!fragmentHost.popLastFragment()) {
                //TODO back onece request exit? second go to singin activity
                finish();
            }
        }
    }
}