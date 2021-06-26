package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mysekolah.PersonalityCareerTest.Question;
import com.example.mysekolah.PersonalityCareerTest.TestContract;
import com.example.mysekolah.PersonalityCareerTest.TestCharResult;
import com.example.mysekolah.PersonalityCareerTest.TestResultInfo;
import com.example.mysekolah.bean.DisciplineResultBean;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess<instance> {

    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    // Get residents by ic number
    public Residents getResidentbyIC(String IC) {

        Residents residents = null;
        Cursor cursor = database.rawQuery("SELECT * FROM Resident WHERE ICNo = ? ", new String[]{IC});
        //if(cursor!=null){
        if (cursor.moveToFirst()) {
            residents = new Residents(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(5), cursor.getString(6), cursor.getString(7));
        }
        cursor.close();
        return residents;
    }

    // Get all spinner district values
    public List<String> getAllDistrict(String state) {
        List<String> districts = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT * FROM Places WHERE State = ?", new String[]{state});
        if (cursor.moveToFirst()) {
            do {
                districts.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return districts;
    }

    // Get all spinner type of school values
    public List<String> getAllSchoolType(String schoolLevel) {
        List<String> schoolType = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT Type FROM SChool WHERE EduLevel=?", new String[]{schoolLevel});
        if (cursor.moveToFirst()) {
            do {
                schoolType.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return schoolType;
    }


    // Get all spinner school list values
    public List<String> getAllSchoolList(String district, String schoolLevel) {
        List<String> schools = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT ScName FROM School WHERE District = ? and EduLevel=? ", new String[]{district, schoolLevel});
        if (cursor.moveToFirst()) {
            do {
                schools.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return schools;
    }


    // Get all spinner school list values
    public List<String> getUserSchool(String ic) {
        List<String> schools = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT PreSchool, PrimarySchool, SecondarySchool FROM Qualification WHERE  ICNo= ? ", new String[]{ic});
        if (cursor.moveToFirst()) {
            do {
//                schools.add(cursor.getString(0));
//                schools.add(cursor.getString(1));
//                schools.add(cursor.getString(2));
                for (int i = 0; i < 3; i++) {
                    if (cursor.getType(i) != 0)//if not null
                        schools.add(cursor.getString(i));
                }


            } while (cursor.moveToNext());
        }

        cursor.close();
        return schools;
    }

    public ArrayList<DisciplineResultBean> getDisciplineResults(String icNo, String year) {
        ArrayList<DisciplineResultBean> disciplineResultBeans = new ArrayList<DisciplineResultBean>();
        Cursor cursor = database.rawQuery("SELECT DiscScore,DisYear,grade,Hardworking,Responsible,Leadership,Dedicate,Politeness,Honesty,Remarks FROM Discipline WHERE  DisYear= ? AND ICNo= ?", new String[]{year, icNo});
        if (cursor.moveToFirst()) {
            do {
                DisciplineResultBean bean = new DisciplineResultBean(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(8));
                disciplineResultBeans.add(bean);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return disciplineResultBeans;
    }

    public List<ExamResult> DisplayExamResult(String ic, String school, String year, String test) {

        List<ExamResult> resultList = new ArrayList<ExamResult>();

        Cursor cursor = database.rawQuery("SELECT SubjectName,Mark,Grade FROM Result join School on Result.ScCode=School.ScCode WHERE Result.ICNo = ? and School.ScName=? and Result.Year=? and Result.Term=?", new String[]{ic, school, year, test});

        if (cursor.moveToFirst()) {
            do {
                ExamResult result = new ExamResult();

                result.setSubject(cursor.getString(0));
                result.setMark(cursor.getString(1));
                result.setGrade(cursor.getString(2));


                String subject = cursor.getString(0);
                String mark = cursor.getString(1);
                String grade = cursor.getString(2);

                ExamResultTable.resultList.add(subject);
                ExamResultTable.resultList.add(mark);
                ExamResultTable.resultList.add(grade);


            } while (cursor.moveToNext());
        }
        return resultList;
    }

    public List<ExamResult> ExportExamResult(String ic, String school, String year, String test) {

        List<ExamResult> resultList = new ArrayList<ExamResult>();

        Cursor cursor = database.rawQuery("SELECT SubjectName,Mark,Grade FROM Result join School on Result.ScCode=School.ScCode WHERE Result.ICNo = ? and School.ScName=? and Result.Year=? and Result.Term=?", new String[]{ic, school, year, test});

        if (cursor.moveToFirst()) {
            do {
                ExamResult result = new ExamResult();

                result.setSubject(cursor.getString(0));
                result.setMark(cursor.getString(1));
                result.setGrade(cursor.getString(2));


                String subject = cursor.getString(0);
                String mark = cursor.getString(1);
                String grade = cursor.getString(2);

                ExportExamResult.resultList.add(subject);
                ExportExamResult.resultList.add(mark);
                ExportExamResult.resultList.add(grade);

            } while (cursor.moveToNext());
        }
        return resultList;
    }

    public List<ExamResult> DisplayExamResult_PR(String ic, String school, String year, String test) {

        List<ExamResult> resultList = new ArrayList<ExamResult>();

        Cursor cursor = database.rawQuery("SELECT SubjectName,Mark,Grade FROM Result join School on Result.ScCode=School.ScCode WHERE Result.ICNo = ? and School.ScName=? and Result.Year=? and Result.Term=?", new String[]{ic, school, year, test});

        if (cursor.moveToFirst()) {
            do {
                ExamResult result = new ExamResult();

                result.setSubject(cursor.getString(0));
                result.setMark(cursor.getString(1));
                result.setGrade(cursor.getString(2));


                String subject = cursor.getString(0);
                String mark = cursor.getString(1);
                String grade = cursor.getString(2);

                ExamResultTable_Pr.resultList.add(subject);
                ExamResultTable_Pr.resultList.add(mark);
                ExamResultTable_Pr.resultList.add(grade);


            } while (cursor.moveToNext());
        }
        return resultList;
    }

    public List<ExamResult> ExportExamResult_PR(String ic, String school, String year, String test) {

        List<ExamResult> resultList = new ArrayList<ExamResult>();

        Cursor cursor = database.rawQuery("SELECT SubjectName,Mark,Grade FROM Result join School on Result.ScCode=School.ScCode WHERE Result.ICNo = ? and School.ScName=? and Result.Year=? and Result.Term=?", new String[]{ic, school, year, test});

        if (cursor.moveToFirst()) {
            do {
                ExamResult result = new ExamResult();

                result.setSubject(cursor.getString(0));
                result.setMark(cursor.getString(1));
                result.setGrade(cursor.getString(2));


                String subject = cursor.getString(0);
                String mark = cursor.getString(1);
                String grade = cursor.getString(2);

                ExportExamResult_PR.resultList.add(subject);
                ExportExamResult_PR.resultList.add(mark);
                ExportExamResult_PR.resultList.add(grade);

            } while (cursor.moveToNext());
        }
        return resultList;
    }


    //display qualification information
    public Qualification DisplayQualification(String IC) {
        Qualification qualifications = null;
        Cursor cursor = database.rawQuery("SELECT Qualification.ICNo,Name,PreSchool,PreYear,PrimarySchool,PrimaryYear," +
                "SecondarySchool,SecondaryYear,qualification,qualificationYear " +
                "FROM Qualification join Resident on Qualification.ICNo=Resident.ICNo WHERE Qualification.ICNo = ?", new String[]{IC});
        if (cursor.moveToFirst()) {
            qualifications = new Qualification(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                    , cursor.getString(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9));
        }
        cursor.close();
        return qualifications;
    }

    public AppEnrol_Info DisplayApplication(String name) {
        AppEnrol_Info info = null;
        Cursor cursor = database.rawQuery("SELECT * " +
                "FROM Application WHERE nameChild = ?", new String[]{name});
        if (cursor.moveToFirst()) {
            info = new AppEnrol_Info(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                    , cursor.getString(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12)
                    , cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16)
                    , cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20)
                    , cursor.getString(21), cursor.getString(22), cursor.getString(23), cursor.getString(24)
                    , cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28)
                    , cursor.getString(29));
        }
        cursor.close();
        return info;
    }


    //get list of absent date of child for selected school, year and month
    public List<String> DisplayAbsentDate(String ic, String school, String year, String month) {

        List<String> AbsentDateList = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT AbsenceDate FROM Attendance JOIN School ON Attendance.ScCode=School.ScCode " +
                "WHERE ICNo=? AND School.ScName=? AND Year=? AND Month=?", new String[]{ic, school, year, month});

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);

                Attendance_Table.AbsentDateList.add(date);//add the date into the list in Attendance_Table.java

            } while (cursor.moveToNext());
        }
        return AbsentDateList;
    }

    //check ic number
    public ArrayList checkuseric(String ic, String nameStr) {

        String querySql = "select ICNo,Name,Gender,Races,Religion,Nationality from Resident where ICNo = '" + ic + "' and name = '" + nameStr + "';";
        Cursor cursor = database.rawQuery(querySql, null);
        //list all the personal information
        ArrayList<User> userArrayList = new ArrayList<User>();
        while (cursor.moveToNext()) {

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

    //new user-insert into database
    public boolean inserData(String ic, String pass, int role, String name, String phone, String address) {

        String insertSql = "insert into User(ICNo,Name, Role, Password, PhoneNo, Address) select '" + ic + "','" + name + "','" + role + "','" + pass + "','" + phone + "','" + address + "' where not exists (select * from User where icNo = '" + ic + "');";

        try {
            database.execSQL(insertSql);
        } catch (RuntimeException e) {
            Log.d("1122334455", e.getLocalizedMessage());
            return false;
        } finally {
            database.close();//add
        }
        return true;
    }

    //check ic and password
    public User checkusericpassword(String ic, String pwd) {

        String queryUser = "Select u.icNo, u.role,u.name, u.gender,u.job,u.salary,u.address,u.phoneno, r.races, r.religion, r.gender, r.nationality, u.bdate from User u, Resident r  where u.icNo = '" + ic + "' and u.Password = '" + pwd + "'";
        Cursor cursor = database.rawQuery(queryUser, null);
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

    //update user profile information
    public boolean updateUser(User user) {

        String update_user = "update User set Bdate ='" + user.getBdate() + "', salary='" + user.getSalary() +
                "',job='" + user.getJob() + "',address='" + user.getAddress() +
                "',phoneno='" + user.getPhoneNo() + "' where icNo = '" + user.getICNo() + "'";
        try {
            database.execSQL(update_user);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }


    public boolean insertSchoolApplication(String icChild, String nameChild, String genderChild, String raceChild, String religionChild,
                                           String nationalityChild, String addressChild, String postcodeChild, String stateChild,
                                           String districtChild, String telChild, String icPr, String namePr, String genderPr,
                                           String racePr, String religionPr, String nationalityPr, String addressPr, String postcodePr, String statePr, String districtPr,
                                           String telPr, String jobPr, String salaryPr, String typeOfSchool, String stateSchool, String districtSchool,
                                           String schoolName, String distance, String status) {

        String insertSql = "INSERT INTO Application \n" +
                "(icChild, namechild, genderChild, raceChild, religionChild, nationalityChild, addressChild, \n" +
                "postcodeChild, stateChild, districtChild, telchild, icPr, namePr, genderPr, racePr, religionPr, nationalityPr, \n" +
                "addressPr, postcodePr, statePr, districtPr, telPr, jobPr, salaryPr, typeOfSchool, stateSchool, districtSchool, \n" +
                "schoolName, distance, status) \n" +
                "VALUES \n" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            database.execSQL(insertSql, new String[]{icChild, nameChild, genderChild, raceChild, religionChild, nationalityChild,
                    addressChild, postcodeChild, stateChild, districtChild, telChild, icPr, namePr, genderPr,
                    racePr, religionPr, nationalityPr, addressPr, postcodePr, statePr, districtPr,
                    telPr, jobPr, salaryPr, typeOfSchool, stateSchool, districtSchool, schoolName, distance, status});
        } catch (RuntimeException e) {
            Log.d("Insertion failed", e.getLocalizedMessage());
            return false;
        } finally {
            database.close();//add
        }
        return true;
    }

    //get list of child name that applied for enrolment
    public List<String> getApplicationList(String parentIC) {

        List<String> list_item = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT nameChild FROM Application WHERE icPr=?", new String[]{parentIC});


        if (cursor.moveToFirst()) {
            do {
                String child = cursor.getString(0);


                Apply_List.list_item.add(child);//add the child name into the list in Apply_List.java

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list_item;
    }

    //get application status of for the selected child
    public String getStatus(String childName) {
        String status = "";
        Cursor cursor = database.rawQuery("SELECT status FROM Application WHERE nameChild=?", new String[]{childName});
        if (cursor.moveToFirst()) {
            status = new String(cursor.getString(0));

        }
        cursor.close();
        return status;
    }

    //get application status information of the child
    public StatusInfo getStatusInfo(String childname) {

        StatusInfo info = null;
        Cursor cursor = database.rawQuery("SELECT icChild,nameChild,schoolName FROM Application WHERE nameChild = ? ", new String[]{childname});
        //if(cursor!=null){
        if (cursor.moveToFirst()) {
            info = new StatusInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        return info;
    }

    //get list of applicant
    public List<String> getEnrolmentList() {

        List<String> list_item = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT nameChild FROM Application", null);

        if (cursor.moveToFirst()) {
            do {
                String applicant = cursor.getString(0);


                All_Enrolment.list_item.add(applicant);//add the applicant name into the list

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list_item;
    }

    //approve application
    public boolean approve(String applicantname) {
        String update_approve = "update Application set status ='2' where nameChild = '" + applicantname + "'";
        try {
            database.execSQL(update_approve);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    //reject application
    public boolean reject(String applicantname) {
        String update_reject = "update Application set status ='3' where nameChild = '" + applicantname + "'";
        try {
            database.execSQL(update_reject);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public ArrayList<Dependency> getdependency(String parentIC) {
        ArrayList<Dependency> arrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM Dependency WHERE ParentICNo = ? ", new String[]{parentIC});

        if (cursor.moveToFirst()) {
            do {

                arrayList.add(new Dependency(cursor.getString(1), cursor.getString(2), cursor.getString(3)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }


    //update Question_List set answer=0 where ques_ID=0;
    //TestContract store all the table information of question list.
    public boolean updateAnswer(String answer, String quesNo) {

        String update_answer =
                "update " +
                        TestContract.QuestionsTable.TABLE_NAME + " set " +
                        TestContract.QuestionsTable.COLUMN_ANSWER_OPTION +
                        " ='" + answer +
                        "' where " + TestContract.QuestionsTable.COLUMN_QUESTION_ID + "='" + quesNo + "'";
        try {
            database.execSQL(update_answer);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    //get selected answer for particular question
    //QuestionContract store all the table information of question list.
    public String getpreAnswer(String quesNo) {
        String answer = "";
        Cursor cursor =
                database.rawQuery(
                        "SELECT " +
                                TestContract.QuestionsTable.COLUMN_ANSWER_OPTION +
                                " FROM " +
                                TestContract.QuestionsTable.TABLE_NAME +
                                " WHERE " + TestContract.QuestionsTable.COLUMN_QUESTION_ID + "=?",
                        new String[]{quesNo});
        if (cursor.moveToFirst()) {
            answer = cursor.getString(0);
        }
        cursor.close();
        return answer;
    }

    //store the personality test question into list
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + TestContract.QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {//if there is next question in next row
                Question question = new Question(
                        //get the next question and their options
                        c.getString(c.getColumnIndex(TestContract.QuestionsTable.COLUMN_QUESTION_ID)),
                        c.getString(c.getColumnIndex(TestContract.QuestionsTable.COLUMN_QUESTION)),
                        c.getString(c.getColumnIndex(TestContract.QuestionsTable.COLUMN_OPTION1)),
                        c.getString(c.getColumnIndex(TestContract.QuestionsTable.COLUMN_OPTION2)),
                        c.getString(c.getColumnIndex(TestContract.QuestionsTable.COLUMN_ANSWER_CATEGORY)),
                        c.getString(c.getColumnIndex(TestContract.QuestionsTable.COLUMN_ANSWER_OPTION)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }


//    //get category
//    public String getCategory(String quesNo) {
//        Question question_category = null;
//        Cursor cursor = database.rawQuery(
//                "SELECT category FROM " +
//                        TestContract.QuestionsTable.TABLE_NAME +
//                        " where " +
//                        TestContract.QuestionsTable.COLUMN_QUESTION_ID +
//                        "=?", new String[]{quesNo}
//        );
//        if (cursor.moveToFirst()) {
//            question_category = new Question(cursor.getString(cursor.getColumnIndex(TestContract.QuestionsTable.COLUMN_ANSWER_CATEGORY)));
//        }
//        cursor.close();
//        return question_category;
//    }

    //store personality result(three characters)-insert into database
    public boolean insertPersonalityResult(String ic, String first, String second, String third) {
        String insertSql =
                "INSERT INTO " +
                        TestContract.TestResultTable.TABLE_NAME +
                        "(" + TestContract.TestResultTable.COLUMN_ICNO + ", " +
                        TestContract.TestResultTable.COLUMN_H_1 + "," +
                        TestContract.TestResultTable.COLUMN_H_2 + "," +
                        TestContract.TestResultTable.COLUMN_H_3 + ") \n" +
                        "VALUES \n" +
                        "(?,?,?,?)";

        try {
            database.execSQL(insertSql, new String[]{ic, first, second, third});
        } catch (RuntimeException e) {
            Log.d("Insertion failed", e.getLocalizedMessage());
            return false;
        } finally {
            database.close();//add
        }
        return true;
    }

    //get personality test result information-explanation of result
    public TestResultInfo getTestInfo(String alphabet) {

        TestResultInfo TestInfo = null;
        Cursor cursor = database.rawQuery(
                "SELECT " + TestContract.CareerSuggestionTable.COLUMN_ALPHABET +
                        "," + TestContract.CareerSuggestionTable.COLUMN_ALPNAME +
                        "," + TestContract.CareerSuggestionTable.COLUMN_DESCRIPTION +
                        "," + TestContract.CareerSuggestionTable.COLUMN_EXPLANATION +
                        "," + TestContract.CareerSuggestionTable.COLUMN_SUGGESTION +
                        " FROM " + TestContract.CareerSuggestionTable.TABLE_NAME +
                        " WHERE alphabet = ? ", new String[]{alphabet});
        if (cursor.moveToFirst()) {
            TestInfo = new TestResultInfo(
                    cursor.getString(cursor.getColumnIndex(TestContract.CareerSuggestionTable.COLUMN_ALPHABET)),
                    cursor.getString(cursor.getColumnIndex(TestContract.CareerSuggestionTable.COLUMN_ALPNAME)),
                    cursor.getString(cursor.getColumnIndex(TestContract.CareerSuggestionTable.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(TestContract.CareerSuggestionTable.COLUMN_EXPLANATION)),
                    cursor.getString(cursor.getColumnIndex(TestContract.CareerSuggestionTable.COLUMN_SUGGESTION)));
        }
        cursor.close();
        return TestInfo;
    }

    //get previous test result
    public TestCharResult getPastResult(String ic) {

        TestCharResult resultInfo = null;
        Cursor cursor = database.rawQuery(
                "SELECT " + TestContract.TestResultTable.COLUMN_H_1 + "," +
                        TestContract.TestResultTable.COLUMN_H_2 + "," +
                        TestContract.TestResultTable.COLUMN_H_3 + " FROM " +
                        TestContract.TestResultTable.TABLE_NAME + " WHERE " +
                        TestContract.TestResultTable.COLUMN_TESTID +
                        "=(select max(" + TestContract.TestResultTable.COLUMN_TESTID + ") " +
                        "FROM " + TestContract.TestResultTable.TABLE_NAME +
                        ") AND " + TestContract.TestResultTable.COLUMN_ICNO +
                        " = ? ", new String[]{ic});

        if (cursor.moveToFirst()) {
            resultInfo = new TestCharResult(
                    cursor.getString(cursor.getColumnIndex(TestContract.TestResultTable.COLUMN_H_1)),
                    cursor.getString(cursor.getColumnIndex(TestContract.TestResultTable.COLUMN_H_2)),
                    cursor.getString(cursor.getColumnIndex(TestContract.TestResultTable.COLUMN_H_3)));
        }
        cursor.close();
        return resultInfo;
    }

    //get list of children
    public ArrayList<User> getPChilds(String ic) {
        ArrayList<User> childs = new ArrayList<>();
        String querySql = "Select ChildICNo, ChildName from Dependency where ParentICNo = '" + ic + "'";
        Cursor cursor = database.rawQuery(querySql, null);
        while (cursor.moveToNext()) {
            String icNo = cursor.getString(0);
            String name = cursor.getString(1);
            User user = new User();
            user.setICNo(icNo);
            user.setName(name);
            childs.add(user);
        }
        cursor.close();
        return childs;
    }


    //delete child
    public Boolean deleteOneChild(String pIc, String cIc) {
        String deSql = "DELETE FROM Dependency WHERE ParentICNo = '" + pIc + "'and ChildICNo = '" + cIc + "'";
        Boolean result = true;
        try {
            database.execSQL(deSql);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    //add child
    public Boolean addOneChild(String pIc, User child) {
        String insertSql = "insert into Dependency (ParentICNo, ChildICNo, ChildName) VALUES (?,?,?)";
        Boolean result = true;
        try {
            database.execSQL(insertSql, new String[]{pIc, child.getICNo(), child.getName()});
        } catch (Exception e) {
            result = false;
        } finally {
            //database.close();
        }
        return result;
    }

    //check if a child can be added
    public Boolean checkChid(String pIc, String cIc) {
        String qSql = "select COUNT(*) from Dependency where ParentICNo = '" + pIc + "' and ChildICNo = '" + cIc + "';";
        Cursor cursor = database.rawQuery(qSql, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count == 0) {
                return true;
            }
        }
        return false;
    }
}
