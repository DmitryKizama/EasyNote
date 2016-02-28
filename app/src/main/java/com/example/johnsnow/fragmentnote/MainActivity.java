package com.example.johnsnow.fragmentnote;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.dialog.LongClickDialog;
import com.example.johnsnow.fragmentnote.dialog.NewEntryDialog;
import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class MainActivity extends FragmentActivity implements NewEntryDialog.OnDialogListener, LongClickDialog.OnDialogListener, NotificationAdapter.OnNotifClickListener {

    private Button add;
    private NewEntryDialog dialog;
    private RecyclerView rvNotif;
    private NotificationAdapter notifAdap;
    private LongClickDialog longClickDialog;


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

        dialog = new NewEntryDialog(this);
        dialog.setListener(this);

        longClickDialog = new LongClickDialog(this);
        longClickDialog.setListener(this);

        add = (Button) findViewById(R.id.addNote);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                UIhelper.showKeyboard(MainActivity.this, v);
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
    public void onNotifLongClick(String word, int position) {
        longClickDialog.show(position);
    }
}
