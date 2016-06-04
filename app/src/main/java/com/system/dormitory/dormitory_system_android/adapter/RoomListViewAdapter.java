package com.system.dormitory.dormitory_system_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;

import java.util.ArrayList;

/**
 * Created by 보운 on 2016-06-03.
 */
public class RoomListViewAdapter extends BaseAdapter {
    private ArrayList<DormitoryRoom> room;
    private AQuery aq;
    private Context context;

    public RoomListViewAdapter(Context context, ArrayList<DormitoryRoom> room) {
        this.context = context;
        this.room = room;
    }

    @Override
    public int getCount() {
        return room.size();
    }

    @Override
    public Object getItem(int i) {
        return room.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.room_check_item, null);

        aq = new AQuery(view);

        aq.id(R.id.room_check_number).text(String.valueOf(room.get(i).getRoom()) + "호");

        return view;
    }
}
