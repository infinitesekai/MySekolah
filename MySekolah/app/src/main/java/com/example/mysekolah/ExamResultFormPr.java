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
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ExamResultFormPr extends AppCompatActivity {

    String[] year = {"2015", "2016", "2017", "2018", "2019", "2020", "2021"};
    String[] test = {"Test 1", "Test 2", "Test 3", "Test 4"};
    TextView name, icView;
    Button showbtn;
    String selectedSchool = "";
    String selectedYear = "";
    String selectedTerm = "";

    String icChild;
    String childName;

    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_form_pr);

        currentUser = (User) getIntent().getSerializableExtra("user");
        icChild= getIntent().getStringExtra("icChild");
        childName= getIntent().getStringExtra("childName");
        lastfragment = 0;
        //ic
        //String ic = getIntent().getExtras().getString("ICNo");

        Log.d("ChildIC:", icChild);

        name = findViewById(R.id.tvName);
        icView = findViewById(R.id.tvIC);

        name.setText(icChild);
        icView.setText(childName);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Spinner school_spin = (Spinner) findViewById(R.id.school_spinner);
        Spinner year_spin = (Spinner) findViewById(R.id.year_spinner);
        Spinner test_spin = (Spinner) findViewById(R.id.test_spinner);


        loadSchoolSpinnerData(icChild);

        ArrayAdapter yearaa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        ArrayAdapter testaa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test);
        testaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        test_spin.setAdapter(testaa);


        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedYear = year_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        test_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedTerm = test_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });


        showbtn = findViewById(R.id.btnshow);


        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExamResultFormPr.this, ExamResultTable_Pr.class);
                i.putExtra("user", currentUser);
                i.putExtra("icChild", icChild);
                i.putExtra("childName", childName);
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//?????????values????????????????????????
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);//?????????values????????????????????????
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


    private void loadSchoolSpinnerData(String ic) {

        Spinner school_spin = (Spinner) findViewById(R.id.school_spinner);


        // database handler
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();

        // Spinner Drop down elements
        List<String> school = db.getUserSchool(ic);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, school);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        school_spin.setAdapter(dataAdapter);

        school_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedSchool = school_spin.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });


    }
}


