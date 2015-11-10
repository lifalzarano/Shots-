package com.example.laurenfalzarano.shots.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laurenfalzarano.shots.Adapters.ShotsListAdapter;
import com.example.laurenfalzarano.shots.Persistence.PreferencesManager;
import com.example.laurenfalzarano.shots.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

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

        // Convert string set to array list
        List<String> shotsArray = PreferencesManager.get().getShotsArray();
        if (shotsArray != null) {
            noShotsText.setText("");
            Log.d("Set ", shotsArray.toString());

            // Add existing shots lists to main list
            ShotsListAdapter shotsListAdapter = new ShotsListAdapter(this);
            shotsList.setAdapter(shotsListAdapter);
        }

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
        String name = PreferencesManager.get().getShotsArray().get(position);
        Log.d("MainActivity", "Name: " + name);
        Intent intent = new Intent(this, AddCounterActivity.class);
        intent.putExtra("name", name);

        startActivity(intent);

    }

    @OnItemLongClick(R.id.shots_list)
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                long id) {
        String name = PreferencesManager.get().getShotsArray().get(position);
        PreferencesManager.get().removeShotsCounter(name);
        Log.d("MainActivity:remove", "Name: " + name);

        return true;

    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Intent intent = new Intent(this, AddCounterActivity.class);
        startActivity(intent);
    }
}
