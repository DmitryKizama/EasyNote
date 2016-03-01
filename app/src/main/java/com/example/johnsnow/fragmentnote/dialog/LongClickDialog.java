package com.example.johnsnow.fragmentnote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.R;

public class LongClickDialog extends Dialog {

    private Button btnDelete, btnUpdate;
    private int position;

    public interface OnDialogListener {
        void onDeleteText(int position);

        void onUpdateText(int position);
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
                    listener.onUpdateText(position);
                }
                LongClickDialog.this.cancel();
            }
        });

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteText(position);
                }
                LongClickDialog.this.cancel();
            }
        });
    }

    public void show(int position) {
        super.show();
        this.position = position;
    }

    public void setListener(OnDialogListener listener) {
        this.listener = listener;
    }

}



