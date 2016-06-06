package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.RoomListAdapter;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by secret on 6/3/16.
 */
public class Room extends Activity {
    private Intent roomIntent;
    private AQuery aq;
    private RoomListAdapter roomListAdapter;
    private static ArrayList<Boolean> checkbox;
    @Bind(R.id.people)
    ListView people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
        aq = new AQuery(this);
        ButterKnife.bind(this);

        checkbox = new ArrayList<Boolean>();

        roomIntent = getIntent();
        DormitoryRoom room = (DormitoryRoom) roomIntent.getSerializableExtra("room");
        people.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        aq.id(R.id.room_number).text(String.valueOf(room.getRoom()) + "호");

        room.addStudent("홍길동");
        room.addStudent("김철수");
        room.addStudent("김영희");
        room.addStudent("김이박");

        for (int i = 0; i < 4; i++) {
            checkbox.add(false);
        }

        roomListAdapter = new RoomListAdapter(getApplicationContext(), room.getStudent());
        people.setAdapter(roomListAdapter);
    }

    public static ArrayList<Boolean> getCheckbox() {
        return checkbox;
    }

    @OnClick(R.id.room_check_submit) void onClick() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            if (checkbox.get(i))
                str += String.valueOf(i) + " ";
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @OnItemClick(R.id.people)
    void onItemClick(int position) {
        checkbox.set(position, !checkbox.get(position));
        roomListAdapter.notifyDataSetChanged();
    }
}
