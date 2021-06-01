package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cottacush.android.currencyedittext.CurrencyEditText;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess (Context context){
        this.openHelper= new DatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance= new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.database= openHelper.getWritableDatabase();
    }

    public void close(){
        if(database != null){
            this.database.close();
        }
    }

    public Residents getResidentbyIC(String IC) {

        Residents residents= null;
        Cursor cursor = database.rawQuery("SELECT * FROM Resident WHERE ICNo = ? ", new String[] {IC});
        //if(cursor!=null){
            if(cursor.moveToFirst()) {
                residents = new Residents(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                        , cursor.getString(5), cursor.getString(6), cursor.getString(7));
            }
        cursor.close();
        return residents;
    }

    // Get all spinner district values
    public List<String> getAllDistrict(String state){
        List<String> districts= new ArrayList<String>();

        Cursor cursor= database.rawQuery("SELECT * FROM Places WHERE State = ?", new String[] {state});
        if(cursor.moveToFirst()){
            do{
                districts.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return districts;
    }

    public List<ExamResult> DisplayExamResult(String ic,String school, String year, String test){

        List<ExamResult> resultList= new ArrayList<ExamResult>();

        Cursor cursor= database.rawQuery("SELECT SubjectName,Mark,Grade FROM Result join School on Result.ScCode=School.ScCode WHERE Result.ICNo = ? and School.ScName=? and Result.Year=? and Result.Term=?", new String[]{ic,school,year,test});

        if (cursor.moveToFirst()){
            do{
                ExamResult result= new ExamResult();

                result.setSubject(cursor.getString(0));
                result.setMark(cursor.getString(1));
                result.setGrade(cursor.getString(2));


                String subject= cursor.getString(0);
                String mark= cursor.getString(1);
                String grade= cursor.getString(2);

                ExamResultTable.resultList.add(subject);
                ExamResultTable.resultList.add(mark);
                ExamResultTable.resultList.add(grade);


            }while (cursor.moveToNext());
        }
        return resultList;
    }

    public List<ExamResult> ExportExamResult(String ic,String school, String year, String test){

        List<ExamResult> resultList= new ArrayList<ExamResult>();

        Cursor cursor= database.rawQuery("SELECT SubjectName,Mark,Grade FROM Result join School on Result.ScCode=School.ScCode WHERE Result.ICNo = ? and School.ScName=? and Result.Year=? and Result.Term=?", new String[]{ic,school,year,test});

        if (cursor.moveToFirst()){
            do{
                ExamResult result= new ExamResult();

                result.setSubject(cursor.getString(0));
                result.setMark(cursor.getString(1));
                result.setGrade(cursor.getString(2));


                String subject= cursor.getString(0);
                String mark= cursor.getString(1);
                String grade= cursor.getString(2);

                ExportExamResult.resultList.add(subject);
                ExportExamResult.resultList.add(mark);
                ExportExamResult.resultList.add(grade);

            }while (cursor.moveToNext());
        }
        return resultList;
    }


    // SELECT Qualification.ICNo,Name,PreSchool,PreYear,PrimarySchool,PrimaryYear,SecondarySchool,SecondaryYear,qualification,qualificationYear FROM (Qualification join Resident on Qualification.ICNo=Resident.ICNo)WHERE Qualification.ICNo = "041005-10-6789";
    public  Qualification DisplayQualification(String IC){
        Qualification qualifications= null;
        Cursor cursor = database.rawQuery("SELECT Qualification.ICNo,Name,PreSchool,PreYear,PrimarySchool,PrimaryYear,SecondarySchool,SecondaryYear,qualification,qualificationYear FROM Qualification join Resident on Qualification.ICNo=Resident.ICNo WHERE Qualification.ICNo = ?", new String[] {IC});
        if(cursor.moveToFirst()) {
            qualifications = new Qualification(cursor.getString(0),cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                    , cursor.getString(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9));
        }
        cursor.close();
        return qualifications;
    }



    //SELECT AbsenceDate FROM Attendance JOIN School ON Attendance.ScCode=School.ScCode WHERE ICNo="160807-10-9088" AND School.ScName="KINDERGARDEN SALAK TINGGI" AND Year="2021" AND Month="May";
    public List<String> DisplayAbsentDate(String ic,String school, String year, String month){

        List<String> AbsentDateList= new ArrayList<String>();

        Cursor cursor= database.rawQuery("SELECT AbsenceDate FROM Attendance JOIN School ON Attendance.ScCode=School.ScCode WHERE ICNo=? AND School.ScName=? AND Year=? AND Month=?", new String[]{ic,school,year,month});

        if (cursor.moveToFirst()){
            do{
                String date=cursor.getString(0);


                Attendance_Table.AbsentDateList.add(date);

            }while (cursor.moveToNext());
        }
        return AbsentDateList;
    }



}




