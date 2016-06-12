package com.system.dormitory.dormitory_system_android.data;

import java.util.ArrayList;

/**
 * Created by 보운 on 2016-05-10.
 */
public class DataManager {
    private static DataManager instance = new DataManager();
    private ArrayList<BoardItem> boardItems;
    private ArrayList<NoticeItem> noticeItems;
    private ArrayList<DormitoryRoom> dormitoryRooms;
    private static String gcmClientKey;

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager() {
        instance = null;
        boardItems = new ArrayList<BoardItem>();
        noticeItems = new ArrayList<NoticeItem>();
        dormitoryRooms = new ArrayList<DormitoryRoom>();
    }

    public void DataClear() {
        boardItems.clear();
        noticeItems.clear();
        dormitoryRooms.clear();
    }

    public ArrayList<BoardItem> getBoardItems() {
        return boardItems;
    }

    public ArrayList<NoticeItem> getNoticeItems() {
        return noticeItems;
    }

    public ArrayList<DormitoryRoom> getDormitoryRooms() {
        return dormitoryRooms;
    }

    public static String getGcmClientKey() {
        return gcmClientKey;
    }

    public static void setGcmClientKey(String gcmClientKey) {
        DataManager.gcmClientKey = gcmClientKey;
    }
}
