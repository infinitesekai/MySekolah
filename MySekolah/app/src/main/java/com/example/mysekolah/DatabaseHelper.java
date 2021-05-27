package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper  extends SQLiteAssetHelper {

    public static final String DBNAME = "MySekolahDB.db";
    //public static final String DBLOCATION = "/data/data/com.example.mysekolah/databases/";
    public static final String TABLE_NAME1= "Resident";
    //private Context mContext;
    //private SQLiteDatabase mDatabase;
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
        //this.mContext = context;
    }



    /*@Override
    public void onCreate(SQLiteDatabase db) {
       super.onOpen(db);
       db.disableWriteAheadLogging();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }*/

   /* public Residents getResidentbyIC(String IC) {
        Residents residents= null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME1 +" WHERE ICNo = ? ", new String[] {String.valueOf(IC)});
        cursor.moveToFirst();
        residents = new Residents(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                                ,cursor.getString(5), cursor.getString(6), cursor.getString(7));
        cursor.close();
        closeDatabase();
        return residents;
    }*/
}