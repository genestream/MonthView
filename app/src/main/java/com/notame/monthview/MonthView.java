package com.notame.monthview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.Calendar;

/**
 * @author kgmyshin
 */
public class MonthView extends GridView {

    private Calendar mDay;
    private MonthGridAdapter mAdapter;
    private OnDayClickListener mOnDayClickListener;
    public MonthView(Context context) {
        this(context, null);
    }
    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setNumColumns(7);
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
        if (mAdapter != null) {
            mAdapter.setOnDayClickListener(mOnDayClickListener);
        }
    }

    public void setup(Calendar day, Calendar pointingDay) {
        mDay = (Calendar) day.clone();
        mAdapter = new MonthGridAdapter(getContext(), mDay, pointingDay);
        setAdapter(mAdapter);
        if (mOnDayClickListener != null) {
            mAdapter.setOnDayClickListener(mOnDayClickListener);
        }
    }

    public void setPointingDay(Calendar pointingDay) {
        mAdapter.setPointingDay(pointingDay);
    }

    public int getYear() {
        return mDay.get(Calendar.YEAR);
    }

    public int getMonth() {
        return mDay.get(Calendar.MONTH);
    }

    public Calendar getDay() {
        return mDay;
    }

    public int getLine() {
        return mAdapter.getLine();
    }

    @Override
    public String toString() {
        return "" + mDay.get(Calendar.YEAR) + "/" + (mDay.get(Calendar.MONTH) + 1);
    }
}
