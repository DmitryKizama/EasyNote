package com.kizema.johnsnow.colornotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.adapters.ColorsAdapter;
import com.kizema.johnsnow.colornotes.anim.ResizeHeightAnimation;
import com.kizema.johnsnow.colornotes.appviews.DualProgressBar;
import com.kizema.johnsnow.colornotes.control.AppearDisappearControl;
import com.kizema.johnsnow.colornotes.control.FilterAppearDisappearControl;
import com.kizema.johnsnow.colornotes.control.SearchAppearDisappearControl;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.Note;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends BaseActivity implements DualProgressBar.OnDualProgressListener,
        ColorsAdapter.OnColorCLick, NotesRecyclerViewController.OnNoteRVInterectionCallback, BaseActivity.OnSoftKeyboardListener {

    private static final int FILTER_ANIM_DURATION = 300;

    private AppearDisappearControl filterAppearDisappearControl, searchAppearDisappearControl;

    private EditText etABTitle;
    private TextView tvTitle;
    private ImageView btnAddNote;

    protected NotesRecyclerViewController notesFrag;

    private boolean isKeyboardOpened = false;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

    private LinearLayout llFilter;
    private int filterH;

    private DualProgressBar dateProgressBar;
    private RecyclerView rvColors;

    private boolean filterIsOpened = true;

    private long leftDateMs, rightDateMs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //TODO set proper date from our date corridor
        leftDateMs = new Date(114, 10, 12).getTime();
        rightDateMs = new Date().getTime();

        notesFrag = new NotesRecyclerViewController(this, this);
        notesFrag.rvNotif.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                notesFrag.rvNotif.getLayoutParams().height = notesFrag.rvNotif.getHeight();
                notesFrag.rvNotif.removeOnLayoutChangeListener(this);
            }
        });

        btnAddNote = (ImageView) findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNote = new Intent(SearchActivity.this, AddActivity.class);
                startActivityForResult(addNote, AddActivity.REQUES_CODE_ADD_NOTE);//comment
            }
        });

        initActionBar();
        initFilter();

        addOnSoftKeyboardListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeOnSoftKeyboardListener(this);
    }

    private void initFilter(){
        llFilter = (LinearLayout) findViewById(R.id.llFilter);
        llFilter.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //we save filter height after layout occured
                filterH = llFilter.getHeight();
                llFilter.removeOnLayoutChangeListener(this);
            }
        });
        initDateProgressbar();
        initColorPicker();
    }

    private void initColorPicker(){
        rvColors = (RecyclerView) findViewById(R.id.rvColors);

        List<UserColor> userColors = UserColor.getAllUsers();
        ColorsAdapter adapter = new ColorsAdapter(this, userColors);
        adapter.OnColorCLick(this);
        rvColors.setAdapter(adapter);

        int w = UIHelper.getW();
        int cols = w / UIHelper.getPixel(50);
        int padd = UIHelper.getW() - cols*UIHelper.getPixel(50);

        int amount = userColors.size();
        int rows = amount / cols + (amount % cols > 0 ? 1 : 0);
        int rvColorHeight = rows * UIHelper.getPixel(50);

        rvColors.getLayoutParams().height = rvColorHeight;
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, cols, GridLayoutManager.VERTICAL, false);
        rvColors.setLayoutManager(mLayoutManager);
        rvColors.setHasFixedSize(true);
        rvColors.setPadding(padd/2, 0, padd/2, 0);
    }

    private void initDateProgressbar(){
        dateProgressBar = (DualProgressBar) findViewById(R.id.ageProgressBar);
        dateProgressBar.setOnDualProgressListener(this);
        dateProgressBar.setLeftProgress(20);
        dateProgressBar.setRightProgress(500);
    }

    private void initActionBar(){
        View ivSwitchView = findViewById(R.id.ivSwitchView);
        View ivSettings = findViewById(R.id.ivSettings);
        ImageView ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ImageView ivFilter = (ImageView) findViewById(R.id.ivFilter);
        etABTitle = (EditText) findViewById(R.id.etABTitle);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        filterAppearDisappearControl = new FilterAppearDisappearControl(ivFilter);

        searchAppearDisappearControl = new SearchAppearDisappearControl(ivSearch);

        ivSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent settingIntent = new Intent(SearchActivity.this, SettingActivity.class);
            startActivity(settingIntent);
            }
        });

        filterAppearDisappearControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFilterState();
            }
        });

        searchAppearDisappearControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });
    }

    private void openSearch(){
        if (!searchAppearDisappearControl.isFirstState()){
            etABTitle.setVisibility(View.VISIBLE);
            UIHelper.showKeyboard(this, etABTitle);
            tvTitle.setVisibility(View.INVISIBLE);
        } else {
            etABTitle.setVisibility(View.INVISIBLE);
            hideKeyBoard();
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    private void changeFilterState(){
        if (!filterIsOpened){
            //open filter
            llFilter.animate().translationY(0).setDuration(FILTER_ANIM_DURATION).start();
            notesFrag.rvNotif.animate().translationY(0).setDuration(FILTER_ANIM_DURATION).start();
            ResizeHeightAnimation anim = new ResizeHeightAnimation(notesFrag.rvNotif, notesFrag.rvNotif.getHeight() - filterH);
            anim.setDuration(FILTER_ANIM_DURATION);
            notesFrag.rvNotif.startAnimation(anim);

        } else {
            //close filter
            notesFrag.rvNotif.animate().translationY(-filterH).setDuration(FILTER_ANIM_DURATION).start();
            ResizeHeightAnimation anim = new ResizeHeightAnimation(notesFrag.rvNotif, notesFrag.rvNotif.getHeight() + filterH);
            anim.setDuration(FILTER_ANIM_DURATION);
            notesFrag.rvNotif.startAnimation(anim);
            llFilter.animate().translationY(-filterH).setDuration(FILTER_ANIM_DURATION).start();
        }

        filterIsOpened = !filterIsOpened;
    }

    @Override
    public void OnColorPicked(UserColor color) {
        //do search
    }

    @Override
    public void onProgressChanged(TextView tvSelected, float progress) {
        long val = (long)(progress * (rightDateMs - leftDateMs) + leftDateMs);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(val);

        tvSelected.setText(formatter.format(calendar.getTime()));
    }

    @Override
    public void onDoneDateEdit() {
        //do search
    }

    @Override
    public boolean isRecyclerViewStable() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        UIHelper.hideKeyboard(SearchActivity.this);
        if (notesFrag.onActivityResult(requestCode, resultCode, data)){
            return;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == AddActivity.REQUES_CODE_ADD_NOTE) {
                int id = data.getIntExtra("NOTE", 0);
                Note note = Note.findbyId(id);
                notesFrag.notifAdap.addBottom(note);
                //TODO scroll list to bottom
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSoftKeyboardShown() {
        isKeyboardOpened = true;
        notesFrag.rvNotif.getLayoutParams().height -= UIHelper.getKeyboardHeight();
        Log.d("YY", "SHOW Change :: " + UIHelper.getKeyboardHeight());
        Log.i("YY", "H :: " +  notesFrag.rvNotif.getLayoutParams().height);
        notesFrag.rvNotif.requestLayout();
    }

    @Override
    public void onSoftKeyboardHidden() {
        isKeyboardOpened = false;
        notesFrag.rvNotif.getLayoutParams().height += UIHelper.getKeyboardHeight();
        Log.d("YY", "HIDE Change :: " + UIHelper.getKeyboardHeight());
        Log.i("YY", "H :: " +  notesFrag.rvNotif.getLayoutParams().height);
        notesFrag.rvNotif.requestLayout();
    }

    @Override
    public void onBackPressed() {

        //TODO FIX close keyboard and search if opened
//        if (isKeyboardOpened){
//            searchAppearDisappearControl.performClick();
//            return;
//        }
//
//        super.onBackPressed();
    }
}
