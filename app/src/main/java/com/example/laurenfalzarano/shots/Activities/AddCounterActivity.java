package com.example.laurenfalzarano.shots.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laurenfalzarano.shots.Persistence.PreferencesManager;
import com.example.laurenfalzarano.shots.R;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCounterActivity extends AppCompatActivity {

    @Bind(R.id.counter_name) EditText counterName;
    @Bind(R.id.shots_count) EditText shotsCount;
    @Bind(R.id.submit_counter) Button submitCounter;
    @Bind(R.id.count_decrement) IconTextView countDecrement;
    @Bind(R.id.count_increment) IconTextView countIncrement;

    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        ButterKnife.bind(this);


        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        Log.d("AddCounterActivity", "Name: " + name);

        if (name != null) {
            editMode = true;
            counterName.setText(name);
            counterName.setGravity(Gravity.CENTER_HORIZONTAL);
            shotsCount.requestFocus();
            shotsCount.setText(Integer.toString(PreferencesManager.get().getShots(name)));
            shotsCount.setFocusable(false);
            submitCounter.setText("UPDATE");
        }

        final PreferencesManager preferences = PreferencesManager.get();

        Button button = (Button) findViewById(R.id.submit_counter);
        final EditText nameField = (EditText) findViewById(R.id.counter_name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameField.getText().toString();
                int count = Integer.parseInt(shotsCount.getText().toString());
                System.out.println(newName);

                if (!editMode) {
                    // Get number of existing shots for this name
                    int oldCount = preferences.getShots(newName);
                    Log.d(newName, Integer.toString(oldCount));

                    // Set the counter
                    preferences.setShotsCount(newName, count);

                    nameField.setText("");
                    shotsCount.setText("1");

                    if (oldCount > 1) {
                        Snackbar.make(v, "A shots counter for '" + newName + "' already existed, and has been updated", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(getApplicationContext(), "The shots counter for " + name + " has been incremented.", Toast.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(v, "A new shots counter for '" + newName + "' has been created", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(getApplicationContext(), "A new shots counter for " + name + " has been created", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (!newName.equals(name)) {
                        preferences.changeCounterName(name, newName);
                    }

                    preferences.setShotsCount(newName, count);
                    Snackbar.make(v, "The shots counter for '" + name + "' has been updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.count_decrement)
    public void decrement(View view) {
        int count = Integer.parseInt(shotsCount.getText().toString());

        if (count == 0) { return; }

        shotsCount.setText(Integer.toString(count - 1));
    }

    @OnClick(R.id.count_increment)
    public void increment(View view) {
        int count = Integer.parseInt(shotsCount.getText().toString());
        shotsCount.setText(Integer.toString(count + 1));
    }

}
