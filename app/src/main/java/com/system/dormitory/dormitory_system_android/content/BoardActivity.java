package com.system.dormitory.dormitory_system_android.content;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ibm.icu.text.SimpleDateFormat;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.CommentListAdapter;
import com.system.dormitory.dormitory_system_android.adapter.RoomListAdapter;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.Item;
import com.system.dormitory.dormitory_system_android.data.TodayOutSleepData;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;


public class BoardActivity extends Activity {
    private AQuery aq;
    private CommentListAdapter commentListAdapter;
    private Item item;
    @Bind(R.id.comment_list)
    ListView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        aq = new AQuery(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");

        aq.id(R.id.content_layout_title).text(item.getTitle());
        aq.id(R.id.content_layout_content).text(item.getContent());
        aq.id(R.id.content_layout_date).text(item.getTime());

        commentListAdapter = new CommentListAdapter(getApplicationContext(), DataManager.getInstance().getComments());
        comments.setAdapter(commentListAdapter);

        RequestParams params = new RequestParams();
        //put params
        params.put("number", item.getNumber());

        Helper_server.post("data/getBoardComment.php", params, new JsonHttpResponseHandler() {
            @Override

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    int sum = Integer.parseInt(response.get("board_sum").toString());
                    for (int i = 0; i < sum; i++) {
                        DataManager.getInstance().addComment(Integer.parseInt(response.get("board_sno" + i).toString()),
                                response.get("board_comment" + i).toString(), response.get("board_time" + i).toString().substring(5, 16));
                    }
                    commentListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }

    @OnClick(R.id.comment_regist_btn)
    void onClick() {
        String comment = aq.id(R.id.comment_edit).getText().toString();

        if (comment.equals(""))
            Toast.makeText(getApplicationContext(), "댓글 내용을 입력하세요!", Toast.LENGTH_SHORT).show();
        else {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            java.text.SimpleDateFormat sdfNow = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strNow = sdfNow.format(date);

            DataManager.getInstance().addComment(Helper_userData.getInstance().getSno(), comment,strNow);

            RequestParams params = new RequestParams();
            //put params
            params.put("sno", Helper_userData.getInstance().getSno());
            params.put("comment", comment);
            params.put("number", item.getNumber());
            params.put("time",strNow);
                //server connect
                Helper_server.post("data/InsertBoardComment.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(BoardActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("댓글 등록이 완료되었습니다.");
                        alert.show();
                    }
                });
            }
            commentListAdapter.notifyDataSetChanged();
        }

}