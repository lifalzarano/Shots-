package com.example.laurenfalzarano.shots.Persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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
    private List<String> shotsArray;

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

    public void setShotsCount(String name, int count) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, count);
        editor.commit();
        System.out.println("Shots count after update: " + preferences.getInt(name, 0));

        updateSet(name);
    }

    public void incrementShots(String name) {
        int count = getShots(name);
        setShotsCount(name, ++count);
    }

    public void decrementShotCount(String name) {
        int count = getShots(name);
        count--;
        preferences.edit().putInt(name, count).apply();
    }

    public void changeCounterName(String oldName, String newName) {
        Set<String> set = getSet();
        set.remove(oldName);
        set.add(newName);
        preferences.edit().putStringSet("shotsList", set).commit();
        Log.d("Change counter name", preferences.getStringSet("shotsList", null).toString());
    }

    private void updateSet(String name) {
        //Retrieve the values
        Set<String> set = getSet();

        set.add(name);
        preferences.edit().putStringSet("shotsList", set).commit();
        System.out.println(preferences.getStringSet("shotsList", null));
    }

    public void removeShotsCounter(String name) {
        Set<String> set = getSet();
        set.remove(name);
        preferences.edit().putStringSet("shotsList", set).commit();

        Log.d("PM:removeShotsCounter", preferences.getStringSet("shotsList", null).toString());
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

        return newSet;
    }

    public List<String> getShotsArray() {
        Set<String> set = preferences.getStringSet("shotsList", null);

        shotsArray = new ArrayList<>();
//        Log.d("New ShotsArray", shotsArray.toString());
        if (set != null) {
            shotsArray.addAll(set);
            Collections.sort(shotsArray);
            return shotsArray;
        } else {
            return null;
        }
    }
}
