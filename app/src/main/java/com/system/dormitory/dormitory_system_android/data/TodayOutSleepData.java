package com.system.dormitory.dormitory_system_android.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016-06-14.
 */
public class TodayOutSleepData {

    public static ArrayList<TodayOutSleepData> student = new ArrayList<TodayOutSleepData>();
    public static ArrayList<TodayOutSleepData> noCheck_student = new ArrayList<TodayOutSleepData>();

    public int sno;
    public int isSuccess;
    public boolean check = false;

    public TodayOutSleepData(int sno, int isSuccess){
        this.sno = sno;
        this.isSuccess = isSuccess;
        this.check = false;
    }

    public TodayOutSleepData(int sno, boolean check){
        this.sno = sno;
        this.isSuccess = 2;
        this.check = check;
    }

    public static boolean isCheck(int sno){

        for(int i=0; i<noCheck_student.size(); i++){
            if(sno == noCheck_student.get(i).sno) return noCheck_student.get(i).check;
        }
        return false;
    }
    public static void removeChecking(int sno){
        for(int i=0; i<noCheck_student.size(); i++){
            if(sno == noCheck_student.get(i).sno){
                noCheck_student.remove(i);
                return;
            }
        }
    }
    public static void AddChecking(int sno){
        for(int i=0; i<noCheck_student.size(); i++){
            if(sno == noCheck_student.get(i).sno){
                return;
            }
        }
        noCheck_student.add(new TodayOutSleepData(sno,true));
    }
    public static void print(){
        System.out.println("hhhh"+noCheck_student.size());
        for(int i=0; i<noCheck_student.size(); i++){
            System.out.println("hhhh"+noCheck_student.get(i).check);
        }
    }

}
