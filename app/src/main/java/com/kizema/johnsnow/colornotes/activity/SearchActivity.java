package com.kizema.johnsnow.colornotes.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.appviews.DualProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends BaseActivity implements DualProgressBar.OnDualProgressListener {

    private View ivBack, ivFilter;
    private TextView tvABTitle;

    private DualProgressBar ageProgressBar;

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

        openFiletr(status == Status.SEARCH_FILTER);
    }

    private void initFilter(){
        initAgeProgressbar();
    }

    private void initAgeProgressbar(){
        ageProgressBar = (DualProgressBar) findViewById(R.id.ageProgressBar);
        ageProgressBar.setOnDualProgressListener(this);
        ageProgressBar.setLeftProgress(20);
        ageProgressBar.setRightProgress(50);
    }

    private void initActionBar(){
        ivBack = findViewById(R.id.ivBack);
        ivFilter = findViewById(R.id.ivFilter);
        tvABTitle = (TextView) findViewById(R.id.tvABTitle);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
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
    }


    private void setTvABTitle(){
        switch (status){
            case SEARCH:
                tvABTitle.setText("Search");
                break;
            case SEARCH_FILTER:
                tvABTitle.setText("Filter & Search");
                break;
        }
    }

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

    @Override
    public void onProgressChanged(TextView tvSelected, float progress) {
        long val = (long)(progress * (rightDateMs - leftDateMs) + leftDateMs);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(val);

        tvSelected.setText(formatter.format(calendar.getTime()));
    }

    @Override
    public void onDoneEdit() {

    }

}
