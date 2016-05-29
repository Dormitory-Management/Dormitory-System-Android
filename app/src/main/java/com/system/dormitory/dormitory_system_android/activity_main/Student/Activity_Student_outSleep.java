package com.system.dormitory.dormitory_system_android.activity_main.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;
import com.system.dormitory.dormitory_system_android.login.Activity_Join;
import com.system.dormitory.dormitory_system_android.login.Activity_Login;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by Administrator on 2016-05-22.
 */
public class Activity_Student_outSleep extends Activity {

    private TextView tv_sno;
    private EditText et_date;
    private EditText et_content;
    private Button btn_resister;

    public int sno;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_outsleep);

        sno = Helper_userData.getInstance().getSno();
        tv_sno = (TextView) findViewById(R.id.tvV_outSleep_SNO_value);
        System.out.println("snosno" + sno);
        tv_sno.setText("" + sno);

        et_date = (EditText) findViewById(R.id.et_outSleep_date);
        et_content = (EditText) findViewById(R.id.et_outSleep_content);

        btn_resister =  (Button) findViewById(R.id.btn_outSleep_resister);

        btn_resister.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                String date = et_date.getText().toString();
                String content = et_content.getText().toString();
                int s_sno = sno;
                //put params
                params.put("date", date);
                params.put("content", content);
                params.put("sno", s_sno);

                //server connect
                Helper_server.post("insertOutSleep.php", params, new JsonHttpResponseHandler() {
                    @Override

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("abde", "success");
                        insertAlert();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + throwable);
                    }
                });
            }});
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Activity_Student_outSleep.this, Activity_Student_Main.class);
                startActivity(intent);
                finish();
                return false;
            default:
                return false;
        }
    }

    public void insertAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Student_outSleep.this);
        alert.setTitle("성공");
        alert.setMessage("가입이 완료되셨습니다.");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity_Student_outSleep.this, Activity_Student_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        alert.show();
    }


}
