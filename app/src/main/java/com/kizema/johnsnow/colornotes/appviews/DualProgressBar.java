package com.kizema.johnsnow.colornotes.appviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.appviews.parsers.TextViewParser;
import com.kizema.johnsnow.colornotes.helper.ColorFilterStateDrawable;
import com.kizema.johnsnow.colornotes.helper.ColorHelper;
import com.kizema.johnsnow.colornotes.helper.UIHelper;

import java.util.HashMap;
import java.util.Map;

public class DualProgressBar  extends RelativeLayout {

    private static final float SCALE = 1.2f;
    private static final float NORM = 1.f;

    private static final int OFFSET = 30;
    private static final int DIST_BTW_POINTERS = 4;
    private static final int BOTTOM_OFFSET = 5;

    private static final int SELECTOR_H = 30;
    private static final int SELECTOR_W = 5;
    private static final int SELECTOR_W_DIV_2 = 1+SELECTOR_W/2;

    private int min = 0, max = 100;

    private AppImageView selectorLeft, selectorRight, selectionWidget;
    private AppTextView textLeft, textRight;

    private OnDualProgressListener onDualProgressListener;

    public interface OnDualProgressListener{
        void onProgressChanged(TextView tvSelected, float progressPersent);
        void onDoneDateEdit();
    }

    public DualProgressBar(Context context) {
        super(context);

        init();
    }

