package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Attendance_Form extends AppCompatActivity
       {

    String[] school = { "KINDERGARDEN SALAK TINGGI", "SK Kota Warisan", "SMK Sri Sepang"};
    String[] year={"2019","2020","2021"};
    String[] month={"January","February","March","April","May","June","July","August","September","October","November","December"};
   // String[] month={"1","2","3","4","5","6","7","8","9","10","11","12"};
    Button showbtn;

           String selectedSchool="";
           String selectedYear="";
           String selectedMonth="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_form);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Spinner school_spin = (Spinner) findViewById(R.id.school_spinner);
        Spinner year_spin = (Spinner) findViewById(R.id.year_spinner);
        Spinner month_spin = (Spinner) findViewById(R.id.month_spinner);


        ArrayAdapter schoolaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,school);
        schoolaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_spin.setAdapter(schoolaa);

        ArrayAdapter yearaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        ArrayAdapter monthaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,month);
        monthaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spin.setAdapter(monthaa);

        school_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSchool=school_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear=year_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        month_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth=month_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String ic= getIntent().getExtras().getString("ICNo");



        showbtn=findViewById(R.id.btnshow);

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Attendance_Form.this, Attendance_Table.class);
                i.putExtra("ICNo", ic);
                i.putExtra("Year", selectedYear);
                i.putExtra("School", selectedSchool);
                i.putExtra("Month", selectedMonth);
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