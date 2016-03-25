package com.kizema.johnsnow.colornotes.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.R;

public class AuthorsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);
        initActionBar();

    }

    private void initActionBar() {
        ImageView ivBackInAuthorsActivity = (ImageView) findViewById(R.id.ivBackInAuthorsActivity);
        ivBackInAuthorsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AuthorsActivity.this, SettingActivity.class);
                finish();
                startActivity(backIntent);
            }
        });

    }

}

