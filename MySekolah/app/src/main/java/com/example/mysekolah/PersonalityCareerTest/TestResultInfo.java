package com.example.mysekolah.PersonalityCareerTest;

public class TestResultInfo {
    private String aphabet;
    private String alpname;
    private String desc;
    private String exp;
    private String field;


    public TestResultInfo(String aphabet, String alpname, String desc, String exp, String field) {
        this.aphabet = aphabet;
        this.alpname = alpname;
        this.desc = desc;
        this.exp = exp;
        this.field = field;
    }

    public String getAlphabet() {
        return aphabet;
    }

    public String getAlpName() {
        return alpname;
    }

    public String getDesc() {
        return desc;
    }

    public String getExp() {
        return exp;
    }

    public String getField() {
        return field;
    }


}
