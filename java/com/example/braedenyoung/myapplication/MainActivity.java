package com.example.braedenyoung.myapplication;


import android.os.Bundle;
import android.util.Log;

import android.app.Activity;


public class MainActivity extends Activity {


    private static final String DEBUG_TAG = "Gestures";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEBUG_TAG,"Activity");
        setContentView(R.layout.activity_fullscreen);
    }
}
