package com.system.dormitory.dormitory_system_android.helper;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.dormitory.dormitory_system_android.R;

/**
 * Created by 보운 on 2016-05-07.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private int MAX_PAGE;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context context) {
        MAX_PAGE = 3;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        switch (position) {
            case 0:
                v = inflater.inflate(R.layout.activity_notice, null);
                break;
            case 1:
                v = inflater.inflate(R.layout.activity_point, null);
                break;
            case 2:
                v = inflater.inflate(R.layout.activity_board, null);
                break;
        }

        container.addView(v, 0);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return MAX_PAGE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
