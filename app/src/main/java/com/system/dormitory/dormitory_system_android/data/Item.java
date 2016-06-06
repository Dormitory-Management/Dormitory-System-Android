package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 보운 on 2016-05-10.
 */
public class Item implements Serializable {
    private String title;
    private String content;
    private String name;
    private Date date;
    private int roomNumber;

    public Item() {
        date = new Date();
    }

    public Item(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public Item(String title, String content, String name) {
        this(title, content);
        this.name = name;
    }

    public Item(String title, String content, int roomNumber, String name) {
        this(title, content);
        this.roomNumber = roomNumber;
        this.name = name;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
