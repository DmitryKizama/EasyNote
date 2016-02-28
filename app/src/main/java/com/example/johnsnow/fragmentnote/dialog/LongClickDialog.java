package com.example.johnsnow.fragmentnote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnsnow.fragmentnote.R;

public class LongClickDialog extends Dialog {

    private Button btnDelete, btnUpdate;
    private EditText etUpdate;
    private int position;

    public interface OnDialogListener {
        void onDeleteText(int position);

        void onUpdateText(int position, String newText);
    }

    private OnDialogListener listener;

    public LongClickDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.changing_note_dialog);
        init();
    }

    private void init() {
        etUpdate = (EditText) findViewById(R.id.etUpdate);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUpdateText(position, etUpdate.getText().toString());
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



