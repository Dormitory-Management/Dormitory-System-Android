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
import android.widget.Toast;

import com.androidquery.AQuery;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 보운 on 2016-05-15.
 */
public class WritingActivity extends Activity {
    private AQuery aq;
    private EditText et_content, et_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_layout);
        aq = new AQuery(this);

        aq.id(R.id.btn_write_submit).clicked(listener);
        aq.id(R.id.btn_write_cancel).clicked(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_write_submit:
                    et_title = (EditText) findViewById(R.id.write_title_edit);
                    et_content = (EditText)findViewById(R.id.write_content_edit);

                    String title = et_title.getText().toString();
                    String content = et_content.getText().toString();

                    // 현재 시간을 msec으로 구한다.
                    long now = System.currentTimeMillis();
                    // 현재 시간을 저장 한다.
                    Date date = new Date(now);
                    // 시간 포맷으로 만든다.
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    String strNow = sdfNow.format(date);
                    RequestParams params = new RequestParams();
                    //put params
                    params.put("sno", Helper_userData.getInstance().getSno());
                    params.put("time", strNow);
                    params.put("title", title);
                    params.put("content", content);

                    Log.d("TestTest ", Helper_userData.getInstance().getSno() + strNow + title + content);
                    //server connect
                    Helper_server.post("data/insertBoard.php", params, new JsonHttpResponseHandler() {
                        @Override

                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            String ok = "";
                            try {
                                ok = response.get("ok").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (ok.equals("yes"))
                                insertAlert();
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.d("Failed: ", "" + statusCode);
                            Log.d("Error : ", "" + throwable);
                        }
                    });
                    break;
                case R.id.btn_write_cancel:
                    Toast.makeText(getApplicationContext(), "글쓰기를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    public void insertAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(WritingActivity.this);
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
