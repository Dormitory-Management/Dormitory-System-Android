package com.system.dormitory.dormitory_system_android.activity_main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.system.dormitory.dormitory_system_android.R;

/**
 * Created by Administrator on 2016-05-16.
 */
public class Activity_penaltyPoint extends Activity {

    private TextView tv_penaltyPoint;
    private int penalty = 5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penaltypoint);

        tv_penaltyPoint = (TextView) findViewById(R.id.tv_penalty);
        tv_penaltyPoint.setText("이제 까지 받은 벌점은 \n"+ penalty + "점 입니다.");
    }

}
