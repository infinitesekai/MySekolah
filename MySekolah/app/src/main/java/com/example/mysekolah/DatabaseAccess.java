package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cottacush.android.currencyedittext.CurrencyEditText;
import com.example.mysekolah.PersonalityCareerTest.Question;
import com.example.mysekolah.PersonalityCareerTest.QuestionContract;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
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

    public Cursor DisplayExamResult() {
        return database.rawQuery("SELECT * FROM Result ", null);
    }

    //GET THE PERSONALITY TEST
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + QuestionContract.QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_OPTION2)));
//                question.setAnswerChoice(c.getInt(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_ANSWER_CHOICE)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
