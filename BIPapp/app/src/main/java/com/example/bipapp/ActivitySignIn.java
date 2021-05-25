package com.example.bipapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bipapp.api.API;
import com.example.bipapp.client.ClientSign;
import com.example.bipapp.client.IClientSignCallback;


public class ActivitySignIn extends AppCompatActivity implements IClientSignCallback {
    private ClientSign mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SharedPreferences preferences = getSharedPreferences(API.PREFERENCES_NAME, Context.MODE_PRIVATE);
        API.setPreferences(preferences);

        EditText editLogin = findViewById(R.id.edit_login);
        EditText editPassword = findViewById(R.id.edit_password);

        Button buttonSignUp = findViewById(R.id.button_sign_up);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignIn.this);
                LayoutInflater layoutInflater = ActivitySignIn.this.getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.dialog_email, null);
                EditText editEmail = view.findViewById(R.id.edit_email);

                builder.setTitle(getResources().getString(R.string.hint_email))
                        .setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mClient.signUp(editLogin.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        Button buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.signIn(editLogin.getText().toString(), editPassword.getText().toString());
            }
        });


        if (API.isTokenExist()) {
            if (API.isTokenActive()) {
                signIn();
            } else {
                if (!API.refreshToken()) {
                    Toast.makeText(this, R.string.bad_token, Toast.LENGTH_LONG).show();
                } else {
                    signIn();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ClientSign.createClient(this);
        try {
            mClient = ClientSign.getClient();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignIn.this);
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
    public void signIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void signUp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignIn.this);
                builder.setTitle(getResources().getString(R.string.title_email))
                        .setMessage(getResources().getString(R.string.message_email))
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
}