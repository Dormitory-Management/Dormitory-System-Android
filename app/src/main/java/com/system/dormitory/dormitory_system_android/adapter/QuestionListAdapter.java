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


public class QuestionListAdapter extends BaseAdapter {
    private ArrayList<? extends Item> question;
    private AQuery aq;
    private Context context;

    public QuestionListAdapter(Context context, ArrayList<? extends Item> question) {
        this.context = context;
        this.question = question;
    }

    @Override
    public int getCount() {
        return question.size();
    }

    @Override
    public Object getItem(int i) {
        return question.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.board_item, null);

        aq = new AQuery(view);

        aq.id(R.id.item_title).text(question.get(i).getTitle());
        aq.id(R.id.item_content).text(question.get(i).getContent());
        aq.id(R.id.item_room_number).text(String.valueOf(question.get(i).getRoomNumber()) + "호");
        aq.id(R.id.item_person).text(""+question.get(i).getSno());
        aq.id(R.id.item_time).text(question.get(i).getTime().substring(0,5));

        return view;
    }
}
