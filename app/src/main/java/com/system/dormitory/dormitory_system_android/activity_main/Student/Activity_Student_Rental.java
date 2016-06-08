package com.system.dormitory.dormitory_system_android.activity_main.Student;

import android.app.Activity;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.login.Activity_Login;

/**
 * Created by Administrator on 2016-06-07.
 */
public class Activity_Student_Rental extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Activity_Login login = (Activity_Login) Activity_Login.login_Activity; //login_Activity_finish
        login.finish();
    }
}
