package com.example.laurenfalzarano.shots;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.laurenfalzarano.shots.Application.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by laurenfalzarano on 9/23/15.
 */
public class PreferencesManager {

    private static PreferencesManager instance;
    public SharedPreferences preferences;
    private List<String> shotsArray = new ArrayList<>();

    public static PreferencesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    public static synchronized PreferencesManager getSync() {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private PreferencesManager() {
        Context context = MyApplication.get().getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getShots(String name) {

        int count = preferences.getInt(name, 0);
        return count;
    }

    public void incrementShots(String name) {
        int count = getShots(name);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, ++count);
        editor.commit();
        System.out.println("Shots count after update: " + preferences.getInt(name, 0));

        updateSet(name);
    }

    public void decrementShot(String name) {
        int count = getShots(name);
        count--;
        preferences.edit().putInt(name, count).apply();
    }

    private void updateSet(String name) {
        //Retrieve the values
        Set<String> set = getSet();

        set.add(name);
        preferences.edit().putStringSet("shotsList", set).commit();
        System.out.println(preferences.getStringSet("shotsList", null));
    }

    public Set<String> getSet() {
        Set<String> set = preferences.getStringSet("shotsList", null);
        Set<String> newSet = new HashSet<>();

        if (set != null) {
            // Do deep copy
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                newSet.add(iter.next().toString());
            }
        }

        shotsArray.addAll(set);
        Collections.sort(shotsArray);

        return newSet;
    }

    public List<String> getShotsArray() {
        return shotsArray;
    }


}
