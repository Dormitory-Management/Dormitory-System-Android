package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-06.
 */
public class Activity_Manager_outSleep_specific extends Activity {

    private TextView tv_sno;
    private TextView tv_date;
    private TextView tv_content;
    private Button btn_confirm;
    private Button btn_cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_outsleep_specific);

        Intent intent = getIntent();
        final int position = intent.getExtras().getInt("position");

        tv_sno = (TextView) findViewById(R.id.tv_outSleep_specific_SNO_value);
        tv_sno.setText("" + Helper_outSleepStudent.student.get(position).sno);

        tv_date = (TextView) findViewById(R.id.tv_outSleep_specific_date_value);
        tv_date.setText("" +  Helper_outSleepStudent.student.get(position).date);

        tv_content = (TextView) findViewById(R.id.tv_outSleep_specific_content);
        tv_content.setText("" +  Helper_outSleepStudent.student.get(position).content);

        btn_confirm =  (Button) findViewById(R.id.btn_outSleep_specific_confirm);
        btn_cancel =  (Button) findViewById(R.id.btn_outSleep_specific_cancel);


        btn_confirm.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.add("number", "" + Helper_outSleepStudent.student.get(position).number);
                Helper_server.post("data/outSleep_ok.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        finish();
                    }
                });
            }});

        btn_cancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.add("number", "" + Helper_outSleepStudent.student.get(position).number);
                Helper_server.post("data/delete_outSleep.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        finish();
                    }
                });
            }});

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Activity_Manager_outSleep_specific.this, Activity_Manager_outSleep.class);
                startActivity(intent);
                finish();
                return false;
            default:
                return false;
        }
    }

}
