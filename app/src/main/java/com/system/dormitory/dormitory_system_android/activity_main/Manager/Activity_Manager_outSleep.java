package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-03.
 */
public class Activity_Manager_outSleep extends Activity {

    ListView outSleep_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_outsleep);


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return false;
            default:
                return false;
        }
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first


        outSleep_list = (ListView) findViewById(R.id.outsleep_confirm_listview);

        final ArrayList<String> items =  new ArrayList<String>();
        items.clear();
        Helper_outSleepStudent.student.clear();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, items);
        outSleep_list.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.add("id","123");
        Helper_server.post("data/getOutSleep.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int sum = Integer.parseInt(response.get("sum").toString());
                    for (int i = 0; i < sum; i++) {

                        if (Integer.parseInt(response.get("isSuccess" + i).toString()) == 0) {
                            System.out.println("aaaa" + response.get("sno"+i).toString());
                            items.add("학번 : " + response.get("sno" + i).toString());
                            Helper_outSleepStudent.student.add(new Helper_outSleepStudent(Integer.parseInt(response.get("number" + i).toString())
                                    , Integer.parseInt(response.get("sno" + i).toString()), response.get("date" + i).toString()
                                    , response.get("content" + i).toString(), Integer.parseInt(response.get("isSuccess" + i).toString())));
                            adapter.notifyDataSetChanged();
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

        outSleep_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Activity_Manager_outSleep.this, Activity_Manager_outSleep_specific.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

}
