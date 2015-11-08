package com.example.laurenfalzarano.shots.Application;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by laurenfalzarano on 11/7/15.
 */
public class MyApplication extends Application {

    private static Context context;
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.context = getApplicationContext();

        // Initialize font awesome
        Iconify.with(new FontAwesomeModule());

    }

    public static MyApplication get() {
        return instance;
    }

    public static Context getAppContext() {
        return context;
    }
}
