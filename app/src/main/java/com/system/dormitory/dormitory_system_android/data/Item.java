package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 보운 on 2016-05-10.
 */
public class Item implements Serializable {
    private String title;
    private String content;
    private int sno;
    private Date time;
    private int roomNumber;
    private String name;
    private int number;

    public Item() {
        name = "user";
        roomNumber = 0;
    }

    public Item(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }
    //board
    public Item(int number, String title, String content, int sno, Date time) {
        this(title, content);
        this.sno = sno;
        this.time = time;
        this.number = number;
    }

    //notice
    public Item(int number, String title, String content, String name, Date time) {
        this(title, content);
        this.time = time;
        this.name = name;
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public int getSno() {
        return sno;
    }
}
