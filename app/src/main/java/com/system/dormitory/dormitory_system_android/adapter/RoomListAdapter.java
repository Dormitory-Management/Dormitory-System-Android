package com.system.dormitory.dormitory_system_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activity_main.Manager.Room;

import java.util.ArrayList;

/**
 * Created by 보운 on 2016-06-05.
 */
public class RoomListAdapter extends BaseAdapter {
    private ArrayList<String> student;
    private AQuery aq;
    private Context context;

    public RoomListAdapter(Context context, ArrayList<String> student) {
        this.context = context;
        this.student = student;
    }

    @Override
    public int getCount() {
        return student.size();
    }

    @Override
    public Object getItem(int i) {
        return student.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.room_item, null);

        aq = new AQuery(view);

        aq.id(R.id.student_name).text(student.get(i));
        aq.id(R.id.room_checkbox).checked(!Room.getCheckbox().get(i));

        return view;
    }
}
