package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ExamResultTable_Pr extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    private ExpandableHeightGridView gridview;
    private TextView tvname, tvyear, tvtest;
    public static ArrayList<String> resultList;
    private ArrayAdapter<String> adapter;
    private Button export;
    DatabaseAccess databaseAccess;

    String icChild;
    String childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_table_pr);

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
        icChild= getIntent().getStringExtra("icChild");
        childName= getIntent().getStringExtra("childName");
       //String ic= getIntent().getExtras().getString("ICNo");
        String school= getIntent().getExtras().getString("School");
        String year= getIntent().getExtras().getString("Year");
        String test= getIntent().getExtras().getString("Test");

        tvname.setText(childName);
        tvyear.setText(year);
        tvtest.setText(test);
        Log.d("ChildIC:", icChild);



        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseAccess.DisplayExamResult_PR(icChild,school,year,test);
        gridview.setAdapter(adapter);
        gridview.setExpanded(true);
        gridview.setFocusable(false);
        //databaseAccess.close();

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ExamResultTable_Pr.this, ExportExamResult_PR.class);
                i.putExtra("user", currentUser);
                i.putExtra("Year", year);
                i.putExtra("Test", test);
                i.putExtra("School", school);
                i.putExtra("icChild", icChild);
                i.putExtra("childName", childName);
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
                    selectedFragment = new HomePage();
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
                    selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };

}