package com.system.dormitory.dormitory_system_android.data;

import java.util.Date;

/**
 * Created by secret on 6/14/16.
 */
public class Comment {
    private String comment;
    private Date date;

    public Comment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
