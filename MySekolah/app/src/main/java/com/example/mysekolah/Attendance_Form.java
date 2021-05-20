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
import android.widget.Toast;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Attendance_Form extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener
       {

    String[] school = { "Kindergarten Aman", "SK Kota Warisan", "SMK Sri Sepang"};
    String[] year={"2015","2016","2017","2018","2019","2020","2021"};
    Button showbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_form);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Spinner school_spin = (Spinner) findViewById(R.id.school_spinner);
        Spinner year_spin = (Spinner) findViewById(R.id.year_spinner);
        school_spin.setOnItemSelectedListener(this);
        year_spin.setOnItemSelectedListener(this);

        ArrayAdapter schoolaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,school);
        schoolaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_spin.setAdapter(schoolaa);

        ArrayAdapter yearaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        showbtn=findViewById(R.id.btnshow);

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Attendance_Form.this, Attendance_Table.class);
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

           @Override
           public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

               //Toast.makeText(getApplicationContext(),school[position] , Toast.LENGTH_LONG).show();
           }
           @Override
           public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
           }
}