package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExamResultTable extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    private ExpandableHeightGridView gridview;
    private TextView tvname, tvyear, tvtest;
    public static ArrayList<String> resultList;
    private ArrayAdapter<String> adapter;
    private Button export;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_table);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //btn export
        export= findViewById(R.id.btnexportResult);

        //GridView
        gridview= findViewById(R.id.result_grid);
        //ArrayList
        resultList= new ArrayList<String>();
        adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,resultList );

        /*String subject, mark, grade;
        subject="";
        mark="";
        grade="";*/

        tvyear= findViewById(R.id.tvYear);
        tvname=findViewById(R.id.tvName);
        tvtest=findViewById(R.id.tvTest);


        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        String ic= getIntent().getExtras().getString("ICNo");
        String school= getIntent().getExtras().getString("School");
        String year= getIntent().getExtras().getString("Year");
        String test= getIntent().getExtras().getString("Test");

        tvname.setText(currentUser.getName());
        tvyear.setText(year);
        tvtest.setText(test);



        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        /*Log.d("selectedYear", year);
        Log.d("selectedSchool", school);
        Log.d("selectedYear", test);*/
        /*try {
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
        }*/
        databaseAccess.DisplayExamResult(ic,school,year,test);
        gridview.setAdapter(adapter);
        gridview.setExpanded(true);
        gridview.setFocusable(false);
        //databaseAccess.close();

       export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ExamResultTable.this, ExportExamResult.class);
                i.putExtra("user", currentUser);
                i.putExtra("Year", year);
                i.putExtra("Test", test);
                i.putExtra("School", school);
                i.putExtra("ICNo", ic);
                startActivity(i);
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };





}