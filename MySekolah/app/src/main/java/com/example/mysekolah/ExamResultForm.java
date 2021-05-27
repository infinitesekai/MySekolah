package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mysekolah.PersonalityCareerTest.NotificationPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExamResultForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] school = { "Kindergarten Aman", "SK Kota Warisan", "SMK Sri Sepang"};
    String[] year={"2015","2016","2017","2018","2019","2020","2021"};
    String[] test= {"Test 1", "Test 2", "Test 3", "Test 4"};
    Button showbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_form);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Spinner school_spin = (Spinner) findViewById(R.id.school_spinner);
        Spinner year_spin = (Spinner) findViewById(R.id.year_spinner);
        Spinner test_spin = (Spinner) findViewById(R.id.Term_spinner);

        school_spin.setOnItemSelectedListener(this);
        year_spin.setOnItemSelectedListener(this);
        test_spin.setOnItemSelectedListener(this);

        ArrayAdapter schoolaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,school);
        schoolaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_spin.setAdapter(schoolaa);

        ArrayAdapter yearaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        ArrayAdapter testaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,test);
        testaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        test_spin.setAdapter(testaa);

        showbtn=findViewById(R.id.btnshow);

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ExamResultForm.this, ExamResultTable.class);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}