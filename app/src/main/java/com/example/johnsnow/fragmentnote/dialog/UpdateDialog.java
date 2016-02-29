package com.example.johnsnow.fragmentnote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.helper.UIhelper;


public class UpdateDialog extends Dialog {


    public interface OnDialogListener {
        void onUpdateText(int position, String word);
    }

    private EditText etWord;
    private Button btnOk, btnCancel;
    private OnDialogListener listener;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }




    public UpdateDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_light);
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
                    listener.onUpdateText(position ,etWord.getText().toString());
                }
                UpdateDialog.this.cancel();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDialog.this.cancel();
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
        return (EditText) findViewById(R.id.etWord);
    }
}
