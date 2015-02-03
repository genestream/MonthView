package com.genestream.monthview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.Calendar;

public class MonthGridAdapter extends ArrayAdapter<Calendar> {

    private static final int NUM_OF_DAYS_IN_WEEK = 7;
    private static final Calendar TODAY = Calendar.getInstance();

    private Context mContext;
    private Calendar mDay;
    private Calendar mPointingDay;
    private LayoutInflater mInflater;
    private OnDayClickListener mOnDayClickListener;
    private int mLine = 0;

    public MonthGridAdapter(Context context, Calendar day, Calendar pointingDay) {
        super(context, 0);
        mContext = context;
        mDay = (Calendar) day.clone();
        mPointingDay = (Calendar) pointingDay.clone();
        mDay.set(Calendar.DAY_OF_MONTH, 1);
        mInflater = LayoutInflater.from(mContext);
        init();
    }

    private void init() {
        Calendar iterator = (Calendar) mDay.clone();
        int month = iterator.get(Calendar.MONTH);
        int lastDay = iterator.getActualMaximum(Calendar.DATE);
        iterator.set(Calendar.DAY_OF_WEEK, MonthViewInfo.START);
        if (iterator.get(Calendar.MONTH) == month && iterator.get(Calendar.DATE) > 1) {
            iterator.add(Calendar.DATE, -7);
        }
        boolean finish = false;
        while (!finish) {
            mLine++;
            for (int i = 0; i < NUM_OF_DAYS_IN_WEEK; i++) {
                if (month == iterator.get(Calendar.MONTH) && lastDay == iterator.get(Calendar.DATE)) {
                    finish = true;
                }
                add((Calendar) iterator.clone());
                iterator.add(Calendar.DATE, 1);
            }
        }
        notifyDataSetChanged();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public int getLine() {
        return mLine;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_month_item, null);
        }
        final Calendar day = getItem(position);
        TextView dayView = (TextView) convertView.findViewById(R.id.month_view_item_content);
        dayView.setText("" + day.get(Calendar.DATE));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnDayClickListener != null) {
                    mOnDayClickListener.onDayClick(day);
                }
            }
        });


        if (day.get(Calendar.MONTH) != mDay.get(Calendar.MONTH)) {
            dayView.setTextColor(Color.GRAY);
        } else if (isSameDay(day, mPointingDay)) {
            dayView.setBackgroundResource(R.drawable.point);
            dayView.setTextColor(Color.WHITE);
        } else if (isSameDay(day, TODAY)) {
            dayView.setBackgroundResource(R.drawable.today);
            dayView.setTextColor(Color.WHITE);
        } else if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayView.setTextColor(Color.RED);
        } else if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayView.setTextColor(Color.BLUE);
        } else {
            dayView.setBackgroundColor(Color.TRANSPARENT);
            dayView.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                && (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE));
    }
}
