package com.example.kmike.gdgkisumupreview;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    boolean isDark;
    BottomSheetBehavior sheetBehavior;
    int rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isDark = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("darkTheme", false);
        final int background, inverse;
        if (isDark){
            setTheme(R.style.AppTheme_Dark_Green);
            background = R.color.colorPrimaryInverse;
            inverse = R.color.colorTextPrimaryInverse;
        } else {
            setTheme(R.style.AppTheme_Green);
            background = R.color.colorPrimary;
            inverse = R.color.colorTextPrimary;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Bottom Sheet
        final TextView tvFeedback = findViewById(R.id.tvFeedback);
        final RelativeLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        final ImageButton btnOne = findViewById(R.id.btnSentimentOne),
                btnTwo = findViewById(R.id.btnSentimentTwo),
                btnThree = findViewById(R.id.btnSentimentThree),
                btnFour = findViewById(R.id.btnSentimentFour),
                btnFive  = findViewById(R.id.btnSentimentFive);

        final EditText etName = findViewById(R.id.etName),
                etEmail = findViewById(R.id.etEmail),
                etFeedBack = findViewById(R.id.etFeedback);

        View.OnClickListener ratingListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton[] otherButtons;

                if (view.equals(btnOne)) {
                    rating = 1;
                    otherButtons = new ImageButton[]{btnTwo, btnThree, btnFour, btnFive};
                } else if (view.equals(btnTwo)) {
                    rating = 2;
                    otherButtons = new ImageButton[]{btnOne, btnThree, btnFour, btnFive};
                } else if (view.equals(btnThree)) {
                    rating = 3;
                    otherButtons = new ImageButton[]{btnOne, btnTwo, btnFour, btnFive};
                } else if (view.equals(btnFour)) {
                    rating = 4;
                    otherButtons = new ImageButton[]{btnOne, btnTwo, btnThree, btnFive};
                } else {
                    rating = 5;
                    otherButtons = new ImageButton[]{btnOne, btnTwo, btnThree, btnFour};
                }
                ((ImageButton) view).setColorFilter(AboutActivity.this.getResources().getColor(R.color.googleGreen));
                for (ImageButton otherButton : otherButtons){
                    otherButton.setColorFilter(AboutActivity.this.getResources().getColor(inverse));
                }
            }
        };

        btnOne.setOnClickListener(ratingListener);
        btnTwo.setOnClickListener(ratingListener);
        btnThree.setOnClickListener(ratingListener);
        btnFour.setOnClickListener(ratingListener);
        btnFive.setOnClickListener(ratingListener);

        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        tvFeedback.setText(R.string.str_feedback);
                        tvFeedback.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        layoutBottomSheet.setBackground(getResources().getDrawable(R.drawable.green));
                        tvFeedback.setTypeface(ResourcesCompat.getFont(AboutActivity.this, R.font.nunito_extra_light));
                        tvFeedback.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_caret_up, 0, 0, 0);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_EXPANDED:
                        tvFeedback.setText(R.string.feedback);
                        tvFeedback.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        layoutBottomSheet.setBackgroundColor(getResources().getColor(background));
                        tvFeedback.setTypeface(ResourcesCompat.getFont(AboutActivity.this, R.font.nunito_light));
                        tvFeedback.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String feedback = etFeedBack.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(AboutActivity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                } else if (email.isEmpty()){
                    Toast.makeText(AboutActivity.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else if (feedback.isEmpty()){
                    Toast.makeText(AboutActivity.this, "Please enter your feedback.", Toast.LENGTH_SHORT).show();
                    etFeedBack.requestFocus();
                } else if (rating == 0) {
                    Toast.makeText(AboutActivity.this, "Please rate the event.", Toast.LENGTH_SHORT).show();
                } else {
                    etName.setText("");
                    etEmail.setText("");
                    etFeedBack.setText("");
                    ImageButton[] imageButtons = new ImageButton[]{btnOne, btnTwo, btnThree, btnFour, btnFive};
                    for (ImageButton imageButton:imageButtons){
                        imageButton.setColorFilter(AboutActivity.this.getResources().getColor(inverse));
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(sheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED){
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
