package com.example.mysekolah;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Output;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DBNAME = "MySekolahDB.db";
    public static final String DBLOCATION = "/data/data/com.example.mysekolah/databases/";
    public static final String TABLE_NAME1 = "Resident";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 2);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            try{
                copyDataBase();
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.enableWriteAheadLogging();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public Residents getResidentbyIC(String IC) {

        Residents residents = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE ICNo = ? ", new String[]{String.valueOf(IC)});
        cursor.moveToFirst();
        residents = new Residents(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                , cursor.getString(5), cursor.getString(6), cursor.getString(7));
        cursor.close();
        closeDatabase();
        return residents;
    }


    //from youtube
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath= DBLOCATION+DBNAME;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e) {
        }
            if(checkDB!=null)
                checkDB.close();
            return checkDB != null ? true : false;
        }

        private void copyDataBase()throws IOException{
            InputStream input=mContext.getAssets().open(DBNAME);
            String outFileName=DBLOCATION+DBNAME;
            OutputStream output=new FileOutputStream(outFileName);
            byte[] buffer= new byte[1024];
            int length;

            while((length=input.read(buffer))>0){
                output.write(buffer,0,length);
            }
            output.flush();
            output.close();
            input.close();
        }

        private void openDB() throws SQLException{

                String myPath= DBLOCATION+DBNAME;
            mDatabase=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);

        }

        public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
        return mDatabase.query("MySekolahDB.db",null,null,null,null,null,null,null);
        }







}


