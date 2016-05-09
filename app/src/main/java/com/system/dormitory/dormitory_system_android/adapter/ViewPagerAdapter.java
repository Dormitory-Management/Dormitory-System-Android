package com.system.dormitory.dormitory_system_android.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.data.BoardItem;
import com.system.dormitory.dormitory_system_android.data.DataManager;

/**
 * Created by 보운 on 2016-05-07.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private int MAX_PAGE;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context) {
        MAX_PAGE = 3;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        ListView listView = null;

        switch (position) {
            case 0:
                v = inflater.inflate(R.layout.activity_notice, null);
                listView = (ListView) v.findViewById(R.id.notice_list);
                listView.setAdapter(new ListViewAdapter(context, DataManager.getInstance().getNoticeItems()));
                break;
            case 1:
                v = inflater.inflate(R.layout.activity_point, null);
                break;
            case 2:
                v = inflater.inflate(R.layout.activity_board, null);
                listView = (ListView) v.findViewById(R.id.board_list);
                listView.setAdapter(new ListViewAdapter(context, DataManager.getInstance().getBoardItems()));
                break;
        }

        container.addView(v);

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
