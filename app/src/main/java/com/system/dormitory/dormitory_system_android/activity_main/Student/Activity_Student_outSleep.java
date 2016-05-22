package com.system.dormitory.dormitory_system_android.activity_main.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

/**
 * Created by Administrator on 2016-05-22.
 */
public class Activity_Student_outSleep extends Activity {

    private TextView tv_sno;
    public int sno;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_outsleep);

        sno = Helper_userData.getInstance().getSno();
        tv_sno = (TextView) findViewById(R.id.tvV_outSleep_SNO_value);
        System.out.println("snosno" + sno);
        tv_sno.setText(""+sno);
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
}
