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
import com.system.dormitory.dormitory_system_android.activity_main.Manager.Activity_Manager_question_list;
import com.system.dormitory.dormitory_system_android.activity_main.Student.Activity_Student_question_list;
import com.system.dormitory.dormitory_system_android.content.Activity_notice_write;
import com.system.dormitory.dormitory_system_android.content.Activity_question_write;
import com.system.dormitory.dormitory_system_android.content.BoardActivity;
import com.system.dormitory.dormitory_system_android.content.NoticeActivity;
import com.system.dormitory.dormitory_system_android.content.Activity_board_write;
import com.system.dormitory.dormitory_system_android.content.Question_Answer_Activity;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

/**
 * Created by 보운 on 2016-05-07.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private final int MAX_PAGE;
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
                System.out.println("aaaaa" + Helper_userData.getInstance().getIsStudent());
                if (Helper_userData.getInstance().getIsStudent() == 1) {
                    v = inflater.inflate(R.layout.activity_manager_notice, null);
                    listView = (ListView) v.findViewById(R.id.notice_list);
                    listView.setAdapter(new NoticeListAdapter(context, DataManager.getInstance().getNoticeItems()));
                    listView.setOnItemClickListener(noticeClick);
                    FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.notice_fab);
                    fab.attachToListView(listView);
                    fab.setOnClickListener(notice_floatingButtonClicked);
                } else {
                    v = inflater.inflate(R.layout.activity_notice, null);
                    listView = (ListView) v.findViewById(R.id.notice_list);
                    listView.setAdapter(new NoticeListAdapter(context, DataManager.getInstance().getNoticeItems()));
                    listView.setOnItemClickListener(noticeClick);
                }
                break;
            case 1:
                v = inflater.inflate(R.layout.activity_board, null);
                listView = (ListView) v.findViewById(R.id.board_list);
                listView.setAdapter(new BoardListAdapter(context, DataManager.getInstance().getBoardItems()));
                listView.setOnItemClickListener(boardClick);
                FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
                fab.attachToListView(listView);
                fab.setOnClickListener(board_floatingButtonClicked);
                break;
            case 2:
                System.out.println("TestTestView " + Helper_userData.getInstance().getIsStudent());
                if (Helper_userData.getInstance().getIsStudent() == 1) {
                    v = inflater.inflate(R.layout.activity_manager_question_list, null);
                    listView = (ListView) v.findViewById(R.id.manager_question_list);
                    listView.setAdapter(new QuestionListAdapter(context, DataManager.getInstance().getQuestionItems()));
                    listView.setOnItemClickListener(questionClick);
                } else {
                    v = inflater.inflate(R.layout.activity_question, null);
                    Button btn_resister = (Button) v.findViewById(R.id.btn_question_resister);
                    btn_resister.setOnClickListener(question_resisterButtonClicked);
                    Button btn_list = (Button) v.findViewById(R.id.btn_question_list);
                    btn_list.setOnClickListener(question_listButtonClicked);
                }
                break;
        }

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
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

    ListView.OnItemClickListener questionClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent content = new Intent(context, Question_Answer_Activity.class);
            content.putExtra("Item", DataManager.getInstance().getQuestionItems().get(i));
            content.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(content);
        }
    };


    FloatingActionButton.OnClickListener board_floatingButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent writing = new Intent(context, Activity_board_write.class);
            writing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(writing);
        }
    };

    FloatingActionButton.OnClickListener notice_floatingButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent writing = new Intent(context, Activity_notice_write.class);
            writing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(writing);
        }
    };

    Button.OnClickListener question_resisterButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent writing = new Intent(context, Activity_question_write.class);
            writing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(writing);
        }
    };
    Button.OnClickListener question_listButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent listIntent = new Intent(context, Activity_Student_question_list.class);
            listIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(listIntent);
        }
    };


}
