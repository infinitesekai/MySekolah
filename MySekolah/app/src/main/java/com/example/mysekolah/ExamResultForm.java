package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExamResultForm extends AppCompatActivity {

    String[] school = { "KINDERGARDEN SALAK TINGGI", "SK Kota Warisan", "SMK Sri Sepang"};
    String[] year={"2015","2016","2017","2018","2019","2020","2021"};
    String[] test= {"Test 1", "Test 2", "Test 3", "Test 4"};
    Button showbtn;
    String selectedSchool="";
    String selectedYear="";
    String selectedTerm="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_form);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Spinner school_spin = (Spinner) findViewById(R.id.tvgender);
        Spinner year_spin = (Spinner) findViewById(R.id.tvraces);
        Spinner test_spin = (Spinner) findViewById(R.id.tvnationality);





        ArrayAdapter schoolaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,school);
        schoolaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_spin.setAdapter(schoolaa);

        ArrayAdapter yearaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        ArrayAdapter testaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,test);
        testaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        test_spin.setAdapter(testaa);




        school_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedSchool=school_spin.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedYear=year_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        test_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedTerm=test_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });


        showbtn=findViewById(R.id.btnshow);

        //ic
        String ic= getIntent().getExtras().getString("ICNo");

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ExamResultForm.this, ExamResultTable.class);
                i.putExtra("ICNo", ic);
                i.putExtra("Year", selectedYear);
                i.putExtra("School", selectedSchool);
                i.putExtra("Test", selectedTerm);
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
                    break;
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };


}