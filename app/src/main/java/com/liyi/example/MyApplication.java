package com.liyi.example;

import android.app.Application;

import com.liyi.sutils.utils.SUtils;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SUtils.initialize(this);
    }
}
