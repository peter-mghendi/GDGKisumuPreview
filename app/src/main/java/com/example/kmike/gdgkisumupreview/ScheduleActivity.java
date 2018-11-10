package com.example.kmike.gdgkisumupreview;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {
    boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isDark = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("darkTheme", false);
        if (isDark) setTheme(R.style.AppTheme_Dark_Blue);
        else setTheme(R.style.AppTheme_Blue);
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_schedule);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_refresh:
                Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
