package com.example.mysekolah.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DisciplineResultBean implements Parcelable {
    private int discScore;
    private int year;
    private String grade;
    private String hardWorking;
    private String responsible;
    private String leadership;
    private String dedicate;
    private String politeness;
    private String honesty;
    private int remarks;

    public DisciplineResultBean(int discScore, int year, String grade, String hardWorking, String responsible, String leadership, String dedicate, String politeness, String honesty,int remarks) {
        this.discScore = discScore;
        this.year = year;
        this.grade = grade;
        this.hardWorking = hardWorking;
        this.responsible = responsible;
        this.leadership = leadership;
        this.dedicate = dedicate;
        this.politeness = politeness;
        this.honesty = honesty;
        this.remarks = remarks;
    }

    protected DisciplineResultBean(Parcel in) {
        discScore = in.readInt();
        year = in.readInt();
        grade = in.readString();
        hardWorking = in.readString();
        responsible = in.readString();
        leadership = in.readString();
        dedicate = in.readString();
        politeness = in.readString();
        honesty = in.readString();
    }

    public static final Creator<DisciplineResultBean> CREATOR = new Creator<DisciplineResultBean>() {
        @Override
        public DisciplineResultBean createFromParcel(Parcel in) {
            return new DisciplineResultBean(in);
        }

        @Override
        public DisciplineResultBean[] newArray(int size) {
            return new DisciplineResultBean[size];
        }
    };

    public int getRemarks() {
        return remarks;
    }

    public void setRemarks(int remarks) {
        this.remarks = remarks;
    }

    public String getGrade() {
        return grade;
    }

    public int getDiscScore() {
        return discScore;
    }

    public int getYear() {
        return year;
    }

    public String getHardWorking() {
        return hardWorking;
    }

    public String getLeadership() {
        return leadership;
    }

    public String getDedicate() {
        return dedicate;
    }

    public String getPoliteness() {
        return politeness;
    }

    public String getHonesty() {
        return honesty;
    }

    public String getResponsible() {
        return responsible;
    }

    @Override
    public String toString() {
        return "DisciplineResultBean{" +
                "discScore=" + discScore +
                ", year=" + year +
                ", grade='" + grade + '\'' +
                ", hardWorking='" + hardWorking + '\'' +
                ", responsible='" + responsible + '\'' +
                ", leadership='" + leadership + '\'' +
                ", dedicate='" + dedicate + '\'' +
                ", politeness='" + politeness + '\'' +
                ", honesty='" + honesty + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(discScore);
        dest.writeInt(year);
        dest.writeString(grade);
        dest.writeString(hardWorking);
        dest.writeString(responsible);
        dest.writeString(leadership);
        dest.writeString(dedicate);
        dest.writeString(politeness);
        dest.writeString(honesty);
    }
}
