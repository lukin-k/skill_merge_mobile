package com.example.bipapp.ui.search_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bipapp.R;

//TODO implentation search

public class FragmentSearchProject extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_project, container, false);
        TextView textView = view.findViewById(R.id.text_home);
        textView.setText("This is home fragment");

        return view;
    }
}