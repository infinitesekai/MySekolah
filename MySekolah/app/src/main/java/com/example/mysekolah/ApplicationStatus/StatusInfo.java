package com.example.mysekolah.ApplicationStatus;

public class StatusInfo {
    private String ICNo;
    private String name;
    private String school;

    public StatusInfo(String ICNo, String name, String school){
        this.ICNo = ICNo;
        this.name = name;
        this.school = school;
    }

    public String getICNo() {
        return ICNo;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }


}

