package com.example.bipapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bipapp.api.API;
import com.example.bipapp.client.ClientSing;
import com.example.bipapp.client.IClientSingCallback;


public class ActivitySingIn extends AppCompatActivity implements IClientSingCallback {
    private ClientSing mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        SharedPreferences preferences = getSharedPreferences(API.PREFERENCES_NAME, Context.MODE_PRIVATE);
        API.setPreferences(preferences);

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


        if (API.isTokenExist()) {
            if (API.isTokenActive()) {
                singIn();
            } else {
                if (!API.refreshToken()) {
                    Toast.makeText(this, R.string.bad_token, Toast.LENGTH_LONG).show();
                } else {
                    singIn();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ClientSing.createClient(this);
        try {
            mClient = ClientSing.getClient();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySingIn.this);
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

    @Override
    public void singIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void singUp() {
        //TODO call create user data activity
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((Button) findViewById(R.id.button_sing_in)).performClick();
            }
        });
    }
}