package com.example.mysekolah;

public class Qualification {
    private String ICNo;
    private String Name;
    private String PreSchool;
    private String PreYear;
    private String PrimarySchool;
    private String PrimaryYear;
    private String SecondarySchool;
    private String SecondaryYear;
    private String qualification;
    private String qualificationYear;

    public Qualification (String ICNo,String Name,String PreSchool,String PreYear,String PrimarySchool,String PrimaryYear,String SecondarySchool,String SecondaryYear,String qualification,String qualificationYear){
        this.ICNo=ICNo;
        this.Name=Name;
        this.PreSchool=PreSchool;
        this.PreYear=PreYear;
        this.PrimarySchool=PrimarySchool;
        this.PrimaryYear=PrimaryYear;
        this.SecondarySchool=SecondarySchool;
        this.SecondaryYear=SecondaryYear;
        this.qualification=qualification;
        this.qualificationYear=qualificationYear;
    }

    public String getICNo() {
        return ICNo;
    }

    public void setICNo(String ICNo) {
        this.ICNo = ICNo;
    }

    public String getName() {
        return Name;
    }

    public String getPreSchool() {
        return PreSchool;
    }

    public void setPreSchool(String PreSchool) {
        this.PreSchool = PreSchool;
    }

    public String getPreYear() {
        return PreYear;
    }

    public void setPreYear(String PreYear) {
        this.PreYear = PreYear;
    }

    public String getPrimarySchool() {
        return PrimarySchool;
    }

    public void setPrimarySchool(String PrimarySchool) {
        this.PrimarySchool = PrimarySchool;
    }

    public String getPrimaryYear() {
        return PrimaryYear;
    }

    public void setPrimaryYear(String PrimaryYear) {
        this.PrimaryYear = PrimaryYear;
    }

    public String getSecondarySchool() {
        return SecondarySchool;
    }

    public void setSecondarySchool(String SecondarySchool) {
        this.SecondarySchool = SecondarySchool;
    }

    public String getSecondaryYear() {
        return SecondaryYear;
    }

    public void setSecondaryYear(String SecondaryYear) {
        this.SecondaryYear =SecondaryYear;
    }

    public String getqualification() {
        return qualification;
    }

    public void setqualification(String qualification) {
        this.qualification = qualification;
    }

    public String getqualificationYear() {
        return qualificationYear;
    }

    public void setqualificationYear(String qualificationYear) {
        this.qualificationYear =qualificationYear;
    }

}
