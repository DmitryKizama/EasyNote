package com.example.johnsnow.fragmentnote.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnsnow.fragmentnote.R;

public class NewEntryDialog extends Dialog {


    public interface OnDialogListener {
        void onEditText(String word);
    }

    private EditText etWord;
    private Button btnOk, btnCancel;
    private OnDialogListener listener;

    public NewEntryDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog);
        init();
    }

    private void init() {
        etWord = (EditText) findViewById(R.id.etWord);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEditText(etWord.getText().toString());
                }
                NewEntryDialog.this.cancel();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewEntryDialog.this.cancel();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        etWord.setText("");
    }

    public void setListener(OnDialogListener listener) {
        this.listener = listener;
    }

    public EditText getEditText(){
        return etWord;
    }
}


