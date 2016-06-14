package com.system.dormitory.dormitory_system_android.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016-06-14.
 */
public class TodayOutSleepData {

    public static ArrayList<TodayOutSleepData> student = new ArrayList<TodayOutSleepData>();

    public int sno;
    public int isSuccess;

    public TodayOutSleepData(int sno, int isSuccess){
        this.sno = sno;
        this.isSuccess = isSuccess;
    }

}
