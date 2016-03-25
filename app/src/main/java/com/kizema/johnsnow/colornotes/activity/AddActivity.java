package com.kizema.johnsnow.colornotes.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.control.AddBtnAppearDisappearControl;
import com.kizema.johnsnow.colornotes.control.AppearDisappearControl;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.Note;

public class AddActivity extends BaseActivity {

    public static final String POS = "POS";

    public static final int REQUES_CODE_ADD_NOTE = 1;
    public static final int REQUES_CODE_FOR_UPDATE = 2;

    private ImageView btnAdd;
    private EditText etNote, etDescription;
    private int position;
    private Note note;

    private AppearDisappearControl addBtnControl;
    private boolean isEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNote = (EditText) findViewById(R.id.etNote);
        etDescription = (EditText) findViewById(R.id.etNoteDescription);

        if (note != null) {
            etNote.setText(note.getName());
//            TODO
//            etDescription.setText(note.getDescription());
        }
        btnAdd = (ImageView) findViewById(R.id.btnAdd);
        addBtnControl = new AddBtnAppearDisappearControl(btnAdd);

        getIntentInfo();

        addBtnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    etNote.setEnabled(true);
                    etDescription.setEnabled(true);
                    UIHelper.showKeyboard(AddActivity.this, etNote);
                } else {
                    etNote.setEnabled(false);
                    etDescription.setEnabled(false);
                    UIHelper.hideKeyboard(AddActivity.this);
                }
                isEdit = !isEdit;

            }
        });

        initActionBar();

        if (!isEdit){
            etNote.setEnabled(false);
            etDescription.setEnabled(false);
        } else{
            etNote.setEnabled(true);
            etDescription.setEnabled(true);
        }
    }

    private void initActionBar() {
        View ivOkView = findViewById(R.id.ivOkViewInAddActivity);
        View ivSettings = findViewById(R.id.ivSettingsInAddActivity);

        ivOkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClick();

            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(AddActivity.this, SettingActivity.class);
                startActivity(settingIntent);
            }
        });

    }

    private void onAddClick() {
        if (note == null) {
            note = new Note(etNote.getText().toString());
        } else {
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void getIntentInfo() {

        if (getIntent() != null) {
            isEdit = getIntent().getBooleanExtra("isEdit", true);
            if (!isEdit) {
                addBtnControl.setFirstState(true);
            }
            position = getIntent().getIntExtra("POS", 0);
            int id = getIntent().getIntExtra("NOTE", 0);
            note = Note.findbyId(id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
