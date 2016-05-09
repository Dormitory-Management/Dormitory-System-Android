package com.system.dormitory.dormitory_system_android.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.ListViewAdapter;
import com.system.dormitory.dormitory_system_android.data.BoardItem;

import java.util.ArrayList;

/**
 * Created by 보운 on 2016-05-07.
 */
public class Activity_Board extends Fragment {
    private View root;
    private AQuery aq;
    private ArrayList<BoardItem> board;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_board, container, false);
        aq = new AQuery(root);

//        ListView listView = (ListView) root.findViewById(R.id.board_list);
//        listView.setAdapter(new ListViewAdapter(getActivity(), board));
        aq.id(R.id.board_list).adapter(new ListViewAdapter(getActivity(), board));

        return root;
    }
}
