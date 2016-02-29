package com.example.johnsnow.fragmentnote;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.adapters.NotificationAdapter;
import com.example.johnsnow.fragmentnote.dialog.LongClickDialog;
import com.example.johnsnow.fragmentnote.dialog.NewNoteDialog;
import com.example.johnsnow.fragmentnote.dialog.UpdateDialog;
import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class MainActivity extends FragmentActivity implements NewNoteDialog.OnDialogListener,
        LongClickDialog.OnDialogListener, NotificationAdapter.OnNotifClickListener, UpdateDialog.OnDialogListener {

    private Button add;
    private NewNoteDialog dialog;
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

        dialog = new NewNoteDialog(this);//work when pressed "add note" button
        dialog.setListener(this);

        updateDialog = new UpdateDialog(this);//set update dialog listener, work when button update pressed
        updateDialog.setListener(this);

        longClickDialog = new LongClickDialog(this);//when long pressed on word
        longClickDialog.setListener(this);

        add = (Button) findViewById(R.id.addNote);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                UIhelper.showKeyboardForDialog(MainActivity.this, dialog.getEditText(), dialog);
            }
        });


    }

    @Override
    public void onEditText(String word) {
        notifAdap.addBottom(word);
    }

    @Override
    public void onDeleteText(int position) {
        notifAdap.deleteElementAtPos(position);
    }

    @Override
    public void onUpdateText(int position) {
        updateDialog.show();
        UIhelper.showKeyboardForDialog(MainActivity.this, updateDialog.getEditText(), updateDialog);
        updateDialog.setPosition(position);
    }

    @Override
    public void onNotifLongClick(String word, int position) {
        longClickDialog.show(position);
    }

    @Override
    public void onUpdateText(int position, String word) {
        notifAdap.update(position, word);
    }
}
