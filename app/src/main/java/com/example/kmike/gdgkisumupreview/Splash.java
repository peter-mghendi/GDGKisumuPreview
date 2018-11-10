package com.example.kmike.gdgkisumupreview;

import android.content.Intent;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isDark = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("darkTheme", false);
        if (isDark) setTheme(R.style.AppTheme_Dark);
        else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);

        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(1000);

        final ProgressBar loader = findViewById(R.id.progressBarLoader);
        final ImageView logo = findViewById(R.id.gdgLogo);
        final TextView title= findViewById(R.id.splash_title);

        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(in);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                title.setVisibility(View.VISIBLE);
                title.startAnimation(in);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.startAnimation(out);
                title.startAnimation(out);
                loader.setVisibility(View.VISIBLE);
                loader.startAnimation(in);
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                startActivity(new Intent(Splash.this,MainActivity.class));
                finish();
            }
        }, 4000);
    }
}
