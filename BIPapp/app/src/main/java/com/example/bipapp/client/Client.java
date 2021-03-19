package com.example.bipapp.client;

import android.util.Log;

//create connection, receive/sent decrypt/encrypt byte from/to server, use API
public class Client extends Thread {

    public Client() {
        start();
    }

    @Override
    public void run() {
        //TODO send/receive, lists to send/receive packets

        for (int i = 0; i < 10; ++i) {
            Log.v(getClass().getName(), "i = " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
