package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.system.dormitory.dormitory_system_android.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BoWoon on 2016-06-06.
 */
public class Rental extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rental_check_submit) void onClick() {
        Toast.makeText(getApplicationContext(), "rental_submit", Toast.LENGTH_SHORT).show();
    }
}
