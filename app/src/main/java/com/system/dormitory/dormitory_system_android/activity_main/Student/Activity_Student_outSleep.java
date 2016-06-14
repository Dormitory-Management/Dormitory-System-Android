package com.system.dormitory.dormitory_system_android.activity_main.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-05-22.
 */
public class Activity_Student_outSleep extends Activity {

    private TextView tv_sno;
    private Button btn_date;
    private EditText et_content;
    private Button btn_resister;
    String date = "";

    public int sno;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_outsleep);

        sno = Helper_userData.getInstance().getSno();
        tv_sno = (TextView) findViewById(R.id.tv_outSleep_SNO_value);
        System.out.println("snosno" + sno);
        tv_sno.setText("" + sno);

        btn_date = (Button) findViewById(R.id.et_outSleep_date);
        et_content = (EditText) findViewById(R.id.et_outSleep_content);

        btn_resister = (Button) findViewById(R.id.btn_outSleep_resister);

        btn_date.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showDialog(1);
            }
        });

        btn_resister.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("aaaaa" + " " + date);
                RequestParams params = new RequestParams();
                String content = et_content.getText().toString();
                int s_sno = sno;
                //put params
                params.put("date", date);
                params.put("content", content);
                params.put("sno", s_sno);

                //server connect
                Helper_server.post("data/insertOutSleep.php", params, new JsonHttpResponseHandler() {
                    @Override

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        String ok = "";
                        try {
                            ok = response.get("ok").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ok.equals("yes"))
                            insertAlert();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + throwable);
                    }
                });
            }
        });
    }

    @Deprecated
    protected Dialog onCreateDialog(int a) {
        DatePickerDialog dpd = new DatePickerDialog
                (Activity_Student_outSleep.this, // 현재화면의 제어권자
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view,
                                                  int year, int monthOfYear, int dayOfMonth) {
                                Toast.makeText(getApplicationContext(),
                                        year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일 을 선택했습니다",
                                        Toast.LENGTH_SHORT).show();
                                String month="";
                                String day="";
                                if(monthOfYear+1 <10) month="0"+(monthOfYear+1);
                                else month=""+(monthOfYear+1);
                                if(dayOfMonth <10) day="0"+(dayOfMonth);
                                else day=""+(dayOfMonth);

                                date = year + "/" + month + "/" + day;
                                btn_date.setText(date);

                            }
                        }
                        , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                        //    호출할 리스너 등록
                        Calendar.getInstance().get(Calendar.YEAR), (Calendar.getInstance().get(Calendar.MONTH)), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)); // 기본값 연월일
        return dpd;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Activity_Student_outSleep.this, Activity_Student_Main.class);
                startActivity(intent);
                finish();
                return false;
            default:
                return false;
        }
    }

    public void insertAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Student_outSleep.this);
        alert.setTitle("성공");
        alert.setMessage("외박등록이 완료되었습니다.");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity_Student_outSleep.this, Activity_Student_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        alert.show();
    }


}
