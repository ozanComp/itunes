package com.sol.itunes;

import android.app.Application;
import android.content.Context;

public class ITunesApp extends Application {
    private static ITunesApp instance;

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
