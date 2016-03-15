package com.kizema.johnsnow.colornotes.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.adapters.ColorsAdapter;
import com.kizema.johnsnow.colornotes.anim.ResizeHeightAnimation;
import com.kizema.johnsnow.colornotes.appviews.DualProgressBar;
import com.kizema.johnsnow.colornotes.control.FilterAppearDisappearControl;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends BaseActivity implements DualProgressBar.OnDualProgressListener,
        ColorsAdapter.OnColorCLick, NotesFragment.OnNoteFragInterectionCallback {

    private View ivBack;
    private FilterAppearDisappearControl filterAppearDisappearControl;
    private EditText tvABTitle;

    protected NotesFragment notesFrag;

    private LinearLayout llFilter;
    private int filterH, filterY;

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

        notesFrag = new NotesFragment();
        notesFrag.init(this, this);

        initActionBar();
        initFilter();
    }

    private void initFilter(){
        llFilter = (LinearLayout) findViewById(R.id.llFilter);
        llFilter.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                filterH = llFilter.getHeight();
                filterY = (int) llFilter.getY();
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
        int h = rows * UIHelper.getPixel(50);

        rvColors.getLayoutParams().height = h;
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
        ivBack = findViewById(R.id.ivBack);
        ImageView ivFilter = (ImageView) findViewById(R.id.ivFilter);
        tvABTitle = (EditText) findViewById(R.id.tvABTitle);
        View abLayout = findViewById(R.id.abLayout);


        filterAppearDisappearControl = new FilterAppearDisappearControl(ivFilter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        filterAppearDisappearControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFiletr();
            }
        });
    }

    private void openFiletr(){
        //open filter
        if (!filterIsOpened){
            llFilter.animate().translationY(0).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();

            notesFrag.rvNotif.animate().translationY(0).setDuration(500).start();
            ResizeHeightAnimation anim = new ResizeHeightAnimation(notesFrag.rvNotif, notesFrag.rvNotif.getHeight() - filterH);
            anim.setDuration(500);
            notesFrag.rvNotif.startAnimation(anim);

        } else {
            notesFrag.rvNotif.animate().translationY(-filterH).setDuration(500).start();
            ResizeHeightAnimation anim = new ResizeHeightAnimation(notesFrag.rvNotif, notesFrag.rvNotif.getHeight() + filterH);
            anim.setDuration(500);
            notesFrag.rvNotif.startAnimation(anim);

            llFilter.animate().translationY(-filterH).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();

        }

        filterIsOpened = !filterIsOpened;

    }

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

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
    public void onDoneEdit() {
        //do search
    }

    @Override
    public boolean isRecyclerViewStable() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        notesFrag.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
