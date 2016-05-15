package com.system.dormitory.dormitory_system_android.data;

import java.util.ArrayList;

/**
 * Created by 보운 on 2016-05-10.
 */
public class DataManager {
    private static DataManager instance = new DataManager();
    private ArrayList<BoardItem> boardItems;
    private ArrayList<NoticeItem> noticeItems;

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager() {
        instance = null;
        boardItems = new ArrayList<BoardItem>();
        noticeItems = new ArrayList<NoticeItem>();
    }

    public void DataClear() {
        boardItems.clear();
        noticeItems.clear();
    }

    public ArrayList<BoardItem> getBoardItems() {
        return boardItems;
    }

    public ArrayList<NoticeItem> getNoticeItems() {
        return noticeItems;
    }
}
