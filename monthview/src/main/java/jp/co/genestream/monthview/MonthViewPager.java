package jp.co.genestream.monthview;

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
    private OnMonthChangeListener mOnMonthChangeListener;
    private OnDayClickListener mOnDayClickListener;
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
        super(context);
        init();
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
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
        mOnMonthChangeListener = onMonthChangeListener;
        mAdapter.setOnMonthChangeListener(mOnMonthChangeListener);
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
        mAdapter.setOnDayClickListener(mOnDayClickListener);
    }

    public void setup(Calendar pointingDay, int startDay) {
        MonthViewInfo.START = startDay;
        mScrollEnable = false;
        setAdapter(null);
        setOnPageChangeListener(null);
        mAdapter = new MonthViewPagerAdapter(getContext(), this);
        setAdapter(mAdapter);
        mAdapter.setup(pointingDay);
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(MonthViewPagerAdapter.POINTING, false);
                mScrollEnable = true;
                setOnPageChangeListener(mOnPageChangeListener);
            }
        });
        mAdapter.setOnMonthChangeListener(mOnMonthChangeListener);
        mAdapter.setOnDayClickListener(mOnDayClickListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // find the first child view
        View view;
        if (getChildCount() > 1) {
            view = getChildAt(1);
        } else {
            view = getChildAt(0);
        }
        if (view != null) {
            // measure the first child view with the specified measure spec
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
    }

    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}
