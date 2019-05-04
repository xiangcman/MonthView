package com.single.monthview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by xiangcheng on 18/7/5.
 */

public class CalendarViewPager extends ViewPager {
    private static final String TAG = CalendarViewPager.class.getSimpleName();

    public CalendarViewPager(Context context) {
        this(context, null);
    }

    Calendar instance;

    public CalendarViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setYear();
    }

    private int current;//下标从0开始的
    int year;
    int month;

    ArrayList<View> monthViews;
    private OnDayClick onDayClick;
    private int preYear;
    private int preMonth;
    private int nextYear;
    private int nextMonth;

    public void setOnDayClick(OnDayClick onDayClick) {
        this.onDayClick = onDayClick;
    }

    public interface OnDayClick {
        void onClick(String day);
    }

    public void setYear() {
        instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
        int index = 0;
        monthViews = new ArrayList<>();
        if (month == 12) {
            nextYear = year + 1;
            nextMonth = 1;
            preYear = year;
            preMonth = month - 1;
        } else if (month == 1) {
            preYear = year - 1;
            preMonth = 12;
            nextYear = year;
            nextMonth = month + 1;
        } else {
            preYear = year;
            preMonth = month - 1;
            nextYear = year;
            nextMonth = month + 1;
        }
        MonthView monthView1 = new MonthView(getContext());
        monthView1.setYearMonth(preYear, preMonth);

        MonthView monthView2 = new MonthView(getContext());
        monthView2.setYearMonth(year, month);
        MonthView monthView3 = new MonthView(getContext());
        monthView3.setYearMonth(nextYear, nextMonth);
        monthViews.add(monthView1);
        monthViews.add(monthView2);
        monthViews.add(monthView3);
        monthView1.setOnclickWhere(new MonthView.OnclickWhere() {
            @Override
            public void onWhere(String day) {
                if (onDayClick != null) {
                    onDayClick.onClick(day);
                }
            }
        });
        monthView2.setOnclickWhere(new MonthView.OnclickWhere() {
            @Override
            public void onWhere(String day) {
                if (onDayClick != null) {
                    onDayClick.onClick(day);
                }
            }
        });
        monthView3.setOnclickWhere(new MonthView.OnclickWhere() {
            @Override
            public void onWhere(String day) {
                if (onDayClick != null) {
                    onDayClick.onClick(day);
                }
            }
        });
        //这里默认是第一个item
        current = Integer.MAX_VALUE / 2 + 1;
        setAdapter(new ViewPagerAdapter(monthViews));
        setCurrentItem(current);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current = position;
                MonthView view = (MonthView) monthViews.get(position % 3);
                year = view.getYear();
                month = view.getMonth();
                if (month == 12) {
                    nextYear = year + 1;
                    nextMonth = 1;
                    preYear = year;
                    preMonth = month - 1;
                } else if (month == 1) {
                    preYear = year - 1;
                    preMonth = 12;
                    nextYear = year;
                    nextMonth = month + 1;
                } else {
                    preYear = year;
                    preMonth = month - 1;
                    nextYear = year;
                    nextMonth = month + 1;
                }
                position = position % 3;
                if (position == 0) {
                    MonthView nextMonthView = (MonthView) monthViews.get(position + 1);
                    nextMonthView.setYearMonth(nextYear, nextMonth);
                    MonthView preMonthView = (MonthView) monthViews.get(position + 2);
                    preMonthView.setYearMonth(preYear, preMonth);
                } else if (position == 1) {
                    MonthView nextMonthView = (MonthView) monthViews.get(position + 1);
                    nextMonthView.setYearMonth(nextYear, nextMonth);
                    MonthView preMonthView = (MonthView) monthViews.get(position - 1);
                    preMonthView.setYearMonth(preYear, preMonth);
                } else if (position == 2) {
                    MonthView nextMonthView = (MonthView) monthViews.get(position - 2);
                    nextMonthView.setYearMonth(nextYear, nextMonth);
                    MonthView preMonthView = (MonthView) monthViews.get(position - 1);
                    preMonthView.setYearMonth(preYear, preMonth);
                }
                view.setDakaList(dakaList);
                if (onMonthChange != null) {
                    onMonthChange.onChange(year, month);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setOnMonthChange(OnMonthChange onMonthChange) {
        this.onMonthChange = onMonthChange;
    }

    private OnMonthChange onMonthChange;

    public interface OnMonthChange {
        void onChange(int year, int month);
    }

    public int getCurrentYear() {
        return year;
    }

    public int getCuurentMonth() {
        return month;
    }

    public void toNext() {
        current++;
        setCurrentItem(current);
    }

    public void toPre() {
        current--;
        setCurrentItem(current);
    }

    private List<DakaItem> dakaList;

    public void setDakaList(List<DakaItem> dakaList) {
        this.dakaList = dakaList;
        MonthView view = (MonthView) monthViews.get(current % 3);
        view.setDakaList(dakaList);
    }
//
//    public boolean hasCurrentMonthDaka() {
//        MonthView view = (MonthView) monthViews.get(current);
//        Logger.d(TAG, "view.hasInitDaka():" + view.hasInitDaka());
//        return view.hasInitDaka();
//    }
//
//    public boolean hasThisDayDaka(String day) {
//        String[] split = day.split("_");
//        int year = Integer.parseInt(split[0]);
//        int month = Integer.parseInt(split[1]);
//        int meDay = Integer.parseInt(split[2]);
//        String date = year + "_" + (month < 10 ? "0" + month : month) + "_" + (meDay < 10 ? "0" + meDay : meDay);
//        MonthView view = (MonthView) monthViews.get(current);
//        return view.checkIsDaka(date);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
