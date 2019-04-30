package com.single.monthview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by xiangcheng on 18/7/5.
 */

public class MonthView extends View {
    private static final String TAG = MonthView.class.getSimpleName();

    private int year;
    private int month;

    private int width;
    private int height;

    private int itemHeight;

    private Paint darkPaint;

    private float itemWidth;

    Paint.FontMetrics fontMetrics;
    float allHeight;

    private static final String[] weekStr = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    //    private List<DakaItem> dakaList;
    private int dakaRadius;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");

    private int spaceDaka;

    private Calendar calendar;

    private int currentRadius;
    private Paint currentPaint;
    public List<Item> items = new ArrayList<>();

    public static class Item {
        public RectF rect;
        public String date;
    }

//    public void setDakaList(List<DakaItem> dakaList) {
//        this.dakaList = dakaList;
//        invalidate();
//        dakaInit = true;
//    }
//
//    //判断那天是不是在打卡里面
//    public boolean checkIsDaka(String date) {
//        if (dakaList != null) {
//            for (int i = 0; i < dakaList.size(); i++) {
//                DakaItem dakaItem = dakaList.get(i);
//                long clockDate = dakaItem.clockDate;
//                Date date1 = new Date();
//                date1.setTime(clockDate);
//                if (date.equals(format.format(date1))) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public MonthView(Context context) {
        this(context, null);
    }

    public void setYearMonth(int year, int month) {
        this.year = year;
        this.month = month;
        invalidate();
    }

    private boolean dakaInit;

    public boolean hasInitDaka() {
        return dakaInit;
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getContext().getResources().getDisplayMetrics());
        currentRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12.5f, getContext().getResources().getDisplayMetrics());
        dakaRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, getContext().getResources().getDisplayMetrics());
        spaceDaka = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getContext().getResources().getDisplayMetrics());
        darkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        darkPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getContext().getResources().getDisplayMetrics()));
        darkPaint.setStrokeWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getContext().getResources().getDisplayMetrics()));
        darkPaint.setColor(Color.parseColor("#333333"));
        currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentPaint.setColor(Color.parseColor("#FFC824"));
        currentPaint.setStyle(Paint.Style.FILL);
        darkPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = darkPaint.getFontMetrics();
        allHeight = fontMetrics.descent - fontMetrics.ascent;
