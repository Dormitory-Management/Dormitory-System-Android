package com.system.dormitory.dormitory_system_android.data;

import java.util.ArrayList;

public class DataManager {
    private static DataManager instance = new DataManager();
    private ArrayList<BoardItem> boardItems;
    private ArrayList<NoticeItem> noticeItems;
    private ArrayList<DormitoryRoom> dormitoryRooms;
    private ArrayList<QuestionItem> questionItems;
    private ArrayList<Comment> comments;

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager() {
        instance = null;
        boardItems = new ArrayList<BoardItem>();
        noticeItems = new ArrayList<NoticeItem>();
        dormitoryRooms = new ArrayList<DormitoryRoom>();
        questionItems = new ArrayList<QuestionItem>();
        comments = new ArrayList<Comment>();
    }

    public void DataClear() {
        boardItems.clear();
        noticeItems.clear();
        dormitoryRooms.clear();
        questionItems.clear();
        comments.clear();
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

    public ArrayList<QuestionItem> getQuestionItems() {
        return questionItems;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(int sno, String comment, String time) {
        comments.add(new Comment(sno, comment, time));
    }
}
