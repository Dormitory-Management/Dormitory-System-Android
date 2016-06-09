package com.system.dormitory.dormitory_system_android.helper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-06-09.
 */
public class Helper_rentalStudent {

    public static ArrayList<Helper_rentalStudent> student = new ArrayList<Helper_rentalStudent>();

    public int number;
    public int sno;
    public String name;
    public String date;
    public String time;
    public int isSuccess;

    public Helper_rentalStudent(int number, int sno, String name, String date, String time, int isSuccess){
        this.number = number;
        this.sno = sno;
        this.name = name;
        this.date = date;
        this.time = time;
        this.isSuccess = isSuccess;
    }
}
