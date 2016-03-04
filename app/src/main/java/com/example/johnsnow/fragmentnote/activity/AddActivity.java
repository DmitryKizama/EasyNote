package com.example.johnsnow.fragmentnote.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class AddActivity extends FragmentActivity {

    private Button btnAdd, btnCancel;
    private EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNote = (EditText) findViewById(R.id.etNote);
        UIhelper.showKeyboard(AddActivity.this, etNote);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("note", etNote.getText().toString());
                intent.putExtra("update", etNote.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}
