package com.example.johnsnow.fragmentnote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.model.Note;

public class LongClickDialog extends Dialog {

    private Button btnDelete, btnUpdate;
    private int position;
    private Note word;

    public interface OnDialogListener {
        void onDeleteText(Note word, int position);
        void onUpdateText(Note word, int position);
    }

    private OnDialogListener listener;

    public LongClickDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_light);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.changing_note_dialog);
        init();
    }

    private void init() {

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUpdateText(word, position);
                }
                LongClickDialog.this.cancel();
            }
        });

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteText(word, position);
                }
                LongClickDialog.this.cancel();
            }
        });
    }

    public void show(Note word, int position) {
        super.show();
        this.position = position;
        this.word = word;
    }

    public void setListener(OnDialogListener listener) {
        this.listener = listener;
    }

}



