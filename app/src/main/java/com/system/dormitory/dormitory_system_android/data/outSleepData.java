package com.system.dormitory.dormitory_system_android.data;

/**
 * Created by Administrator on 2016-06-04.
 */
public class outSleepData {

    private int number;
    private int sno;
    private String date;
    private String content;
    private int isSuccess;

    public outSleepData(int number, int sno, String date, String content, int isSuccess){
        this.number = number;
        this.sno = sno;
        this.date = date;
        this.content = content;
        this.isSuccess = isSuccess;

    }

    public int getNumber(){
        return number;
    }
    public void setNumber(int value) {
        this.number = value;
    }

    public int getSno(){
        return sno;
    }
    public void setSno(int value) {
        this.sno = value;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String value){
        this.date = value;
    }

    public String getContents() {
        return content;
    }
    public void setContents(String value) {
        this.content = value;
    }

    public int getIsSuccess(){
        return isSuccess;
    }
    public void setIsSuccess(int value) {
        this.isSuccess = value;
    }


}
