package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;

/**
 * Created by 보운 on 2016-05-10.
 */
public class BoardItem extends Item implements Serializable {
    public BoardItem() {
        super();
    }

    public BoardItem(String title, String content) {
        super(title, content);
    }

    public BoardItem(String title, String content, int roomNumber, String name) {
        super(title, content, roomNumber, name);
    }
}
