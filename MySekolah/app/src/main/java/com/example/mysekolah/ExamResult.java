package com.example.mysekolah;

public class ExamResult {


    private String subject;
    private String mark;
    private String grade;



    public ExamResult(){

    }

    public ExamResult(String year, String term, String name, String subject, String mark, String grade,String school) {

        this.subject = subject;
        this.mark = mark;
        this.grade = grade;

    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


}
