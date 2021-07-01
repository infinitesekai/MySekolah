package com.example.mysekolah.PersonalityCareerTest;

import java.io.Serializable;

public class Answer_Tracking implements Serializable {
    private String quesID;
    private int answerOption;

    public Answer_Tracking() {
    }

    public Answer_Tracking(String quesID, int answerOption) {
        this.quesID = quesID;
        this.answerOption = answerOption;
    }

    public String getQuestionID() {
        return quesID;
    }

    public void setQuestionID(String quesID) {
        this.quesID = quesID;
    }
    public int getAnswerChoice() {
        return answerOption;
    }

    public void setAnswerChoice(int answerOption) {
        this.answerOption = answerOption;
    }
}
