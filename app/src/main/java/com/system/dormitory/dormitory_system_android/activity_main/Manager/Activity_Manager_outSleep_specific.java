package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activity_main.Student.Activity_Student_Main;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-06.
 */
public class Activity_Manager_outSleep_specific extends Activity {

    private TextView tv_sno;
    private TextView tv_date;
    private TextView tv_content;
    private Button btn_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsleep_specific);

        Intent intent = getIntent();
        int position = intent.getExtras().getInt("position");
        int sno = intent.getExtras().getInt("sno");
        String date = intent.getExtras().getString("date");
        String content = intent.getExtras().getString("content");


        System.out.println("aaaaa"+position);

        tv_sno = (TextView) findViewById(R.id.tv_outSleep_specific_SNO_value);
        tv_sno.setText("" + Helper_outSleepStudent.student.get(position).sno);

        tv_date = (TextView) findViewById(R.id.tv_outSleep_specific_date_value);
        tv_date.setText("" +  Helper_outSleepStudent.student.get(position).date);

        tv_content = (TextView) findViewById(R.id.tv_outSleep_specific_content);
        tv_content.setText("" +  Helper_outSleepStudent.student.get(position).content);

        btn_confirm =  (Button) findViewById(R.id.btn_outSleep_specific_confirm);


        btn_confirm.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//                System.out.println("aaaaa" + " " + date);
////                RequestParams params = new RequestParams();
////                String content = et_content.getText().toString();
////                int s_sno = sno;
////                //put params
////                params.put("date", date);
////                params.put("content", content);
////                params.put("sno", s_sno);
//
//                //server connect
//                Helper_server.post("data/insertOutSleep.php", params, new JsonHttpResponseHandler() {
//                    @Override
//
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        String ok = "";
//                        try {
//                            ok = response.get("ok").toString();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                        Log.d("Failed: ", "" + statusCode);
//                        Log.d("Error : ", "" + throwable);
//                    }
//                });
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
