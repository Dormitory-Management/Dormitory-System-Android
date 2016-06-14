package com.system.dormitory.dormitory_system_android.content;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.ibm.icu.text.SimpleDateFormat;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activity_main.Manager.Activity_Manager_outSleep;
import com.system.dormitory.dormitory_system_android.activity_main.Student.Activity_Student_Main;
import com.system.dormitory.dormitory_system_android.data.Item;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class Question_Answer_Activity extends Activity {
    private AQuery aq;
    private EditText et_content;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_question_answer);
        aq = new AQuery(this);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");
        Log.d("TestTest ", "" + item.getNumber());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        aq.id(R.id.tv_manager_answer_sno_value).text(String.valueOf(item.getSno()));
        aq.id(R.id.tv_manager_answer_title).text(item.getTitle());
        aq.id(R.id.tv_manager_answer_content).text(item.getContent());
        aq.id(R.id.tv_manager_answer_time_value).text(item.getTime());

        aq.id(R.id.btn_question_answer_submit).clicked(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("TestTest ", "comecome");
            et_content = (EditText)findViewById(R.id.et_manager_answer_answer_content);

            String answer = et_content.getText().toString();

            // 현재 시간을 msec으로 구한다.
            long now = System.currentTimeMillis();
            // 현재 시간을 저장 한다.
            Date date = new Date(now);
            // 시간 포맷으로 만든다.
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            String strNow = sdfNow.format(date);
            RequestParams params = new RequestParams();
            //put params
            params.put("number", item.getNumber());
            Log.d("TestTest ", "" + item.getNumber());
            params.put("time", strNow);
            params.put("answer", answer);

            Log.d("TestTest ", Helper_userData.getInstance().getSno() + strNow + answer);
            //server connect

            Helper_server.post("data/questionAnswer.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    insertAlert();
                }
            });
        }
    };

    public void insertAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Question_Answer_Activity.this);
        alert.setTitle("성공");
        alert.setMessage("등록이 완료되었습니다.");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }
}