package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.RoomCheckListAdapter;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import butterknife.ButterKnife;
import butterknife.OnItemClick;
import cz.msebera.android.httpclient.Header;

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

 }

    @OnItemClick(R.id.room_check_list)
    void onItemClick(int position) {
        Intent room = new Intent(getApplicationContext(), Room.class);
        room.putExtra("room", data.getDormitoryRooms().get(position));
        startActivity(room);
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first

        data = DataManager.getInstance();
        data.DataClear();

        roomList = (ListView) findViewById(R.id.room_check_list);

        final ArrayList<Integer> items =  new ArrayList<Integer>();
        items.clear();

        final RoomCheckListAdapter adapter = new RoomCheckListAdapter(getApplicationContext(), data.getDormitoryRooms());
        roomList.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.add("id", "123");
        Helper_server.post("data/getRoomList.php", params, new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int sum = Integer.parseInt(response.get("sum").toString());
                    for (int i = 0; i < sum; i++) {
                        if(Integer.parseInt(response.get("roomNumber" + i).toString())!=0)
                        items.add(Integer.parseInt(response.get("roomNumber" + i).toString()));
                    }
                    HashSet<Integer> hashSet = new HashSet<Integer>(items);
                    ArrayList<Integer> roomList = new ArrayList<Integer>(hashSet);
                    Collections.sort(roomList);
                    for(int i=0; i< roomList.size(); i++){
                        data.getDormitoryRooms().add(new DormitoryRoom(roomList.get(i)));
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
}
