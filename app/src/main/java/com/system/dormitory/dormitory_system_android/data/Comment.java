package com.system.dormitory.dormitory_system_android.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by secret on 6/14/16.
 */
public class Comment {
    private String comment;
    private int sno;
    private String time;

    public Comment(int sno, String comment, String time) {
        this.comment = comment;
        this.time = time;
        this.sno = sno;
        // 현재 시간을 저장 한다.

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSno(){
        return sno;
    }
    public void setSno(int sno){this.sno = sno;}
}
