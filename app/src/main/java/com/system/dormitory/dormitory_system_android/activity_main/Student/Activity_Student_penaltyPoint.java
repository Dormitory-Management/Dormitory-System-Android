package com.system.dormitory.dormitory_system_android.activity_main.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-05-16.
 */
public class Activity_Student_penaltyPoint extends Activity {

    private TextView tv_penaltyPoint;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penaltypoint);


        RequestParams params = new RequestParams();
        params.add("sno", ""+Helper_userData.getInstance().getSno());
        Helper_server.post("data/getScore.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int score = Integer.parseInt(response.get("score").toString());
                    tv_penaltyPoint = (TextView) findViewById(R.id.tv_penalty);
                    tv_penaltyPoint.setText("이제 까지 받은 벌점은 \n" + score + "점 입니다.");

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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Activity_Student_penaltyPoint.this, Activity_Student_Main.class);
                startActivity(intent);
                finish();
                return false;
            default:
                return false;
        }
    }
}
