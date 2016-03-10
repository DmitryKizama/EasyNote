package com.example.johnsnow.fragmentnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.adapters.NotificationAdapter;
import com.example.johnsnow.fragmentnote.dialog.LongClickDialog;
import com.example.johnsnow.fragmentnote.dialog.UpdateDialog;
import com.example.johnsnow.fragmentnote.helper.Constant;

public class MainActivity extends FragmentActivity implements
        LongClickDialog.OnDialogListener, NotificationAdapter.OnNotifClickListener, UpdateDialog.OnDialogListener
// NewNoteDialog.OnDialogListener doesn't needed any more, because of AddActivity
{

    public interface OnUpdateNote {
        void onUpdateNote (String word, int position);
    }

    private Button add;
    //    private NewNoteDialog dialog;
    private RecyclerView rvNotif;
    private NotificationAdapter notifAdap;
    private LongClickDialog longClickDialog;
    private UpdateDialog updateDialog;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNotif = (RecyclerView) findViewById(R.id.rvNotif);
        notifAdap = new NotificationAdapter(this);
        rvNotif.setAdapter(notifAdap);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotif.setHasFixedSize(true);
        rvNotif.setLayoutManager(mLayoutManager);

        notifAdap.setOnNotifClickListener(this);

//        dialog = new NewNoteDialog(this);//work when pressed "add note" button
//        dialog.setListener(this);

        updateDialog = new UpdateDialog(this);//set update dialog listener, work when button update pressed
        updateDialog.setListener(this);

        longClickDialog = new LongClickDialog(this);//when long pressed on word
        longClickDialog.setListener(this);

        add = (Button) findViewById(R.id.addNote);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNote = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addNote, Constant.REQUES_CODE_ADD_NOTE);
//                dialog.show();
//                UIhelper.showKeyboardForDialog(MainActivity.this, dialog.getEditText(), dialog);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
//            String note = data.getStringExtra(Constant.NOTE);
            if (requestCode == Constant.REQUES_CODE_ADD_NOTE) {
                String note = data.getStringExtra(Constant.NOTE);
                notifAdap.addBottom(note);

            }
            if (requestCode == Constant.REQUES_CODE_FOR_UPDATE) {
                String note = data.getStringExtra("WORD");
                int position = data.getIntExtra("POS", 0);
                notifAdap.update(position, note);
            }
        }
    }

    @Override
    public void onDeleteText(int position) {
        notifAdap.deleteElementAtPos(position);
    }

    @Override
    public void onUpdateText(String word, int position) {
        Intent addNote = new Intent(MainActivity.this, AddActivity.class);
        addNote.putExtra("POS", position);
        addNote.putExtra("WORD", word);
        startActivityForResult(addNote, Constant.REQUES_CODE_FOR_UPDATE);
//        updateDialog.show();
//        UIhelper.showKeyboardForDialog(MainActivity.this, updateDialog.getEditText(), updateDialog);
//        updateDialog.setPosition(position);
    }

    @Override
    public void onNotifLongClick(String word, int position) {
        longClickDialog.show(word,position);
    }

    @Override
    public void onUpdateText(int position, String word) {
        notifAdap.update(position, word);
    }
}
