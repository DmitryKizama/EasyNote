package com.example.johnsnow.fragmentnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.adapters.NotificationAdapter;
import com.example.johnsnow.fragmentnote.control.NoteItemTouchListener;
import com.example.johnsnow.fragmentnote.dialog.LongClickDialog;
import com.example.johnsnow.fragmentnote.helper.Constant;
import com.example.johnsnow.fragmentnote.model.Note;

public class MainActivity extends FragmentActivity implements
        LongClickDialog.OnDialogListener, NotificationAdapter.OnNotifClickListener
// NewNoteDialog.OnDialogListener doesn't needed any more, because of AddActivity
{

    private Button btnAdd;
    private RecyclerView rvNotif;
    private NotificationAdapter notifAdap;
    private LongClickDialog longClickDialog;

    private NoteItemTouchListener chatFeedTouchListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNotif = (RecyclerView) findViewById(R.id.rvNotif);
        chatFeedTouchListener = new NoteItemTouchListener();

        notifAdap = new NotificationAdapter(this, Note.getAll());
        rvNotif.setAdapter(notifAdap);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotif.setHasFixedSize(true);
        rvNotif.setLayoutManager(mLayoutManager);

        notifAdap.setOnNotifClickListener(this);

        longClickDialog = new LongClickDialog(this);//when long pressed on word
        longClickDialog.setListener(this);

        btnAdd = (Button) findViewById(R.id.addNote);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNote = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addNote, Constant.REQUES_CODE_ADD_NOTE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {

            if (requestCode == Constant.REQUES_CODE_ADD_NOTE) {
                int id = data.getIntExtra("NOTE", 0);
                Note note = Note.findbyId(id);
                notifAdap.addBottom(note);

            }
            if (requestCode == Constant.REQUES_CODE_FOR_UPDATE) {
                int position = data.getIntExtra("POS", 0);
                int id = data.getIntExtra("NOTE", 0);
                Note note = Note.findbyId(id);
                notifAdap.update(position, note);
            }
        }
    }

    @Override
    public void onDeleteText(Note note, int position) {
        notifAdap.deleteElementAtPos(position);
        note.delete();
    }

    @Override
    public void onUpdateText(Note word, int position) {
        Intent addNote = new Intent(MainActivity.this, AddActivity.class);
        addNote.putExtra("POS", position);
        addNote.putExtra("NOTE", word.getIdNumber());
        startActivityForResult(addNote, Constant.REQUES_CODE_FOR_UPDATE);
//        updateDialog.show();
//        UIhelper.showKeyboardForDialog(MainActivity.this, updateDialog.getEditText(), updateDialog);
//        updateDialog.setPosition(position);
    }

    @Override
    public void onNotifLongClick(Note word, int position) {
        longClickDialog.show(word, position);
    }

    @Override
    public void onItemDown(View v, int pos) {
        chatFeedTouchListener.onDownOccured(v);
    }

    @Override
    public void onTouch(MotionEvent me) {
        chatFeedTouchListener.onTouch(rvNotif, me);
    }

    @Override
    public void onItemClicked(Note thread, int pos) {
        //TODO this is called when we single click on Note item
    }

}
