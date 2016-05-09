package com.system.dormitory.dormitory_system_android.data;

/**
 * Created by 보운 on 2016-05-10.
 */
public class Item {
    private String title;
    private String content;

    public Item() {

    }

    public Item(String title, String content) {
        this.title = title;
        this.content = content;
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
}
