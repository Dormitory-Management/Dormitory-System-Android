package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ibm.icu.text.SimpleDateFormat;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activity_main.Student.Activity_Student_Main;
import com.system.dormitory.dormitory_system_android.adapter.RoomCheckListAdapter;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.DormitoryRoom;
import com.system.dormitory.dormitory_system_android.data.TodayOutSleepData;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_check_activity);
        ButterKnife.bind(this);
        getTodayOutSleep();
        btn_submit = (Button) findViewById(R.id.room_check_button);
        btn_submit.setOnClickListener(roomCheck_listener);
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
                        if (Integer.parseInt(response.get("roomNumber" + i).toString()) != 0)
                            items.add(Integer.parseInt(response.get("roomNumber" + i).toString()));
                    }
                    HashSet<Integer> hashSet = new HashSet<Integer>(items);
                    ArrayList<Integer> roomList = new ArrayList<Integer>(hashSet);
                    Collections.sort(roomList);
                    for (int i = 0; i < roomList.size(); i++) {
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
        TodayOutSleepData.print();
    }

    public void getTodayOutSleep() {

        TodayOutSleepData.student.clear();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        java.text.SimpleDateFormat sdfNow = new java.text.SimpleDateFormat("yyyy/MM/dd");
        String strNow = sdfNow.format(date);
        System.out.println("strNow:" + strNow);
        RequestParams params = new RequestParams();
        params.add("date", strNow);
        Helper_server.post("data/getTodayOutSleep.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int sum = Integer.parseInt(response.get("sum").toString());
                    for (int i = 0; i < sum; i++) {
                        if (Integer.parseInt(response.get("isSuccess" + i).toString()) == 1) {
                            TodayOutSleepData.student.add(new TodayOutSleepData(Integer.parseInt(response.get("sno" + i).toString()),
                                    Integer.parseInt(response.get("isSuccess" + i).toString())));
                        }
                    }
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

    Button.OnClickListener roomCheck_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("TestTest ", "comecome");

            RequestParams params = new RequestParams();
            //put params
            params.put("size", TodayOutSleepData.noCheck_student.size());
            if (TodayOutSleepData.noCheck_student.size() == 0) {
                noAlert();
            } else {
                for (int i = 0; i < TodayOutSleepData.noCheck_student.size(); i++) {
                    params.put("sno" + (i + 1), TodayOutSleepData.noCheck_student.get(i).sno);
                    Log.d("TestTest" + (i + 1), " " + TodayOutSleepData.noCheck_student.get(i).sno);
                }
                //server connect
                Helper_server.post("data/updateScore_RoomCheck.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        insertAlert();
                    }
                });
            }
        }
    };

    public void insertAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(RoomCheck.this);
        alert.setTitle("성공");
        alert.setMessage("벌점적용이 완료되었습니다..");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        alert.show();
    }
    public void noAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(RoomCheck.this);
        alert.setTitle("성공");
        alert.setMessage("벌점적용될 사람이 없습니다..");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                  finish();
            }
        });
        alert.show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return false;
            default:
                return false;
        }
    }
}
