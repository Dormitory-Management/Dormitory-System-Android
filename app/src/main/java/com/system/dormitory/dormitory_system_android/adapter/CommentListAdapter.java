package com.system.dormitory.dormitory_system_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.data.Comment;

import java.util.ArrayList;

/**
 * Created by secret on 6/14/16.
 */
public class CommentListAdapter extends BaseAdapter {
    private ArrayList<Comment> comments;
    private AQuery aq;
    private Context context;

    public CommentListAdapter(Context context, ArrayList<Comment> board) {
        this.context = context;
        this.comments = board;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.comment_item, null);

        aq = new AQuery(view);

        aq.id(R.id.comment_content).text(comments.get(i).getSno() + " : " +comments.get(i).getComment());

        return view;
    }
}
