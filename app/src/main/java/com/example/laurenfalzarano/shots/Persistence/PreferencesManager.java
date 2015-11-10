package com.example.laurenfalzarano.shots.Persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.laurenfalzarano.shots.Application.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by laurenfalzarano on 9/23/15.
 */
public class PreferencesManager {

    private static PreferencesManager instance;
    public SharedPreferences preferences;

    private static final String SHOT_COUNTER_LIST_KEY = "shotCounterList";

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

    public int getNumShotsForCounter(String name) {
        return preferences.getInt(name, 0);
    }

    public void setShotsCount(String shotCounterName, int count) {
        preferences.edit().putInt(shotCounterName, count).apply();
    }

    public void changeCounterName(String oldName, String newName) {
        Set<String> set = getShotCounterSet();
        int numShots = getNumShotsForCounter(oldName);
        set.remove(oldName);
        set.add(newName);
        preferences.edit().putStringSet(SHOT_COUNTER_LIST_KEY, set).apply();
        setShotsCount(newName, numShots);
    }

    public boolean isCounterNameTaken(String shotCounterName) {
        return getShotCounterSet().contains(shotCounterName);
    }

    public void addShotCounter(String name) {
        Set<String> set = getShotCounterSet();
        set.add(name);
        preferences.edit().putStringSet(SHOT_COUNTER_LIST_KEY, set).apply();
    }

    public void removeShotsCounter(String name) {
        Set<String> shotCounterSet = getShotCounterSet();
        shotCounterSet.remove(name);
        preferences.edit().putStringSet(SHOT_COUNTER_LIST_KEY, shotCounterSet).apply();
    }

    private Set<String> getShotCounterSet() {
        return preferences.getStringSet(SHOT_COUNTER_LIST_KEY, new HashSet<String>());
    }

    public List<String> getShotCounterList() {
        Set<String> shotCounterSet = getShotCounterSet();
        List<String> shotCounterList = new ArrayList<>(shotCounterSet);
        Collections.sort(shotCounterList);
        return shotCounterList;
    }
}
