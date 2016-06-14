package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DormitoryRoom implements Serializable {

    private int room; //방번호
    private int count; //학생수
    private ArrayList<String> student; //학생이름.
    private ArrayList<Integer> sno;

    public DormitoryRoom() {
        this.student = new ArrayList<String>();
        this.sno = new ArrayList<Integer>();
    }

    public DormitoryRoom(int room) {
        this();
        this.room = room;
    }

    public void dataClear(){
        student.clear();

    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getStudent() {
        return student;
    }

    public ArrayList<Integer> getSno() {
        return sno;
    }

    public boolean addStudent(String name) {
        if (student.size() < 4) {
            student.add(name);
            return true;
        }
        else
            return false;
    }
    public boolean addSno(int value) {
        if (sno.size() < 4) {
            sno.add(value);
            return true;
        }
        else
            return false;
    }
}
