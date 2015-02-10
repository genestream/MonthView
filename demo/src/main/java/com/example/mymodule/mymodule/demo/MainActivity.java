package com.example.mymodule.mymodule.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import jp.co.genestream.monthview.MonthViewPager;
import jp.co.genestream.monthview.OnDayClickListener;
import jp.co.genestream.monthview.OnMonthChangeListener;

import java.util.Calendar;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView monthTextView = (TextView) findViewById(R.id.month_text_view);
        final MonthViewPager monthViewPager = (MonthViewPager) findViewById(R.id.month_view);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 4);
        calendar.add(Calendar.MONTH, 1);
        monthTextView.setText("" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");

        monthViewPager.setup(calendar, Calendar.WEDNESDAY);
        monthViewPager.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onChange(Calendar calendar) {
                monthTextView.setText("" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
            }
        });
        monthViewPager.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(Calendar calendar) {
                monthViewPager.setup(calendar, Calendar.MONDAY);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
