package jp.co.genestream.monthview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * @author kgmyshin
 */
public class MonthViewPagerAdapter extends PagerAdapter {

    public static final int PREV = 0;
    public static final int POINTING = 1;
    public static final int NEXT = 2;
    private static final int NUM = 3;

    private Calendar mPointingDay;
    private Calendar mCalendar;
    private Context mContext;
    private ViewPager mParent;

    private MonthView[] mMonthItemViews = new MonthView[NUM];
    private OnMonthChangeListener mOnMonthChangeListener;
    private OnDayClickListener mOnDayClickListener;

    public MonthViewPagerAdapter(Context context, ViewPager parent) {
        mContext = context;
        mParent = parent;
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        mOnMonthChangeListener = onMonthChangeListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
        for (MonthView monthView : mMonthItemViews) {
            if (monthView != null) {
                monthView.setOnDayClickListener(mOnDayClickListener);
            }
        }
    }

    public void setup(Calendar pointingDay) {
        mPointingDay = (Calendar) pointingDay.clone();
        refresh(mPointingDay);
    }

    private void refresh(Calendar day) {

        for (int i = PREV; i <= NEXT; i++) {
            destroyItem(mParent, i, mMonthItemViews[i]);
        }

        mCalendar = (Calendar) day.clone();
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar iteratorCalendar = (Calendar) mCalendar.clone();
        iteratorCalendar.add(Calendar.MONTH, -1);
        MonthView prevMonthView = makeMonthItemView(iteratorCalendar);
        mMonthItemViews[PREV] = prevMonthView;

        iteratorCalendar.add(Calendar.MONTH, 1);
        MonthView pointingMonthView = makeMonthItemView(iteratorCalendar);
        mMonthItemViews[POINTING] = pointingMonthView;

        iteratorCalendar.add(Calendar.MONTH, 1);
        MonthView nextMonthView = makeMonthItemView(iteratorCalendar);
        mMonthItemViews[NEXT] = nextMonthView;
        notifyDataSetChanged();
    }

    public void onChange(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        if (month == mMonthItemViews[PREV].getDay().get(Calendar.MONTH)) {
            destroyItem(mParent, NEXT, mMonthItemViews[NEXT]);

            mMonthItemViews[NEXT] = mMonthItemViews[POINTING];
            mMonthItemViews[POINTING] = mMonthItemViews[PREV];

            Calendar newPrevCalendar = (Calendar) mMonthItemViews[PREV].getDay().clone();
            newPrevCalendar.add(Calendar.MONTH, -1);
            MonthView prevMonthView = makeMonthItemView(newPrevCalendar);

            mMonthItemViews[PREV] = prevMonthView;
            notifyDataSetChanged();
            mParent.setCurrentItem(POINTING);

        } else if (month == mMonthItemViews[NEXT].getDay().get(Calendar.MONTH)) {
            destroyItem(mParent, PREV, mMonthItemViews[PREV]);

            mMonthItemViews[PREV] = mMonthItemViews[POINTING];
            mMonthItemViews[POINTING] = mMonthItemViews[NEXT];

            Calendar newNextCalendar = (Calendar) mMonthItemViews[NEXT].getDay().clone();
            newNextCalendar.add(Calendar.MONTH, 1);
            MonthView nextMonthView = makeMonthItemView(newNextCalendar);

            mMonthItemViews[NEXT] = nextMonthView;
            notifyDataSetChanged();
            mParent.setCurrentItem(POINTING);
        } else {
            refresh(calendar);
        }

        if (mOnMonthChangeListener != null) {
            mOnMonthChangeListener.onChange(calendar);
        }
    }

    private MonthView makeMonthItemView(Calendar day) {
        MonthView monthView = new MonthView(mContext);
        monthView.setup(day, mPointingDay);
        monthView.setOnDayClickListener(mOnDayClickListener);
        return monthView;
    }

    public MonthView getCurrentItem() {
        return mMonthItemViews[POINTING];
    }

    public MonthView getItem(int position) {
        return mMonthItemViews[position];
    }

    @Override
    public int getCount() {
        return NUM;
    }

    @Override
    public int getItemPosition(Object object) {
        for (int i = 0; i < NUM; i++) {
            if (mMonthItemViews[i].equals(object)) {
                return i;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mMonthItemViews == null || mMonthItemViews[position] == null) {
            return null;
        }
        container.addView(mMonthItemViews[position]);
        return mMonthItemViews[position];
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }
}