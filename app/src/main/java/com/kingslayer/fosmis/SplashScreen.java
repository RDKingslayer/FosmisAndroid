package com.kingslayer.fosmis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        Thread myThread = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(1500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                    if(mySharedPreferences.getString("user", "").equals(""))
                    {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(SplashScreen.this, NoticeActivity.class));
                    }

                    finish();
                }

            }
        };

        myThread.start();

    }
}
