package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;

/**
 * Created by 보운 on 2016-05-10.
 */
public class NoticeItem extends Item implements Serializable {
    public NoticeItem() {
        super();
    }

    public NoticeItem(String title, String content) {
        super(title, content);
    }

    public NoticeItem(int number, String title, String content, String name, String time) {
        super(number, title, content, name, time);
    }
}
