package com.example.mysekolah.PersonalityCareerTest;

import java.io.Serializable;

public class Question implements Serializable {
    private String questionID;
    private String question;
    private String option1;
    private String option2;
    private String category;
    private String answer;
    private int answerChoice;
    public Question(){

    }

    public Question(String questionID, String question, String option1,
                    String option2, String category, String answer) {
        this.questionID = questionID;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.category = category;
        this.answer = answer;
//        this.answerChoice = answerChoice;
    }
    public Question(String category) {
        this.category = category;
    }
    public String getQuestionID() {
        return questionID;
    }
    public String setQuestionID(String questionID) {
        return questionID;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getCategory() {
        return category;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(int answerChoice) {
        this.answerChoice = answerChoice;
    }
}

