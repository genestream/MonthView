package jp.co.genestream.monthview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.genestream.monthview.R;

import java.util.Calendar;

/**
 * @author kgmyshin
 */
public class MonthView extends LinearLayout {

    private GridView mDayGridView;
    private Calendar mDay;
    private MonthGridAdapter mAdapter;
    private OnDayClickListener mOnDayClickListener;

    public MonthView(Context context) {
        super(context);
        init();
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_month, this);
        mDayGridView = (GridView) view.findViewById(R.id.day_grid_view);

        TextView[] headerItems = {
                (TextView) view.findViewById(R.id.first_day),
                (TextView) view.findViewById(R.id.second_day),
                (TextView) view.findViewById(R.id.third_day),
                (TextView) view.findViewById(R.id.fourth_day),
                (TextView) view.findViewById(R.id.fifth_day),
                (TextView) view.findViewById(R.id.sixth_day),
                (TextView) view.findViewById(R.id.seventh_day)
        };
        int pos = MonthViewInfo.START;
        for (TextView headerItem : headerItems) {
            initHeaderItem(headerItem, pos++);
        }
    }

    private void initHeaderItem(TextView headerItem, int pos) {
        int correctPos = (pos > 7) ? (pos % 7) : pos;
        Context context = getContext();
        switch (correctPos) {
            case Calendar.SUNDAY:
                headerItem.setText(context.getString(R.string.sunday));
                headerItem.setTextColor(Color.RED);
                break;
            case Calendar.MONDAY:
                headerItem.setText(context.getString(R.string.monday));
                break;
            case Calendar.TUESDAY:
                headerItem.setText(context.getString(R.string.tuesday));
                break;
            case Calendar.WEDNESDAY:
                headerItem.setText(context.getString(R.string.wednesday));
                break;
            case Calendar.THURSDAY:
                headerItem.setText(context.getString(R.string.thursday));
                break;
            case Calendar.FRIDAY:
                headerItem.setText(context.getString(R.string.friday));
                break;
            case Calendar.SATURDAY:
                headerItem.setText(context.getString(R.string.saturday));
                headerItem.setTextColor(Color.BLUE);
                break;
            default:
                break;
        }
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
        mDayGridView.setAdapter(mAdapter);
        if (mOnDayClickListener != null) {
            mAdapter.setOnDayClickListener(mOnDayClickListener);
        }
    }

    public Calendar getDay() {
        return mDay;
    }

}
