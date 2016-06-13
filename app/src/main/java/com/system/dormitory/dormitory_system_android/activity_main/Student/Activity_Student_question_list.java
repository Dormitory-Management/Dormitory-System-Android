package com.system.dormitory.dormitory_system_android.activity_main.Student;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.data.BoardItem;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.NoticeItem;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-13.
 */
public class Activity_Student_question_list extends Activity {
    private DataManager dataManager;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question_list);


    }

    public void init() {
        dataManager = DataManager.getInstance();
        dataManager.DataClear();

        RequestParams params = new RequestParams();
        params.add("id", "123");
        Helper_server.post("data/getBoardAndNotice.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int notice_sum = Integer.parseInt(response.get("notice_sum").toString());
                    System.out.println("aaaa" + notice_sum);
                    for (int i = 0; i < notice_sum; i++) {
                        dataManager.getNoticeItems().add(new NoticeItem(response.get("notice_title" + i).toString(),
                                response.get("notice_content" + i).toString(), "사감",
                                response.get("notice_time" + i).toString()));
                        //viewPager.getAdapter().notifyDataSetChanged();
                    }
                    int board_sum = Integer.parseInt(response.get("board_sum").toString());
                    System.out.println("aaaa" + board_sum);
                    for (int i = 0; i < board_sum; i++) {
                        dataManager.getBoardItems().add(new BoardItem(response.get("board_title" + i).toString(),
                                response.get("board_content" + i).toString(), Integer.parseInt(response.get("board_sno" + i).toString()),
                                response.get("board_time" + i).toString()));
                        //viewPager.getAdapter().notifyDataSetChanged();
                    }


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
//        aq.id(R.id.notice_layout).clicked(changePage);
//        aq.id(R.id.point_layout).clicked(changePage);
//        aq.id(R.id.board_layout).clicked(changePage);
        
    }

}
