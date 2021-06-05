package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

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

    // Get all spinner school list values
    public List<String> getAllSchoolList(String district, String schoolLevel){
        List<String> schools= new ArrayList<String>();

        Cursor cursor= database.rawQuery("SELECT ScName FROM School WHERE District = ? and EduLevel=?", new String[] {district,schoolLevel});
        if(cursor.moveToFirst()){
            do{
                schools.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return schools;
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

    public ArrayList checkuseric(String ic, String nameStr) {
        //SQLiteDatabase db = this.getWritableDatabase();
        String querySql = "select ICNo,Name,Gender,Races,Religion,Nationality from Resident where ICNo = '" + ic +"' and name = '" + nameStr +"';";
        Cursor cursor = database.rawQuery(querySql, null);
        ArrayList<User> userArrayList = new ArrayList<User>();
        while(cursor.moveToNext()) {
            //光标移动成功
            String icno = cursor.getString(0);
            String name = cursor.getString(1);
            String gender = cursor.getString(2);
            String races = cursor.getString(3);
            String religion = cursor.getString(4);
            String nation = cursor.getString(5);
            User user = new User();
            user.setICNo(icno);
            user.setName(name);
            user.setRace(races);
            user.setReligion(religion);
            user.setNation(nation);
            user.setGender(gender);
            userArrayList.add(user);
        }
        cursor.close();
        return userArrayList;
    }

    //新增用户
    public boolean inserData(String ic, String pass, int role, String name, String phone, String address) {
        //SQLiteDatabase db = this.getWritableDatabase();
//        String insertSql = "insert into User(icNo, Name, Role, Password) value('" + ic +"','"+ name + "','" + role +"','"+ pass +"')";
        String insertSql = "insert into User(ICNo,Name, Role, Password, PhoneNo, Address) select '" + ic+ "','" + name + "','" + role +"','" + pass + "','" + phone + "','" + address +"' where not exists (select * from User where icNo = '"+ ic +"');";

        try {
            database.execSQL(insertSql);
        } catch (RuntimeException e) {
            Log.d("1122334455",e.getLocalizedMessage());
            return false;
        }  finally {
            database.close();//add
        }
        return true;
    }

    //校验用户名密码
    public User checkusericpassword(String ic, String pwd) {
       // SQLiteDatabase db = this.getWritableDatabase();
        String queryUser = "Select u.icNo, u.role,u.name, u.gender,u.job,u.salary,u.address,u.phoneno, r.races, r.religion, r.gender, r.nationality, u.bdate from User u, Resident r  where u.icNo = '"+ ic +"' and u.Password = '" + pwd +"'";
        Cursor cursor =  database.rawQuery(queryUser, null);
        User user = new User();
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            String icno = cursor.getString(0);
            int role = cursor.getInt(1);
            String name = cursor.getString(2);
            String gender = cursor.getString(3);
            String job = cursor.getString(4);
            String salary = cursor.getString(5);
            String address = cursor.getString(6);
            String phone = cursor.getString(7);
            String races = cursor.getString(8);
            String religion = cursor.getString(9);
            String gender2 = cursor.getString(10);
            String nation = cursor.getString(11);
            String bdate = cursor.getString(12);
            user.setICNo(icno);
            user.setRole(role);
            user.setName(name);
            user.setGender(gender2);
            user.setJob(job);
            user.setSalary(salary);
            user.setAddress(address);
            user.setPhoneNo(phone);
            user.setRace(races);
            user.setReligion(religion);
            user.setNation(nation);
            user.setBdate(bdate);
        }
        cursor.close();
        return user;
    }

    //更新user信息
    public boolean updateUser(User user) {
        //SQLiteDatabase db = this.getWritableDatabase();
        String update_user = "update User set Bdate ='"+ user.getBdate() +"', salary='"+ user.getSalary() +
                "',job='"+ user.getJob() +"',address='"+ user.getAddress() +
                "',phoneno='" + user.getPhoneNo() +"' where icNo = '" + user.getICNo() +"'";
        try {
            database.execSQL(update_user);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

//    public Cursor getApplicationList(String parentIC){
//        String query="SELECT nameChild FROM Application WHERE icPr="+ parentIC;
//
//        Cursor cursor= database.rawQuery(query,null);
//
//        return cursor;
//
//    }

    public List<String> getApplicationList(String parentIC){

        List<String> list_item= new ArrayList<String>();

        String query="SELECT nameChild FROM Application WHERE icPr="+ parentIC;
               Cursor cursor= database.rawQuery(query,null);


        if (cursor.moveToFirst()){
            do{
                String child=cursor.getString(0);


                Apply_List.list_item.add(child);

            }while (cursor.moveToNext());
        }
        return list_item;
    }

    public String getStatus(String childName){
        String status;
        Cursor cursor= database.rawQuery("SELECT status FROM Application WHERE nameChild=?", new String[]{childName});
        status = cursor.getString(0);
        cursor.close();
        return status;
    }

    public StatusInfo getStatusInfo(String childname) {

        StatusInfo info= null;
        Cursor cursor = database.rawQuery("SELECT icChild,nameChild,schoolName FROM Application WHERE nameChild = ? ", new String[] {childname});
        //if(cursor!=null){
        if(cursor.moveToFirst()) {
            info = new  StatusInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        return info;
    }

}




