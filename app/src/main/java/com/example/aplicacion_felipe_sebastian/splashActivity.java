package com.example.aplicacion_felipe_sebastian;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class splashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //SystemClock.sleep(4000);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
             Intent i = new Intent(splashActivity.this,login.class);
             startActivity(i);
             finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea,5000);

    }//fin onCreate



}









