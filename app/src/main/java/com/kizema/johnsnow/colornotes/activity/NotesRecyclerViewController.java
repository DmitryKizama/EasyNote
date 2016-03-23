package com.kizema.johnsnow.colornotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.adapters.NotificationAdapter;
import com.kizema.johnsnow.colornotes.control.NoteItemTouchListener;
import com.kizema.johnsnow.colornotes.helper.Constant;
import com.kizema.johnsnow.colornotes.model.Note;

public class NotesRecyclerViewController implements NoteItemTouchListener.OnNoteItemTouchInActionListener,
        NotificationAdapter.OnNotifClickListener {

    public RecyclerView rvNotif;
    public NotificationAdapter notifAdap;
    private NoteItemTouchListener chatFeedTouchListener;

    private boolean scrollEnabled = true;

    private OnNoteRVInterectionCallback onNoteInterectionCallback;
    private BaseActivity activity;

    public interface OnNoteRVInterectionCallback {
        boolean isRecyclerViewStable();
    }

    public void update() {
        notifAdap.update(Note.getAll());
    }

    public NotesRecyclerViewController(BaseActivity activity, OnNoteRVInterectionCallback onNoteRVInterectionCallback) {
        this.onNoteInterectionCallback = onNoteRVInterectionCallback;
        this.activity = activity;

        init();
    }

    private void init() {
        rvNotif = (RecyclerView) activity.findViewById(R.id.rvNotif);
        chatFeedTouchListener = new NoteItemTouchListener(this);

        notifAdap = new NotificationAdapter(activity, Note.getAll());
        rvNotif.setAdapter(notifAdap);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                if (!scrollEnabled) {
                    return false;
                }

                return super.canScrollVertically();
            }
        };

        rvNotif.setHasFixedSize(onNoteInterectionCallback.isRecyclerViewStable());
        rvNotif.setLayoutManager(mLayoutManager);

        notifAdap.setOnNotifClickListener(this);
    }

    @Override
    public void setTouchInAction(boolean inAction) {
        scrollEnabled = inAction;
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
    public void onItemClicked(Note note, int pos) {
        //TODO this is called when we single click on Note item
        Intent addNote = new Intent(activity, AddActivity.class);
        addNote.putExtra("isEdit", false);
        addNote.putExtra("POS", pos);
        addNote.putExtra("NOTE", note.getIdNumber());
        activity.startActivityForResult(addNote, Constant.REQUES_CODE_FOR_UPDATE);
//        Toast.makeText(activity, "Clicked : " + note.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemRemoveClicked(Note note, int pos) {
        notifAdap.deleteElementAtPos(pos);
        note.delete();
    }

    @Override
    public void itemEditClicked(Note note, int pos) {
        Intent addNote = new Intent(activity, AddActivity.class);
        addNote.putExtra("POS", pos);
        addNote.putExtra("NOTE", note.getIdNumber());
        activity.startActivityForResult(addNote, Constant.REQUES_CODE_FOR_UPDATE);
    }

    /**
     * @return true if controoller worked out event, false - otherwise
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constant.REQUES_CODE_FOR_UPDATE) {
            if (resultCode == Activity.RESULT_OK) {
                int position = data.getIntExtra("POS", 0);
                int id = data.getIntExtra("NOTE", 0);
                Note note = Note.findbyId(id);
                notifAdap.update(position, note);
            }

            return true;
        }

        return false;
    }
}
