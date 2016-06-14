package com.system.dormitory.dormitory_system_android.data;

import java.io.Serializable;
import java.util.Date;

public class QuestionItem extends Item implements Serializable {
    private String answer;
    private String answerTime;

    public QuestionItem() {
        super();
    }

    public QuestionItem(String title, String content) {
        super(title, content);
    }

    public QuestionItem(int number, String title, String content, int sno, String time, String answer, String answerTime) {
        super(number, title, content, sno, time);
        if (answer.equals("0")) {
            this.answer = "답변을 기다리는 중입니다.";
            this.answerTime = "답변을 기다리는 중입니다.";
        } else {
            this.answer = answer;
            this.answerTime = answerTime;
        }
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerTime() {
        return answerTime;
    }
}
