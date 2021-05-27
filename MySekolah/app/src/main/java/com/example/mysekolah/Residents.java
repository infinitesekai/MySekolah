package com.example.mysekolah;

public class Residents {

    private String ICNo;
    private String name;
    private String gender;
    private String races;
    private String religion;
    private String nationality;


    public Residents(String ICNo, String name, String gender,String races, String religion, String nationality) {
        this.ICNo = ICNo;
        this.name = name;
        this.gender = gender;
        this.races = races;
        this.religion = religion;
        this.nationality = nationality;
    }

    public String getICNo() {
        return ICNo;
    }

    public void setICNo(String ICNo) {
        this.ICNo = ICNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRaces() {
        return races;
    }

    public void setRaces(String races) {
        this.races = races;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }



}
