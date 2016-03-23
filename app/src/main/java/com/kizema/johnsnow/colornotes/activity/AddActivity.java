package com.kizema.johnsnow.colornotes.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.Note;

public class AddActivity extends BaseActivity {

    public static final String POS = "POS";

    public static final int REQUES_CODE_ADD_NOTE = 1;
    public static final int REQUES_CODE_FOR_UPDATE = 2;

    private Button btnAdd, btnCancel;
    private EditText etNote;
    private int position;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getIntentInfo();

        etNote = (EditText) findViewById(R.id.etNote);
        UIHelper.showKeyboard(AddActivity.this, etNote);
        if (note != null) {
            etNote.setText(note.getName());
        }

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClick();
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

    private void onAddClick(){
        if (note == null){
            note = new Note(etNote.getText().toString());
        }else {
            note.setName(etNote.getText().toString());
            note.save();
        }
        Intent intent = new Intent();
        intent.putExtra("NOTE", note.getIdNumber());
        intent.putExtra("POS", position);
        UIHelper.hideKeyboard(this);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getIntentInfo() {
        if (getIntent() != null) {
            position = getIntent().getIntExtra("POS", 0);
            int id = getIntent().getIntExtra("NOTE", 0);
            note = Note.findbyId(id);
        }
    }
}
