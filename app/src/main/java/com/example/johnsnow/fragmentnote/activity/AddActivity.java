package com.example.johnsnow.fragmentnote.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.helper.Constant;
import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class AddActivity extends FragmentActivity {

    public static final String POS = "POS";

    private Button btnAdd, btnCancel;
    private EditText etNote;
    private int position;
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getIntentInfo();

        etNote = (EditText) findViewById(R.id.etNote);
        UIhelper.showKeyboard(AddActivity.this, etNote);

        etNote.setText(word);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("WORD", etNote.getText().toString());
                intent.putExtra("POS", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
    private void getIntentInfo(){
        if (getIntent() != null){
            position = getIntent().getIntExtra("POS", 0);
            word = getIntent().getStringExtra("WORD");
        }
    }
}
