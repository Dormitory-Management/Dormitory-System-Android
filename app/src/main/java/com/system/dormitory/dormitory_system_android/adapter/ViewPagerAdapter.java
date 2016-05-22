package com.system.dormitory.dormitory_system_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.content.BoardActivity;
import com.system.dormitory.dormitory_system_android.content.NoticeActivity;
import com.system.dormitory.dormitory_system_android.content.WritingActivity;
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
                listView.setOnItemClickListener(noticeClick);
                break;
            case 1:
                v = inflater.inflate(R.layout.activity_board, null);
                listView = (ListView) v.findViewById(R.id.board_list);
                listView.setAdapter(new ListViewAdapter(context, DataManager.getInstance().getBoardItems()));
                listView.setOnItemClickListener(boardClick);
                FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
                fab.attachToListView(listView);
                fab.setOnClickListener(floatingButtonClicked);
                break;
            case 2:
                v = inflater.inflate(R.layout.activity_point, null);
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

    ListView.OnItemClickListener noticeClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent content = new Intent(context, NoticeActivity.class);
            content.putExtra("Item", DataManager.getInstance().getNoticeItems().get(i));
            content.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(content);
        }
    };

    ListView.OnItemClickListener boardClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent content = new Intent(context, BoardActivity.class);
            content.putExtra("Item", DataManager.getInstance().getBoardItems().get(i));
            content.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(content);
        }
    };

    FloatingActionButton.OnClickListener floatingButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent writing = new Intent(context, WritingActivity.class);
            writing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(writing);
        }
    };
}
