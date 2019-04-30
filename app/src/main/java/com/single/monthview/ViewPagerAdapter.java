package com.single.monthview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    //界面列表
    private ArrayList<View> views;

    public ViewPagerAdapter(ArrayList<View> views) {
        this.views = views;
    }

    /**
     * 获得当前界面数
     */
    @Override
    public int getCount() {
//        if (views != null) {
//            return views.size();
//        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //当views的数量小于等于三时，此处会报异常，此处把异常catch到不处理，然后destroyItem不移除
//    @Override
//    public Object instantiateItem(ViewGroup container, final int position) {
//        try {
//            container.addView(views.get(position % views.size()));
//        } catch (Exception e) {
//        }
//        return views.get(position % views.size());
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        if (views.size() > 3) {
//            container.removeView(views.get(position % views.size()));
//        }
//    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //Warning：不要在这里调用removeView
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= views.size();
        if (position < 0) {
            position = views.size() + position;
        }
        View view = views.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }

}
