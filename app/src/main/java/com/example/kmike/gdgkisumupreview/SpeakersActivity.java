package com.example.kmike.gdgkisumupreview;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SpeakersActivity extends AppCompatActivity {
    boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            isDark = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("darkTheme", false);
            if (isDark) setTheme(R.style.AppTheme_Dark_Red);
            else setTheme(R.style.AppTheme_Red);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_speakers);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final ImageButton btn = findViewById(R.id.btnRoomDetails),
                    btn1 = findViewById(R.id.btnRoomDetails1),
                    btn2 = findViewById(R.id.btnRoomDetails2);
            final RelativeLayout lay = findViewById(R.id.layRoomDetails),
                    lay1 = findViewById(R.id.layRoomDetails1),
                    lay2 = findViewById(R.id.layRoomDetails2);

            View.OnClickListener details = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout[] otherLayouts;
                    ImageButton[] imageButtons;
                    RelativeLayout layout;
                    if (view.equals(btn)) {
                        layout = lay;
                        otherLayouts = new RelativeLayout[]{lay1, lay2};
                        imageButtons = new ImageButton[]{btn1, btn2};
                    } else if (view.equals(btn1)) {
                        layout = lay1;
                        otherLayouts = new RelativeLayout[]{lay, lay2};
                        imageButtons = new ImageButton[]{btn, btn2};
                    } else {
                        layout = lay2;
                        otherLayouts = new RelativeLayout[]{lay, lay1};
                        imageButtons = new ImageButton[]{btn, btn1};
                    }

                    boolean isVisible = layout.getVisibility() == View.VISIBLE;
                    if (isVisible) {
                        layout.setVisibility(View.GONE);
                        ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.ic_caret_down));
                    } else {
                        layout.setVisibility(View.VISIBLE);
                        ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.ic_caret_up));
                        for (RelativeLayout otherLayout : otherLayouts) {
                            otherLayout.setVisibility(View.GONE);
                        }
                        for (ImageButton imageButton : imageButtons) {
                            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_caret_down));
                        }
                    }
                }
            };
            btn.setOnClickListener(details);
            btn1.setOnClickListener(details);
            btn2.setOnClickListener(details);
        }catch(Exception e){
            Toast.makeText(SpeakersActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
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