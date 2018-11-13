package com.example.kmike.gdgkisumupreview;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ResourcesActivity extends AppCompatActivity {
    boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isDark = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("darkTheme", false);
        if (isDark) setTheme(R.style.AppTheme_Dark_Yellow);
        else setTheme(R.style.AppTheme_Yellow);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = findViewById(R.id.fab);
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.layoutResources), "Please refresh before requesting a resource.", Snackbar.LENGTH_INDEFINITE)
                .setAction("Request", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ResourcesActivity.this, "Resource requested", Toast.LENGTH_SHORT).show();
                    }
                });
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_request));
            }

            @Override
            public void onShown(Snackbar sb) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (snackbar.isShown()){
                    snackbar.dismiss();
                } else{
                    snackbar.show();
                }
            }
        });
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
