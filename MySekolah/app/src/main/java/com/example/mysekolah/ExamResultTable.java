package com.example.mysekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExamResultTable extends AppCompatActivity {

    private GridView gridview;
    private TextView name, year, test;
    private ArrayList<String> resultList;
    private ArrayAdapter<String> adapter;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_table);

        //GridView
        gridview= findViewById(R.id.result_grid);
        //ArrayList
        resultList= new ArrayList<String>();
        adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,resultList );
        String subject, mark, grade;
        subject="";
        mark="";
        grade="";
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        try {
            //for holding retrieve data from query and store in the form of rows
            Cursor c=databaseAccess.DisplayExamResult();
            //Move the cursor to the first row.
            if(c.moveToFirst()){
                do {
                   subject=c.getString(c.getColumnIndex("SubjectName"));
                   mark=c.getString(c.getColumnIndex("Mark"));
                   grade=c.getString(c.getColumnIndex("Grade"));
                   //add in to array list
                    resultList.add(subject);
                    resultList.add(mark);
                    resultList.add(grade);
                    gridview.setAdapter(adapter);
                }while (c.moveToNext());
            }else
            {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "No data found 2", Toast.LENGTH_LONG).show();
        }
        databaseAccess.close();
    }
}