package com.example.mysekolah;

import java.io.Serializable;

public class User implements Serializable {//实现这个接口才可以在intent中传对象

    private String userID;
    private String ICNo;
    private String name;
    private int role;
    private String password;
    private String gender;
    private String race;
    private String religion;
    private String nation;
    private String bDate;
    private String job;
    private String salary;
    private String address;
    private String phoneNo;

    public User() {
    }

    public User(String userID, String ICNo, String name, int role, String password, String gender, String bDate, String job, String salary, String address, String phoneNo, String religion) {
        this.userID = userID;
        this.ICNo = ICNo;
        this.name = name;
        this.role = role;
        this.password = password;
        this.gender = gender;
        this.bDate = bDate;
        this.job = job;
        this.salary = salary;
        this.address = address;
        this.phoneNo = phoneNo;
        this.religion= religion;
    }
    public String userID() {
        return userID ;
    }

    public void  userID(String  userID) {
        this. userID =  userID;
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


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBdate() {
        return bDate;
    }

    public void setBdate(String bDate) {
        this.bDate = bDate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