//        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWeek(canvas);
        drawDate(canvas);
    }

    private void drawDate(Canvas canvas) {
        int monthDaysCount = Utils.getMonthDaysCount(year, month);
        int week = Utils.getWeek(year, month);
        //第一行可以显示几个
        int line1 = 7 - (week - 1);//假如周日,line7个
        if (line1 < 7) {
            drawLastMonth(canvas, line1);
        }
        drawLine1(canvas, line1, week);
        for (int i = 0; i < 5; i++) {
            for (int j = (line1 + 1) + i * 7; j < (line1 + 1) + 7 * (i + 1); j++) {//8--->8+7=15     x=
                if (j <= monthDaysCount) {
                    //当天
                    if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) + 1 == month && j == calendar.get(Calendar.DAY_OF_MONTH)) {
                        canvas.drawCircle((j - ((line1 + 1) + i * 7)) * itemWidth + itemWidth / 2, 2 * itemHeight + i * itemHeight + itemHeight / 2, currentRadius, currentPaint);
                        darkPaint.setColor(Color.parseColor("#ffffff"));
                    } else {
                        darkPaint.setColor(Color.parseColor("#333333"));
                    }
                    canvas.drawText(j + "", (j - ((line1 + 1) + i * 7)) * itemWidth + itemWidth / 2, 2 * itemHeight + i * itemHeight + itemHeight / 2 - allHeight / 2 - fontMetrics.ascent, darkPaint);

                    Item item = new Item();
                    float left = (j - ((line1 + 1) + i * 7)) * itemWidth;
                    float top = 2 * itemHeight + i * itemHeight;
                    RectF rect = new RectF(left, top, left + itemWidth, top + itemHeight);
                    item.date = year + "_" + month + "_" + j;
                    item.rect = rect;
                    items.add(item);

//                    if (dakaList != null && dakaList.size() > 0) {
//                        String date = year + "_" + (month < 10 ? "0" + month : month) + "_" + (j < 10 ? "0" + j : j);
//                        if (checkIsDaka(date)) {
//                            darkPaint.setColor(Color.parseColor("#cccccc"));
//                            canvas.drawCircle((j - ((line1 + 1) + i * 7)) * itemWidth + itemWidth / 2, 2 * itemHeight + i * itemHeight + +itemHeight / 2 + allHeight / 2 + spaceDaka, dakaRadius, darkPaint);
//                        }
//                    }
                } else {
                    darkPaint.setColor(Color.parseColor("#cccccc"));
                    canvas.drawText(j - monthDaysCount + "", (j - ((line1 + 1) + i * 7)) * itemWidth + itemWidth / 2, 2 * itemHeight + i * itemHeight + itemHeight / 2 - allHeight / 2 - fontMetrics.ascent, darkPaint);
                }
            }
        }
    }

    private void drawLastMonth(Canvas canvas, int line1) {
        darkPaint.setColor(Color.parseColor("#cccccc"));
        int monthDaysCount = 0;
        //获取上个月的天数
        if (month - 1 == 0) {
            monthDaysCount = Utils.getMonthDaysCount(year - 1, 12);
        } else {
            monthDaysCount = Utils.getMonthDaysCount(year, month - 1);
        }
        for (int i = monthDaysCount - (7 - line1) + 1; i <= monthDaysCount; i++) {//
            canvas.drawText(i + "", (i - (monthDaysCount - (7 - line1) + 1)) * itemWidth + itemWidth / 2, itemHeight + itemHeight / 2 - allHeight / 2 - fontMetrics.ascent, darkPaint);
        }
    }

    private void drawLine1(Canvas canvas, int line1, int week) {
        for (int i = 1; i <= line1; i++) {
            //当天
            if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) + 1 == month && i == calendar.get(Calendar.DAY_OF_MONTH)) {
                canvas.drawCircle((week - 1) * itemWidth + (i - 1) * itemWidth + itemWidth / 2, itemHeight + itemHeight / 2, currentRadius, currentPaint);
                darkPaint.setColor(Color.parseColor("#ffffff"));
            } else {
                darkPaint.setColor(Color.parseColor("#333333"));
            }
            canvas.drawText(i + "", (week - 1) * itemWidth + (i - 1) * itemWidth + itemWidth / 2, itemHeight + itemHeight / 2 - allHeight / 2 - fontMetrics.ascent, darkPaint);
            Item item = new Item();
            float left = (week - 1) * itemWidth + (i - 1) * itemWidth;
            float top = itemHeight;
            RectF rect = new RectF(left, top, left + itemWidth, top + itemHeight);
            item.date = year + "_" + month + "_" + i;
            item.rect = rect;
            items.add(item);
//            if (dakaList != null && dakaList.size() > 0) {
//                String date = year + "_" + (month < 10 ? "0" + month : month) + "_" + (i < 10 ? "0" + i : i);
//                if (checkIsDaka(date)) {
//                    darkPaint.setColor(Color.parseColor("#cccccc"));
//                    canvas.drawCircle((week - 1) * itemWidth + (i - 1) * itemWidth + itemWidth / 2, itemHeight + itemHeight / 2 + allHeight / 2 + spaceDaka, dakaRadius, darkPaint);
//                }
//            }
        }
    }

    private void drawWeek(Canvas canvas) {
        darkPaint.setColor(Color.parseColor("#cccccc"));
        for (int i = 0; i < weekStr.length; i++) {
            canvas.drawText(weekStr[i], i * itemWidth + itemWidth / 2, itemHeight / 2 - allHeight / 2 - fontMetrics.ascent, darkPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = w * 1.0f / 7;
    }

    public int getHight() {
        return itemHeight * 7;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), itemHeight * 7);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).rect.contains(event.getX(), event.getY())) {
                    if (onclickWhere != null) {
                        onclickWhere.onWhere(items.get(i).date);
                        break;
                    }
                }
            }
        }
        return true;
    }

    private OnclickWhere onclickWhere;

    public void setOnclickWhere(OnclickWhere onclickWhere) {
        this.onclickWhere = onclickWhere;
    }

    public interface OnclickWhere {
        void onWhere(String day);
    }
}
