package com.system.dormitory.dormitory_system_android.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ibm.icu.text.SimpleDateFormat;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.CommentListAdapter;
import com.system.dormitory.dormitory_system_android.adapter.RoomListAdapter;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.Item;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BoardActivity extends Activity {
    private AQuery aq;
    private CommentListAdapter commentListAdapter;
    @Bind(R.id.comment_list)
    ListView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        aq = new AQuery(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("Item");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초");

        aq.id(R.id.content_layout_title).text(item.getTitle());
        aq.id(R.id.content_layout_content).text(item.getContent());
//        aq.id(R.id.content_layout_date).text(dateFormat.format(item.getTime()));
        aq.id(R.id.content_layout_date).text((item.getTime()));

        commentListAdapter = new CommentListAdapter(getApplicationContext(), DataManager.getInstance().getComments());
        comments.setAdapter(commentListAdapter);
    }

    @OnClick(R.id.comment_regist_btn)
    void onClick() {
        String comment = aq.id(R.id.comment_edit).getText().toString();

        if (comment.equals(""))
            Toast.makeText(getApplicationContext(), "댓글 내용을 입력하세요!", Toast.LENGTH_SHORT).show();
        else {
            DataManager.getInstance().addComment(comment);
            commentListAdapter.notifyDataSetChanged();
        }
    }
}
