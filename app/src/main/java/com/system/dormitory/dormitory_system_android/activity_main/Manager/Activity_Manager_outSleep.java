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
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activity_main.Student.Activity_Student_Main;
import com.system.dormitory.dormitory_system_android.helper.Helper_outSleepStudent;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-03.
 */
public class Activity_Manager_outSleep extends Activity {

    ListView outSleep_list;
    static final String[] LIST_MENU = {"LIST1", "LIST2","LIST3"};



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsleep_confirm);

        outSleep_list = (ListView) findViewById(R.id.outsleep_confirm_listview);

        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, items);

        outSleep_list.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.add("id","123");
        Helper_server.post("data/getOutSleep.php", params, new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int sum = Integer.parseInt(response.get("sum").toString());
                    for (int i = 0; i < sum; i++) {
                        items.add(response.get("sno"+i).toString());
                        Helper_outSleepStudent.student.add(new Helper_outSleepStudent(Integer.parseInt(response.get("sno" + i).toString()), response.get("date" + i).toString()
                                , response.get("content" + i).toString(), Integer.parseInt(response.get("isSuccess" + i).toString())));
                        System.out.println("aaaaa" + Helper_outSleepStudent.student.get(i).content);
                        adapter.notifyDataSetChanged();
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
                String strText = (String) parent.getItemAtPosition(position);
                System.out.println("aaaa"+strText+"posi: "+position);

                Intent intent = new Intent(Activity_Manager_outSleep.this, Activity_Manager_outSleep_specific.class);
                intent.putExtra("sno", Helper_outSleepStudent.student.get(position).sno);
                intent.putExtra("date", Helper_outSleepStudent.student.get(position).date);
                intent.putExtra("content", Helper_outSleepStudent.student.get(position).content);

                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Activity_Manager_outSleep.this, Activity_Manager_Main.class);
                startActivity(intent);
                finish();
                return false;
            default:
                return false;
        }
    }

}
