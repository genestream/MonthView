package com.notame.monthview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final TextView debug = (TextView) findViewById(R.id.debug);
        MonthViewPager monthViewPager = (MonthViewPager) findViewById(R.id.mont_view_pager);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 4);
        calendar.add(Calendar.MONTH, 1);
        monthViewPager.setup(calendar);
        monthViewPager.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onChange(Calendar calendar) {
                debug.setText("" + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
            }
        });
        monthViewPager.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(Calendar calendar) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
