package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
