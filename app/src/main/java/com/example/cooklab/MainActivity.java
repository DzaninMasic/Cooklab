package com.example.cooklab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force Light theme.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(1800);
        animationDrawable.start();
    }

    public boolean onTouchEvent(MotionEvent touchEvent){

        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "onTouchEvent: down");
                x1=touchEvent.getX();
                y1=touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "onTouchEvent: up");
                x2=touchEvent.getX();
                y2=touchEvent.getY();
                if((y2<y1 && x1<x2) || (y2<y1 && x1>x2)){
                    Intent intent=new Intent(MainActivity.this,LoginScreen.class);
                    startActivity(intent);
                }
                break;
        }
        return false;
    }

}