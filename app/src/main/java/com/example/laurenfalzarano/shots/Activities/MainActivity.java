package com.example.laurenfalzarano.shots.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laurenfalzarano.shots.Adapters.ShotsListAdapter;
import com.example.laurenfalzarano.shots.Persistence.PreferencesManager;
import com.example.laurenfalzarano.shots.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.shots_list) ListView shotCountersList;
    @Bind(R.id.no_shots_text) TextView noShotCounters;
    @Bind(R.id.add_shot_counter) FloatingActionButton addShotCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ShotsListAdapter shotsListAdapter = new ShotsListAdapter(this, noShotCounters);
        shotCountersList.setAdapter(shotsListAdapter);
        addShotCounter.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_plus)
                .colorRes(R.color.white)
                .actionBarSize());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage_menu, menu);
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }


    @OnItemClick(R.id.shots_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = PreferencesManager.get().getShotsCounterList().get(position);
        Intent intent = new Intent(this, ShotCounterFormActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @OnItemLongClick(R.id.shots_list)
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String name = PreferencesManager.get().getShotsCounterList().get(position);
        PreferencesManager.get().removeShotsCounter(name);
        return true;
    }

    @OnClick(R.id.add_shot_counter)
    public void onClick(View view) {
        Intent intent = new Intent(this, ShotCounterFormActivity.class);
        startActivity(intent);
    }

}
