package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_rentalStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-09.
 */
public class Activity_Manager_Rental_specific extends Activity {

    private TextView tv_sno;
    private TextView tv_name;
    private TextView tv_time;
    private Button btn_confirm;
    private Button btn_cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_rental_specific);

        Intent intent = getIntent();
        final int position = intent.getExtras().getInt("position");

        tv_sno = (TextView) findViewById(R.id.tv_manager_rental_specific_sno_value);
        tv_sno.setText("" + Helper_rentalStudent.student.get(position).sno);

        tv_name = (TextView) findViewById(R.id.tv_manager_rental_specific_name_value);
        tv_name.setText("" + Helper_rentalStudent.student.get(position).name);

        tv_time = (TextView) findViewById(R.id.tv_manager_rental_specific_time_value);
        tv_time.setText("" + Helper_rentalStudent.student.get(position).date + " " + Helper_rentalStudent.student.get(position).time);


        btn_confirm =  (Button) findViewById(R.id.btn_manager_rental_specific_confirm);
        btn_cancel =  (Button) findViewById(R.id.btn_manager_rental_specific_cancel);

        btn_confirm.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.add("number", "" + Helper_rentalStudent.student.get(position).number);
                params.add("sno", "" + Helper_rentalStudent.student.get(position).sno);

                Helper_server.post("data/rental_ok.php", params, new TextHttpResponseHandler() {
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
                params.add("number", "" + Helper_rentalStudent.student.get(position).number);
                params.add("sno", "" + Helper_rentalStudent.student.get(position).sno);
                Helper_server.post("data/delete_rental.php", params, new TextHttpResponseHandler() {
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
                finish();
                return false;
            default:
                return false;
        }
    }




}
