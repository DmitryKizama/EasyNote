package com.kizema.johnsnow.colornotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.helper.Constant;
import com.kizema.johnsnow.colornotes.model.Note;

public class MainActivity extends BaseActivity implements NotesFragment.OnNoteFragInterectionCallback {

    private Button btnAdd;

    protected NotesFragment notesFrag;

    private View ivSearch, ivSettings, ivSwitchView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();

        notesFrag = (NotesFragment) getFragmentManager().findFragmentById(R.id.notesFrag);

        btnAdd = (Button) findViewById(R.id.addNote);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNote = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addNote, Constant.REQUES_CODE_ADD_NOTE);
            }
        });
    }

    private void initActionBar(){
        ivSearch = findViewById(R.id.ivSearch);
        ivSettings = findViewById(R.id.ivSettings);
        ivSwitchView = findViewById(R.id.ivSwitchView);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUES_CODE_ADD_NOTE) {
                int id = data.getIntExtra("NOTE", 0);
                Note note = Note.findbyId(id);
                notesFrag.notifAdap.addBottom(note);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean isRecyclerViewStable() {
        return true;
    }
}
