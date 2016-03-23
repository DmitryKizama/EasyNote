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

public class AddActivity extends BaseActivity implements BaseActivity.OnSoftKeyboardListener {

    public static final String POS = "POS";

    public static final int REQUES_CODE_ADD_NOTE = 1;
    public static final int REQUES_CODE_FOR_UPDATE = 2;

    private ImageView btnAdd;
    private EditText etNote;
    private int position;
    private Note note;

    private AppearDisappearControl addBtnControl;
    private boolean isEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNote = (EditText) findViewById(R.id.etNote);
        if (note != null) {
            etNote.setText(note.getName());
        }
        btnAdd = (ImageView) findViewById(R.id.btnAdd);
        addBtnControl = new AddBtnAppearDisappearControl(btnAdd);

        getIntentInfo();

        addBtnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit == true) {
                    etNote.setEnabled(true);
                    UIHelper.showKeyboard(AddActivity.this, etNote);
                } else {
                    etNote.setEnabled(false);
                    UIHelper.hideKeyboard(AddActivity.this);
                }
                isEdit = !isEdit;

            }
        });

        initActionBar();
    }

    private void initActionBar() {
        View ivCancelView = findViewById(R.id.ivCancelViewInAddActivity);
        View ivSettings = findViewById(R.id.ivSettingsInAddActivity);

        ivCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        onAddClick();
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
    public void onSoftKeyboardShown() {
        btnAdd.setTranslationY(-UIHelper.getKeyboardHeight());
    }

    @Override
    public void onSoftKeyboardHidden() {
        btnAdd.setTranslationY(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeOnSoftKeyboardListener(this);
    }
}
