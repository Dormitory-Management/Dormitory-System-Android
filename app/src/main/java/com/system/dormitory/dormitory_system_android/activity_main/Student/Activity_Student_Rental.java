package com.system.dormitory.dormitory_system_android.activity_main.Student;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;
import com.system.dormitory.dormitory_system_android.login.Activity_Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-06-07.
 */
public class Activity_Student_Rental extends Activity {

    private TextView tv_sno;
    private Spinner spinner_rent;
    private Button btn_date;
    private Button btn_time;
    private Button btn_resister;
    private String time;
    private String date;
    private String name;
    private int sno;
    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_rental);

        sno = Helper_userData.getInstance().getSno();
        tv_sno = (TextView) findViewById(R.id.tv_student_rental_sno_value);
        System.out.println("snosno" + sno);
        tv_sno.setText("" + sno);

        spinner_rent = (Spinner) findViewById(R.id.spi_student_rental_object_value);
        spinner_rent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println(("position : " + position +
                        parent.getItemAtPosition(position)));
                name = ""+parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_date = (Button)findViewById(R.id.btn_student_rental_date);
        btn_date.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });

        btn_time = (Button)findViewById(R.id.btn_student_rental_time);
        btn_time.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                showDialog(DIALOG_TIME);
            }
        });

        btn_resister = (Button)findViewById(R.id.btn_student_rental_resister);
        btn_resister.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                //put params
                params.put("time", time);
                params.put("date", date);
                params.put("sno", sno);
                params.put("name", name);

                //server connect
                Helper_server.post("data/insertRent.php", params, new JsonHttpResponseHandler() {
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

    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_TIME) {
            TimePickerDialog tpd =
                    new TimePickerDialog(Activity_Student_Rental.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    time = hourOfDay + "시" + minute + "분";
                                    Toast.makeText(getApplicationContext(),
                                            hourOfDay + "시 " + minute + "분 을 선택했습니다",
                                            Toast.LENGTH_SHORT).show();
                                    btn_time.setText(time);
                                }
                            }, // 값설정시 호출될 리스너 등록
                            4, 19, false); // 기본값 시분 등록
            // true : 24 시간(0~23) 표시
            // false : 오전/오후 항목이 생김
            return tpd;
        }
        else if(id==DIALOG_DATE) {
            DatePickerDialog dpd = new DatePickerDialog
                    (Activity_Student_Rental.this, // 현재화면의 제어권자
                            new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker view,
                                                      int year, int monthOfYear, int dayOfMonth) {
                                    Toast.makeText(getApplicationContext(),
                                            year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일 을 선택했습니다",
                                            Toast.LENGTH_SHORT).show();
                                    date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                    btn_date.setText(date);

                                }
                            }
                            , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                            //    호출할 리스너 등록
                            Calendar.getInstance().get(Calendar.YEAR), (Calendar.getInstance().get(Calendar.MONTH)), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)); // 기본값 연월일
            return dpd;
        }
        else return null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Activity_Student_Rental.this, Activity_Student_Main.class);
                startActivity(intent);
                finish();
                return false;
            default:
                return false;
        }
    }
    public void insertAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Student_Rental.this);
        alert.setTitle("성공");
        alert.setMessage("물건대여이 완료되었습니다.");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity_Student_Rental.this, Activity_Student_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        alert.show();
    }
}

