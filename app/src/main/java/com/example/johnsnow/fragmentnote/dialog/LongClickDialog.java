package com.example.johnsnow.fragmentnote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.johnsnow.fragmentnote.R;

public class LongClickDialog extends Dialog {

    private Button btnDelete, btnInfo;
    private int position;

    public interface OnDialogListener {
        void onDeleteText(int position);
    }

    private OnDialogListener listener;

    public LongClickDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_note_dialoge);
        init();
    }

    private void init() {
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
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