    public DualProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        init(attrs);
    }

    public DualProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        init(attrs);

    }

    public void setOnDualProgressListener(OnDualProgressListener onDualProgressListener){
        this.onDualProgressListener = onDualProgressListener;
    }

    private void init(AttributeSet attrs) {
        int l, r;
        TypedArray custom = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.DualProgressBar, 0, 0);
        try {
            min = custom.getInteger(R.styleable.DualProgressBar_DualProgressBarMin, 0);
            max = custom.getInteger(R.styleable.DualProgressBar_DualProgressBarMax, 100);
            l = custom.getInteger(R.styleable.DualProgressBar_ValLeft, 15);
            r = custom.getInteger(R.styleable.DualProgressBar_ValRight, 30);
        } finally {
            custom.recycle();
        }

        setLeftProgress(l);
        setRightProgress(r);
    }

    private void init() {
        initLine();

        selectionWidget = initSelectionWidget();

        selectorLeft = initSelector();
        selectorRight = initSelector();

        textLeft = initText();
        textRight = initText();

        textLeft.setPadding(UIHelper.getPixel(10), 0, 0, 0);
        textRight.setPadding(0, 0, UIHelper.getPixel(10), 0);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) textRight.getLayoutParams();
        p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        setOnTouchListener(new WidgetTouchListener());
    }

    public void setLeftProgress(int progress) {
        if (min > progress || progress > max) {
            return;
        }

        float pos = (float) (progress - min) / (float) (max - min);
        pos = getElementWidth() * pos + UIHelper.getPixel(OFFSET);

        setSelectorLeftPosition(pos);
//        setTextWidgetPos(textLeft, pos);
        textLeft.setText("" + getProgress(selectorLeft));
        if (onDualProgressListener != null){
            onDualProgressListener.onProgressChanged(textLeft, (float) getProgress(textLeft) / (max - min));
        }
    }

    public void setRightProgress(int progress) {
        if (min > progress || progress > max) {
            return;
        }

        float pos = (float) (progress - min) / (float) (max - min);
        pos = getElementWidth() * pos + UIHelper.getPixel(OFFSET);
        setSelectorRightPosition(pos);
//        setTextWidgetPos(textRight, pos);
        textRight.setText("" + getProgress(selectorRight));

        if (onDualProgressListener != null){
            onDualProgressListener.onProgressChanged(textRight, (float) getProgress(textRight) / (max - min));
        }
    }

    private void setSelectorLeftPosition(float x){
        selectorLeft.setX(x);
        selectionWidget.setX(x);

        ViewGroup.LayoutParams p = selectionWidget.getLayoutParams();
        p.width = (int) ( selectorRight.getX()-selectorLeft.getX());
        selectionWidget.setLayoutParams(p);
    }

    private void setSelectorRightPosition(float x){
        selectorRight.setX(x);

        ViewGroup.LayoutParams p = selectionWidget.getLayoutParams();
        p.width = (int) ( x-selectorLeft.getX());
        selectionWidget.setLayoutParams(p);
    }


    private void setTextWidgetPos(final TextView tv, final float x){
        if (tv.getWidth() == 0){
            tv.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    tv.setX(x - tv.getWidth() / 2 + UIHelper.getPixel(SELECTOR_W_DIV_2));
                    tv.removeOnLayoutChangeListener(this);
                }
            });
        }
        tv.setX(x - tv.getWidth() / 2 + UIHelper.getPixel(SELECTOR_W_DIV_2));
    }

    public int getProgress(View selector) {
        float x = selector.getX() - UIHelper.getPixel(OFFSET);
        return (int) ((float) (max - min) / getElementWidth() * x + min);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    private void initLine() {
        int w = UIHelper.getW();

        AppImageView line = new AppImageView(getContext());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w, UIHelper.getPixel(6));
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p.bottomMargin = UIHelper.getPixel(SELECTOR_H)/2 +  UIHelper.getPixel(BOTTOM_OFFSET) - UIHelper.getPixel(3);
        line.setLayoutParams(p);
        line.setImageDrawable(new ColorDrawable(getColor(R.color.ABIconPressed)));
        addView(line);
    }

    private int getColor(int color){
        return getContext().getResources().getColor(color);
    }

    private AppTextView initText() {
        AppTextView text = new AppTextView(getContext());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p.bottomMargin = (int) ( UIHelper.getPixel(SELECTOR_H) * 1.3f) +  UIHelper.getPixel(BOTTOM_OFFSET);
        text.setLayoutParams(p);

        text.setFont(TextViewParser.HelveticaNeueLightFont);
        text.setTextSize(24);
        text.setTextColor(getColor(R.color.TextColorTitle));
        addView(text);

        return text;
    }

    private AppImageView initSelector() {
        AppImageView selector = new AppImageView(getContext());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(UIHelper.getPixel(SELECTOR_W), UIHelper.getPixel(SELECTOR_H));
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p.bottomMargin = UIHelper.getPixel(BOTTOM_OFFSET);
        selector.setLayoutParams(p);

        Map<Integer, Integer> map = new HashMap();
        map.put(android.R.attr.state_selected, getColor(R.color.abBack));

        Drawable dr = new ColorFilterStateDrawable(ColorHelper.getColorDrawable(), getColor(R.color.black), map);
        selector.setImageDrawable(dr);
        addView(selector);

        return selector;
    }

    private AppImageView initSelectionWidget(){
        AppImageView selector = new AppImageView(getContext());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(0, UIHelper.getPixel(SELECTOR_H));
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p.bottomMargin = UIHelper.getPixel(BOTTOM_OFFSET);
        selector.setLayoutParams(p);

        Drawable dr = new ColorFilterStateDrawable(ColorHelper.getColorDrawable(), getColor(R.color.ABIconPressed), null);
        selector.setImageDrawable(dr);
        addView(selector);

        return selector;
    }

    private class WidgetTouchListener implements OnTouchListener {

        private AppImageView selectedSelector;
        private TextView selectedTV;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if (Math.abs(x - selectorLeft.getX()) < Math.abs(x - selectorRight.getX())) {
                        selectedSelector = selectorLeft;
                        selectedTV = textLeft;
                    } else {
                        selectedSelector = selectorRight;
                        selectedTV = textRight;
                    }

                    selectedSelector.setPivotX(0.5f*selectedSelector.getWidth());
                    selectedSelector.setPivotY(0.5f*selectedSelector.getHeight());

                    selectedSelector.setScaleX(SCALE);
                    selectedSelector.setScaleY(SCALE);

                    selectedSelector.setSelected(true);
                    break;

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    selectedSelector.setScaleX(NORM);
                    selectedSelector.setScaleY(NORM);

                    selectedSelector.setSelected(false);
                    break;
            }

            if (selectedSelector == selectorLeft){
                if ( x > selectorRight.getX() - UIHelper.getPixel(DIST_BTW_POINTERS)){
                    x = selectorRight.getX() - UIHelper.getPixel(DIST_BTW_POINTERS);
                }
            } else {
                if ( x < selectorLeft.getX() + UIHelper.getPixel(DIST_BTW_POINTERS)){
                    x = selectorLeft.getX() + UIHelper.getPixel(DIST_BTW_POINTERS);
                }
            }

            if (x < UIHelper.getPixel(OFFSET)) {
                if (selectedSelector == selectorLeft){
                    x = UIHelper.getPixel(OFFSET);
                }

            } else if (x > getElementWidth() + UIHelper.getPixel(OFFSET)) {
                if (selectedSelector == selectorRight){
                    x = getElementWidth() + UIHelper.getPixel(OFFSET);
                }
            }

            if (selectedSelector == selectorLeft){
                setSelectorLeftPosition(x);
            } else {
                setSelectorRightPosition(x);
            }

//            setTextWidgetPos(selectedTV, x);
            selectedTV.setText("" + getProgress(selectedSelector));
            if (onDualProgressListener != null){
                onDualProgressListener.onProgressChanged(selectedTV, (float) getProgress(selectedSelector) / (max - min));
            }

            if (event.getAction() == MotionEvent.ACTION_CANCEL ||
                    event.getAction() == MotionEvent.ACTION_UP) {
                if (onDualProgressListener != null) {
                    onDualProgressListener.onDoneDateEdit();
                }
            }

            return true;
        }
    }

    public int getLeftValue(){
        return Integer.parseInt(textLeft.getText().toString());
    }

    public int getRightValue(){
        return Integer.parseInt(textRight.getText().toString());
    }

    public int getElementWidth() {
        return UIHelper.getW() - 2 * UIHelper.getPixel(OFFSET);
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("left", (int) selectorLeft.getX());
        bundle.putInt("right", (int) selectorRight.getX());

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            int left = bundle.getInt("left");
            setSelectorLeftPosition(left);
//            setTextWidgetPos(textLeft, left);
            textLeft.setText("" + getProgress(selectorLeft));

            int right = bundle.getInt("right");
            setSelectorRightPosition(right);
//            setTextWidgetPos(textRight, right);
            textRight.setText("" + getProgress(selectorRight));

            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

}
