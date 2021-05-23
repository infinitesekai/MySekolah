package com.example.mysekolah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.airbnb.lottie.animation.content.Content;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME ="Login.db";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }
    //To create table on database
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE USERS(ic_number TEXT primary key,password TEXT)");

    }
    //drop the table if already exist
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS USERS");

    }
    //to insert data
    public Boolean inserData(String ic_number,String password){
        SQLiteDatabase MyDB =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("ic_number",ic_number);
        contentValues.put("password",password);
        long result =MyDB.insert("users",null,contentValues);

        if(result==1) return false;
        else
            return true;
    }
    //to check the ic_number
    public Boolean checkuseric(String ic_number) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * from users where ic_number= ?", new String[]{ic_number});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }
        //to check the password
        public Boolean checkusericpassword(String ic_number, String password){
        SQLiteDatabase MyDB =this.getWritableDatabase();
        Cursor cursor =MyDB.rawQuery("SELECT* FROM users WHERE ic_number = ? and passward =?",new String[]{ic_number,password});
        if(cursor.getCount()>0)
            return true;

        else return false;

        }



    }


