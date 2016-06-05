package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.RoomCheckListAdapter;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by secret on 6/3/16.
 */
public class RoomCheck extends Activity {
    private ListView roomList;
    private DataManager data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_check_activity);
        ButterKnife.bind(this);

        data = DataManager.getInstance();

        roomList = (ListView) findViewById(R.id.room_check_list);

        for (int i = 0; i < 20; i++)
            data.getDormitoryRooms().add(new DormitoryRoom(i + 100));

        roomList.setAdapter(new RoomCheckListAdapter(getApplicationContext(), data.getDormitoryRooms()));
    }

    @OnItemClick(R.id.room_check_list)
    void onItemClick(int position) {
        Intent room = new Intent(getApplicationContext(), Room.class);
        room.putExtra("room", data.getDormitoryRooms().get(position));
        startActivity(room);
    }
}
