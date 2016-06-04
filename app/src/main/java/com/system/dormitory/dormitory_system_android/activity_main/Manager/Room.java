package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;

/**
 * Created by secret on 6/3/16.
 */
public class Room extends Activity {
    private Intent roomIntent;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
        aq = new AQuery(this);

        roomIntent = getIntent();
        DormitoryRoom room = (DormitoryRoom) roomIntent.getSerializableExtra("room");

        aq.id(R.id.room_number).text(String.valueOf(room.getRoom()));
    }
}
