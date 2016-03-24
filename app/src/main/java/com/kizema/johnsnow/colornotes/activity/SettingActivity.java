package com.kizema.johnsnow.colornotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kizema.johnsnow.colornotes.R;

public class SettingActivity extends BaseActivity {

    private View ivOkView, ivCancelView;
    private LinearLayout lvPickColors, lvRateUs, lvAuthors, lvClearAllNotes;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();

        lvPickColors = (LinearLayout) findViewById(R.id.lvPickColors);
        lvRateUs = (LinearLayout) findViewById(R.id.lvRateUs);
        lvAuthors = (LinearLayout) findViewById(R.id.lvAuthors);
        lvClearAllNotes = (LinearLayout) findViewById(R.id.lvClearAllNotes);

        lvPickColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lvRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "TI PIDOR", Toast.LENGTH_SHORT).show();
            }
        });

        lvAuthors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lvClearAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initActionBar() {
        ivOkView = findViewById(R.id.ivOkView);
        ivCancelView = findViewById(R.id.ivCancelView);

        ivOkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
