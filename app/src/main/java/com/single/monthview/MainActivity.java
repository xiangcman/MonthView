package com.single.monthview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalendarViewPager calendarViewPager = findViewById(R.id.viewpager);
        final TextView selectDate = findViewById(R.id.select_date);
        int cuurentMonth = calendarViewPager.getCuurentMonth();
        selectDate.setText(calendarViewPager.getCurrentYear() + "年" + (cuurentMonth < 10 ? "0" + cuurentMonth : cuurentMonth) + "月");
        calendarViewPager.setOnMonthChange(new CalendarViewPager.OnMonthChange() {
            @Override
            public void onChange(int year, int month) {
                String strMonth;
                if (month < 10) {
                    strMonth = "0" + month;
                } else {
                    strMonth = "" + month;
                }
                selectDate.setText(year + "年" + strMonth + "月");
            }
        });
        calendarViewPager.setOnDayClick(new CalendarViewPager.OnDayClick() {
            @Override
            public void onClick(String day) {
                Toast.makeText(MainActivity.this, "日期:" + day, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarViewPager.toPre();
            }
        });
        findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarViewPager.toNext();
            }
        });
    }
}
