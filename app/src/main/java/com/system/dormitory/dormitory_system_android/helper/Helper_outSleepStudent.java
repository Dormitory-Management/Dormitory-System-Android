package com.system.dormitory.dormitory_system_android.helper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-06-06.
 */
public class Helper_outSleepStudent {

    public static ArrayList<Helper_outSleepStudent> student = new ArrayList<Helper_outSleepStudent>();

    public int sno;
    public String date;
    public String content;
    public int isSuccess;

    public Helper_outSleepStudent(int sno, String date, String content, int isSuccess){
        this.sno = sno;
        this.date = date;
        this.content = content;
        this.isSuccess = isSuccess;
    }

}
