package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ibm.icu.text.SimpleDateFormat;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.QuestionListAdapter;
import com.system.dormitory.dormitory_system_android.content.QuestionActivity;
import com.system.dormitory.dormitory_system_android.content.Question_Answer_Activity;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.QuestionItem;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-13.
 */
public class Activity_Manager_question_list extends Activity {
    private DataManager dataManager;
    private ListView listView;
    private SimpleDateFormat simpleDateFormat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question_list);
    }

    public void init() {
        dataManager = DataManager.getInstance();
        dataManager.DataClear();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        listView = (ListView) findViewById(R.id.student_question_list);
        final QuestionListAdapter adapter = new QuestionListAdapter(getApplicationContext(), DataManager.getInstance().getQuestionItems());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(questionClick);

        RequestParams params = new RequestParams();
        params.add("sno", ""+Helper_userData.getInstance().getSno());
        Helper_server.post("data/getQuestion.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int question_sum = Integer.parseInt(response.get("question_sum").toString());
                    System.out.println("aaaa" + question_sum);
                    for (int i = 0; i < question_sum; i++) {
                        System.out.println("aaaa" + response.get("question_title" + i).toString());
                        dataManager.getQuestionItems().add(new QuestionItem(Integer.parseInt(response.get("question_number" + i).toString()),response.get("question_title" + i).toString(),
                                response.get("question_content" + i).toString(), Integer.parseInt(response.get("question_sno" + i).toString()),
                                response.get("question_time" + i).toString(), response.get("question_answer" + i).toString(),
                                response.get("question_answerTime" + i).toString()));
                        adapter.notifyDataSetChanged();
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

    ListView.OnItemClickListener questionClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent content = new Intent(getApplicationContext(), Question_Answer_Activity.class);
            content.putExtra("Item", DataManager.getInstance().getQuestionItems().get(i));
            content.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(content);
        }
    };


    public void onResume() {
        super.onResume();  // Always call the superclass method first
        init();
    }
}
