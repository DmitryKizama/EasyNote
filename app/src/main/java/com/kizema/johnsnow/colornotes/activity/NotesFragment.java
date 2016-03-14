package com.kizema.johnsnow.colornotes.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.adapters.NotificationAdapter;
import com.kizema.johnsnow.colornotes.control.NoteItemTouchListener;
import com.kizema.johnsnow.colornotes.helper.Constant;
import com.kizema.johnsnow.colornotes.model.Note;

public class NotesFragment extends Fragment implements NoteItemTouchListener.OnNoteItemTouchInActionListener,
        NotificationAdapter.OnNotifClickListener{

    public RecyclerView rvNotif;
    public NotificationAdapter notifAdap;
    private NoteItemTouchListener chatFeedTouchListener;

    private boolean scrollEnabled = true;

    private ViewGroup chatFragParent;

    private OnNoteFragInterectionCallback onNoteInterectionCallback;

    public interface OnNoteFragInterectionCallback {
        boolean isRecyclerViewStable();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chatFragParent = (ViewGroup) inflater.inflate(R.layout.fragment_note, container, false);
        return chatFragParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onNoteInterectionCallback = (OnNoteFragInterectionCallback) getActivity();
        } catch (ClassCastException ex){
            Log.e("ERR", "Holding activity must implement OnNoteInterectionCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNoteInterectionCallback = null;
    }

    public void update(){
        notifAdap.update(Note.getAll());
    }

    private void init(){
        rvNotif = (RecyclerView) chatFragParent.findViewById(R.id.rvNotif);
        chatFeedTouchListener = new NoteItemTouchListener(this);

        notifAdap = new NotificationAdapter(getActivity(), Note.getAll());
        rvNotif.setAdapter(notifAdap);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                if (!scrollEnabled){
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
        Toast.makeText(getActivity(), "Clicked : " + note.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemRemoveClicked(Note note, int pos) {
        notifAdap.deleteElementAtPos(pos);
        note.delete();
    }

    @Override
    public void itemEditClicked(Note note, int pos) {
        Intent addNote = new Intent(getActivity(), AddActivity.class);
        addNote.putExtra("POS", pos);
        addNote.putExtra("NOTE", note.getIdNumber());
        startActivityForResult(addNote, Constant.REQUES_CODE_FOR_UPDATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == Constant.REQUES_CODE_FOR_UPDATE) {
                int position = data.getIntExtra("POS", 0);
                int id = data.getIntExtra("NOTE", 0);
                Note note = Note.findbyId(id);
                notifAdap.update(position, note);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
