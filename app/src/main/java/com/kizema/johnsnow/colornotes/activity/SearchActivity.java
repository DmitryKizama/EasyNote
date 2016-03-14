package com.kizema.johnsnow.colornotes.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.adapters.ColorsAdapter;
import com.kizema.johnsnow.colornotes.appviews.DualProgressBar;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends BaseActivity implements DualProgressBar.OnDualProgressListener,
        ColorsAdapter.OnColorCLick {

    private View ivBack, ivFilter;
    private TextView tvABTitle;

    private DualProgressBar ageProgressBar;
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
        initColorPicker();

        openFiletr(status == Status.SEARCH_FILTER);
    }

    private void initFilter(){
        initAgeProgressbar();
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
