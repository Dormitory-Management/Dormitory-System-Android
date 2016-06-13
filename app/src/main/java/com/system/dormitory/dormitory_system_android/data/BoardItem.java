package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;

public class BoardItem extends Item implements Serializable {
    public BoardItem() {
        super();
    }

    public BoardItem(String title, String content) {
        super(title, content);
    }

    public BoardItem(String title, String content, int sno, String time) {
        super(title, content, sno, time);
    }
}
