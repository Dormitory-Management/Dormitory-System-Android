package com.system.dormitory.dormitory_system_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.ibm.icu.text.SimpleDateFormat;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.data.Item;

import java.util.ArrayList;

/**
 * Created by BoWoon on 2016-06-06.
 */
public class NoticeListAdapter extends BaseAdapter {
    private ArrayList<? extends Item> board;
    private AQuery aq;
    private Context context;

    public NoticeListAdapter(Context context, ArrayList<? extends Item> board) {
        this.context = context;
        this.board = board;
    }

    @Override
    public int getCount() {
        return board.size();
    }

    @Override
    public Object getItem(int i) {
        return board.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.notice_item, null);

        aq = new AQuery(view);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

        aq.id(R.id.item_title).text(board.get(i).getTitle());
        aq.id(R.id.item_content).text(board.get(i).getContent());
        aq.id(R.id.item_person).text(board.get(i).getName());
        aq.id(R.id.item_time).text(dateFormat.format(board.get(i).getDate()));

        return view;
    }
}
