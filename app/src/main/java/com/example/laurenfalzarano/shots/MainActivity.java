package com.example.laurenfalzarano.shots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    private ListView shotsList;

    @Bind(R.id.no_shots_text) TextView noShotsText;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        shotsList = (ListView) findViewById(R.id.shots_list);

        Set<String> set = PreferencesManager.get().getSet();

        if (set == null) {
            set = new HashSet<>();
        } else {
            noShotsText.setText("");
            Log.d("Set ", set.toString());
        }

        // Convert string set to array list
        List<String> shotsArray = PreferencesManager.get().getShotsArray();
        shotsArray.addAll(set);

        // Add existing shots lists to main list
        ShotsListAdapter shotsListAdapter = new ShotsListAdapter(this);
        shotsList.setAdapter(shotsListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @OnItemClick(R.id.shots_list)
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    //    String name = subjectsAdapter.getItem(position);
        Intent intent = new Intent(this, AddCounterActivity.class);
    //    intent.putExtra("subject", subject);

        startActivity(intent);

    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Intent intent = new Intent(this, AddCounterActivity.class);
        startActivity(intent);
    }
}