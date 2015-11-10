package com.example.laurenfalzarano.shots.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.laurenfalzarano.shots.Adapters.ShotCounterListAdapter;
import com.example.laurenfalzarano.shots.Persistence.PreferencesManager;
import com.example.laurenfalzarano.shots.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class MainActivity extends StandardActivity {

    @Bind(R.id.shots_list) ListView shotCountersList;
    @Bind(R.id.no_shots_text) TextView noShotCounters;
    @Bind(R.id.add_shot_counter) FloatingActionButton addShotCounter;

    private ShotCounterListAdapter shotCounterListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        shotCounterListAdapter = new ShotCounterListAdapter(this, noShotCounters);
        shotCountersList.setAdapter(shotCounterListAdapter);
        addShotCounter.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_plus)
                .colorRes(R.color.white)
                .actionBarSize());

        if (PreferencesManager.get().getFirstTimeUser()) {
            PreferencesManager.get().setFirstTimeUser(false);
            new MaterialDialog.Builder(this)
                    .title(R.string.welcome)
                    .content(R.string.ask_for_help)
                    .positiveText(android.R.string.yes)
                    .show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        shotCounterListAdapter.refreshContent();
    }

    @OnItemClick(R.id.shots_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = PreferencesManager.get().getShotCounterList().get(position);
        Intent intent = new Intent(this, ShotCounterFormActivity.class);
        intent.putExtra(ShotCounterFormActivity.SHOT_COUNTER_NAME_KEY, name);
        startActivity(intent);
    }

    @OnItemLongClick(R.id.shots_list)
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final String shotCounterName = shotCounterListAdapter.getItem(position);
        String message = getString(R.string.confirm_deletion_content) + shotCounterName + "\"?";
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_deletion_title)
                .content(message)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        PreferencesManager.get().removeShotsCounter(shotCounterName);
                        shotCounterListAdapter.removeShotCounter(position);
                    }
                })
                .show();
        return true;
    }

    @OnClick(R.id.add_shot_counter)
    public void onClick(View view) {
        Intent intent = new Intent(this, ShotCounterFormActivity.class);
        startActivity(intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
