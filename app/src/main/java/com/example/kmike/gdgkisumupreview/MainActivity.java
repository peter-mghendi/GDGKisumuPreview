package com.example.kmike.gdgkisumupreview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isDark;
    private CardView cvSchedule, cvLivestream, cvResources, cvAbout;
    private TextView tvDevFestTitle;
    Animation fadein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        isDark = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("darkTheme", false);
        if (isDark) setTheme(R.style.AppTheme_Dark);
        else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View.OnClickListener cardClick = new View.OnClickListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public void onClick(View view) {
                Class nextClass;
                if (view.equals(cvSchedule)) nextClass = ScheduleActivity.class;
                else if (view.equals(cvLivestream)) nextClass = SpeakersActivity.class;
                else if (view.equals(cvResources)) nextClass = ResourcesActivity.class;
                else nextClass = AboutActivity.class;
                startActivity(new Intent(MainActivity.this, nextClass));
            }
        };

        fadein = new AlphaAnimation(0.0f,1.0f);
        fadein.setDuration(200);

        tvDevFestTitle = findViewById(R.id.tvTitle);
        cvSchedule = findViewById(R.id.cvSchedule);
        cvLivestream = findViewById(R.id.cvLiveStream);
        cvResources = findViewById(R.id.cvResources);
        cvAbout = findViewById(R.id.cvAbout);

        cvSchedule.setOnClickListener(cardClick);
        cvLivestream.setOnClickListener(cardClick);
        cvResources.setOnClickListener(cardClick);
        cvAbout.setOnClickListener(cardClick);

        if (getIntent().hasExtra("animate") && getIntent().getBooleanExtra("animate", false)){
            animate();
        }

        if (!getIntent().hasExtra("showDialog") || getIntent().getBooleanExtra("showDialog", true) ) {
            builder.setTitle("Have you gotten your ticket?")
                    .setMessage("Get your ticket now!!!!")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String link = "https://www.meetup.com/GDGKisumu/events/255024490";
                            final Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(linkIntent);
                                }
                            }, 2000);
                            animate();
                        }
                    })
                    .setNeutralButton("No thank you", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            animate();
                        }
                    });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    animate();
                }
            });
            builder.create().show();
        }
    }

    void animate(){
        cvSchedule.setVisibility(View.VISIBLE);
        cvSchedule.startAnimation(fadein);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cvLivestream.setVisibility(View.VISIBLE);
                cvLivestream.startAnimation(fadein);
            }
        },400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cvResources.setVisibility(View.VISIBLE);
                cvResources.startAnimation(fadein);
            }
        },800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cvAbout.setVisibility(View.VISIBLE);
                cvAbout.startAnimation(fadein);
            }
        },1200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_theme:
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                editor.putBoolean("darkTheme", !isDark).apply();
                startActivity(new Intent(MainActivity.this, MainActivity.class)
                        .putExtra("showDialog", false)
                        .putExtra("animate", true));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
