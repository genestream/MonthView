package com.notame.monthview;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * @author kgmyshin
 */
public class MonthViewPager extends ViewPager {

    private boolean mScrollEnable = true;
    private MonthViewPagerAdapter mAdapter;
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int i) {
            setOnPageChangeListener(null);
            mScrollEnable = false;
            final MonthView selectedMonthView = mAdapter.getItem(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.onChange(selectedMonthView.getDay());
                    setOnPageChangeListener(mOnPageChangeListener);
                    mScrollEnable = true;
                }
            }, 400);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public MonthViewPager(Context context) {
        this(context, null);
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mAdapter = new MonthViewPagerAdapter(getContext(), this);
        setAdapter(mAdapter);
        setOnPageChangeListener(mOnPageChangeListener);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(100);
        layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
                mScrollEnable = false;
            }

            @Override
            public void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
                mScrollEnable = true;
            }
        });
        setLayoutTransition(layoutTransition);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mScrollEnable) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)  {
        if (mScrollEnable) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        mAdapter.setOnMonthChangeListener(onMonthChangeListener);
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mAdapter.setOnDayClickListener(onDayClickListener);
    }

    public void setup(Calendar pointingDay) {
        mAdapter.setup(pointingDay);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAdapter != null && mAdapter.getCurrentItem() != null) {
            MonthView current = mAdapter.getCurrentItem();
            current.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int height = current.getMeasuredHeight() * current.getLine();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
