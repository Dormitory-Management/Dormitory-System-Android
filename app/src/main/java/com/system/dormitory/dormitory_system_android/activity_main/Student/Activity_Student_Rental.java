package com.system.dormitory.dormitory_system_android.activity_main.Student;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;
import com.system.dormitory.dormitory_system_android.login.Activity_Login;

/**
 * Created by Administrator on 2016-06-07.
 */
public class Activity_Student_Rental extends Activity {

    private TextView tv_sno;
    private Spinner spinner_rent;
    private Button btn_time;
    private Button btn_resister;
    private String time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_rental);

        int sno = Helper_userData.getInstance().getSno();
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_time = (Button)findViewById(R.id.btn_student_rental_time);
        btn_time.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                System.out.println("aaaaaa+들어옴1");
                showDialog(1);
            }
        });


        btn_resister = (Button)findViewById(R.id.btn_student_rental_resister);
        btn_resister.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("aaaaaa+들어옴2");
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        TimePickerDialog tpd =
                new TimePickerDialog(Activity_Student_Rental.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                time = hourOfDay+ "시" + minute +"분";
                                Toast.makeText(getApplicationContext(),
                                        hourOfDay + "시 " + minute + "분 을 선택했습니다",
                                        Toast.LENGTH_SHORT).show();
                                btn_time.setText(time);
                            }
                        }, // 값설정시 호출될 리스너 등록
                        4,19, false); // 기본값 시분 등록
        // true : 24 시간(0~23) 표시
        // false : 오전/오후 항목이 생김
        return tpd;
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
}

