package com.example.bipapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bipapp.api.API;
import com.example.bipapp.client.Client;
import com.example.bipapp.client.IClientCallback;

public class ActivitySingIn extends AppCompatActivity implements IClientCallback {
    private Client mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        SharedPreferences preferences = getSharedPreferences(API.PREFERENCES_NAME, Context.MODE_PRIVATE);
        API.setPreferences(preferences);

        mClient = new Client(this);

        EditText editLogin = findViewById(R.id.edit_login);
        EditText editPassword = findViewById(R.id.edit_password);

        Button buttonSingUp = findViewById(R.id.button_sing_up);

        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.singUp(editLogin.getText().toString(), editPassword.getText().toString());
            }
        });

        Button buttonSingIn = findViewById(R.id.button_sing_in);
        buttonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.singIn(editLogin.getText().toString(), editPassword.getText().toString());
            }
        });


        //TODO
//        if token exist
        //        then check active maybe refresh
        //        if check then go mainActivity
        //        else show error dialog

    }

    @Override
    public void showMessage(String title, String message) {
//        Log.v(title, message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    @Override
    public void singIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}