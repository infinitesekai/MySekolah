package com.example.mysekolah.SchoolEnrollment;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PreSchoolForm2 extends AppCompatActivity {

    String[] statePR= {"Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

    Button next, back;
    EditText addressPR, postcodePR,telPR, jobPR, salaryPR;
    boolean isAllFieldsChecked = false;


    String schoolLevel;

    String selectedState="";
    String selectedDistrict="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form2);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner state_spin = (Spinner) findViewById(R.id.spinnerStatePr);
        //state_spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter statePR_aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,statePR);
        statePR_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spin.setAdapter(statePR_aa);


        state_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedState=state_spin.getSelectedItem().toString();
                loadSpinnerData(selectedState);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        next= findViewById(R.id.btnNext);
        back=findViewById(R.id.btnBack);

        addressPR=findViewById(R.id.etAddressPr);
        postcodePR= findViewById(R.id.etPosPr);
        telPR= findViewById(R.id.etTelPr);
        jobPR= findViewById(R.id.etJobPr);
        salaryPR= findViewById(R.id.etSalaryPr);

        schoolLevel=getIntent().getStringExtra("SchoolLevel");
        String selectedState= getIntent().getStringExtra("SelectedState");
        String selectedDistrict= getIntent().getStringExtra("SelectedDistrict");
        Log.d("Selected State", selectedState);
        Log.d("Selected District", selectedDistrict);



        //next button operation
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked= CheckAllField();

                if(isAllFieldsChecked) {
                    Intent i = new Intent(PreSchoolForm2.this, PreSchoolForm3.class);
                    i.putExtra("SchoolLevel", schoolLevel);
                    startActivity(i);
                }
            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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

    private void loadSpinnerData(String selectedState) {

        Spinner district_spin = (Spinner) findViewById(R.id.spinnerDistrictPr);
        //district_spin.setOnItemSelectedListener(this);

        // database handler
        DatabaseAccess db= DatabaseAccess.getInstance(this);

        // Spinner Drop down elements
        List<String> districts= db.getAllDistrict(selectedState);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, districts);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        district_spin.setAdapter(dataAdapter);

        district_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedDistrict= district_spin.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });



    }

    private boolean CheckAllField() {
        if (addressPR.length()==0){
            addressPR.setError("This field is required");
            return false;
        }
        if(postcodePR.length()==0){
            postcodePR.setError("This field is required");
            return false;
        }
        if(telPR.length()==0){
            telPR.setError("This field is required");
            return false;
        }
        if(jobPR.length()==0){
            jobPR.setError("This field is required");
            return false;
        }
        if(salaryPR.length()==0){
            salaryPR.setError("This field is required");
            return false;
        }

        return true;
    }


}