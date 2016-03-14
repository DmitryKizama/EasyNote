package com.kizema.johnsnow.colornotes.activity;

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
import com.kizema.johnsnow.colornotes.appviews.DualProgressBar;
import com.kizema.johnsnow.colornotes.control.FilterAppearDisappearControl;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends BaseActivity implements DualProgressBar.OnDualProgressListener,
        ColorsAdapter.OnColorCLick {

    private View ivBack;
    private FilterAppearDisappearControl filterAppearDisappearControl;
    private EditText tvABTitle;

    private LinearLayout llFilter;
    private int filterH;

    private DualProgressBar dateProgressBar;
    private RecyclerView rvColors;

    private long leftDateMs, rightDateMs;

    private Status status = Status.SEARCH_FILTER;

    private enum Status{
        SEARCH, SEARCH_FILTER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //TODO set proper date from our date corridor
        leftDateMs = new Date(114, 10, 12).getTime();
        rightDateMs = new Date().getTime();

        initActionBar();

        initFilter();
    }

    private void initFilter(){
        llFilter = (LinearLayout) findViewById(R.id.llFilter);
        llFilter.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                filterH = llFilter.getHeight();
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

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, cols, GridLayoutManager.VERTICAL, false);
        rvColors.setLayoutManager(mLayoutManager);
        rvColors.setHasFixedSize(true);
        rvColors.setPadding(padd/2, 0, padd/2, 0);
    }

    private void initDateProgressbar(){
        dateProgressBar = (DualProgressBar) findViewById(R.id.ageProgressBar);
        dateProgressBar.setOnDualProgressListener(this);
        dateProgressBar.setLeftProgress(20);
        dateProgressBar.setRightProgress(50);
    }

    private void initActionBar(){
        ivBack = findViewById(R.id.ivBack);
        ImageView ivFilter = (ImageView) findViewById(R.id.ivFilter);
        tvABTitle = (EditText) findViewById(R.id.tvABTitle);
        View abLayout = findViewById(R.id.abLayout);
        abLayout.bringToFront();

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
                switch (status) {
                    case SEARCH:
                        status = Status.SEARCH_FILTER;
                        openFiletr(true);
                        break;
                    case SEARCH_FILTER:
                        status = Status.SEARCH;
                        openFiletr(false);
                        break;
                }

                setTvABTitle();
            }
        });

        setTvABTitle();
    }

    private void openFiletr(boolean open){
        //open filter
        if (open){
            llFilter.animate().translationY(0).setDuration(500).start();
        } else {
            llFilter.animate().translationY(-filterH).setDuration(500).start();
        }

    }


    private void setTvABTitle(){
        switch (status){
            case SEARCH:
//                tvABTitle.setText("Search");
                break;
            case SEARCH_FILTER:
//                tvABTitle.setText("Filter & Search");
                break;
        }
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

}
