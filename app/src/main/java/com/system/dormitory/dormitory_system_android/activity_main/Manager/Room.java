package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.RoomListAdapter;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by secret on 6/3/16.
 */
public class Room extends Activity {
    private Intent roomIntent;
    private AQuery aq;
    private RoomListAdapter adapter;
    private static ArrayList<Boolean> checkbox;
    private DormitoryRoom room;
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
        room = (DormitoryRoom) roomIntent.getSerializableExtra("room");
        people.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        aq.id(R.id.room_number).text(String.valueOf(room.getRoom()) + "호");

        adapter = new RoomListAdapter(getApplicationContext(), room.getStudent());
        people.setAdapter(adapter);

        for (int i = 0; i < 4; i++) {
            checkbox.add(false);
        }
        RequestParams params = new RequestParams();
        params.add("roomNumber", String.valueOf(room.getRoom()));
        Helper_server.post("data/getRoomInName.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int sum = Integer.parseInt(response.get("sum").toString());
                    for (int i = 0; i < sum; i++) {
                        room.addStudent(response.get("name" + i).toString());
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
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

    public static ArrayList<Boolean> getCheckbox() {
        return checkbox;
    }

    //TODO checkbox변수를 사용하면 누가 불참했는지 알 수 있음 false가 불참
    @OnClick(R.id.room_check_submit)
    void onClick() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            if (checkbox.get(i))
                str += String.valueOf(i) + " ";
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnItemClick(R.id.people)
    void onItemClick(int position) {
        checkbox.set(position, !checkbox.get(position));
        adapter.notifyDataSetChanged();
    }
}
