package com.kizema.johnsnow.colornotes.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.adapters.ColorsAdapter;
import com.kizema.johnsnow.colornotes.control.AddBtnAppearDisappearControl;
import com.kizema.johnsnow.colornotes.control.AppearDisappearControl;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.Note;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.util.List;

public class AddActivity extends BaseActivity implements ColorsAdapter.OnColorCLick {

    public static final String POS = "POS";

    public static final int REQUES_CODE_ADD_NOTE = 1;
    public static final int REQUES_CODE_FOR_UPDATE = 2;

    private ImageView btnAdd;
    private EditText etNote, etDescription;
    private int position;
    private Note note;
    private UserColor color;
    private RecyclerView rvColorsInAddActivity;

    private AppearDisappearControl addBtnControl;
    private boolean isEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNote = (EditText) findViewById(R.id.etNote);
        etDescription = (EditText) findViewById(R.id.etNoteDescription);

        btnAdd = (ImageView) findViewById(R.id.btnAdd);
        addBtnControl = new AddBtnAppearDisappearControl(btnAdd);

        getIntentInfo();

        addBtnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    etNote.setEnabled(true);
                    etDescription.setEnabled(true);
//                    UIHelper.showKeyboard(AddActivity.this, etNote);
                } else {
                    etNote.setEnabled(false);
                    etDescription.setEnabled(false);
//                    UIHelper.hideKeyboard(AddActivity.this);
                }
                isEdit = !isEdit;

            }
        });

        initActionBar();
        initColorPicker();
        initStartSettings();


    }

    private void initStartSettings(){
        if (!isEdit) {
            etNote.setEnabled(false);
            etDescription.setEnabled(false);
            isEdit = true;
        } else {
            addBtnControl.setFirstState(false);
            etNote.setEnabled(true);
            etDescription.setEnabled(true);
            isEdit = false;
        }

        if (note != null) {
            etNote.setText(note.getName());
            etDescription.setText(note.getDescription());
//            rvColorsInAddActivity.
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

    private void initColorPicker(){
        rvColorsInAddActivity = (RecyclerView) findViewById(R.id.rvColorsInAddActivity);

        List<UserColor> userColors = UserColor.getAllUsers();
        ColorsAdapter adapter = new ColorsAdapter(this, userColors);
        adapter.OnColorCLick(this);
        rvColorsInAddActivity.setAdapter(adapter);

        int w = UIHelper.getW();
        int cols = w / UIHelper.getPixel(50);
        int padd = UIHelper.getW() - cols*UIHelper.getPixel(50);

        int amount = userColors.size();
        int rows = amount / cols + (amount % cols > 0 ? 1 : 0);
        int rvColorHeight = rows * UIHelper.getPixel(50);

        rvColorsInAddActivity.getLayoutParams().height = rvColorHeight;
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, cols, GridLayoutManager.VERTICAL, false);
        rvColorsInAddActivity.setLayoutManager(mLayoutManager);
        rvColorsInAddActivity.setHasFixedSize(true);
        rvColorsInAddActivity.setPadding(padd / 2, 0, padd / 2, 0);
    }

    private void onAddClick() {
        if (note == null) {
            note = new Note(etNote.getText().toString(), etDescription.getText().toString(), color);
        } else {
            note.setName(etNote.getText().toString());
            note.setDescription(etDescription.getText().toString());
            note.setColor(color);
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
    public void OnColorPicked(UserColor color) {
        this.color = color;
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
