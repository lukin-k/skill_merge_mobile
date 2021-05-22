package com.example.bipapp.ui.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bipapp.R;
import com.example.bipapp.client.ClientMain;

//TODO back onece request exit? second go to singin activity
public class FragmentUser extends Fragment {
    //TODO add refresh layout to get new user info
    private ClientMain mClient;
    private FragmentUserInfo mFragmentUserInfo;
    private FragmentManager mFragmentManager;
    private AlertDialog mRationaleAlertDialogue;
    private FloatingActionButton mFab;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlertDialog.Builder requestApprovementAlertDialogueBuilder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
                    requestApprovementAlertDialogueBuilder.setMessage("Great! Now you can edit your info if you want to.");
                    requestApprovementAlertDialogueBuilder.setCancelable(true);

                    requestApprovementAlertDialogueBuilder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mFab.callOnClick();
                                }
                            });
                    requestApprovementAlertDialogueBuilder.create().show();
                } else {
                    mRationaleAlertDialogue.show();
                }
                return;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mClient = ClientMain.getClient();

        mFragmentUserInfo = new FragmentUserInfo();
        mFragmentManager = getChildFragmentManager();

        AlertDialog.Builder rationaleAlertDialogueBuilder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        rationaleAlertDialogueBuilder.setMessage("The access to the phone's media is required for uploading photos and " +
                "other media for the user profile. Would you like to try again and grant this permission?");
        rationaleAlertDialogueBuilder.setCancelable(true);

        rationaleAlertDialogueBuilder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mFab.callOnClick();
                    }
                });

        rationaleAlertDialogueBuilder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        mRationaleAlertDialogue = rationaleAlertDialogueBuilder.create();

        mFab = view.findViewById(R.id.fab_edit_user);
        mFab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                else
                {
                    FragmentUserEdit fragmentUserEdit = new FragmentUserEdit();
                    mFab.setVisibility(View.GONE);

                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame_container_user, fragmentUserEdit)
                            .commit();
                }
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