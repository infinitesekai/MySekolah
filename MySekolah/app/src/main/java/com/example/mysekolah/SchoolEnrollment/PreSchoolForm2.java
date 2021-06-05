package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import android.widget.TextView;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PreSchoolForm2 extends AppCompatActivity {

    String[] statePR= {"Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

    Button next, back;
    EditText addressPR, postcodePR,telPR, jobPR, salaryPR;
    private TextView ic_PR, name_PR, gender_PR, race_PR, religion_PR, nationality_PR, pageTitle;
    boolean isAllFieldsChecked = false;


    String schoolLevel;
    private User currentUser;
    private int lastfragment;

    String selectedState="";
    String selectedDistrict="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form2);

        //get all the data from last intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        String icChild= getIntent().getStringExtra("icChild");
        String nameChild= getIntent().getStringExtra("nameChild");
        String genderChild= getIntent().getStringExtra("genderChild");
        String raceChild= getIntent().getStringExtra("raceChild");
        String religionChild= getIntent().getStringExtra("religionChild");
        String nationalityChild= getIntent().getStringExtra("nationalityChild");
        String addressChild= getIntent().getStringExtra("addressChild");
        String postcodeChild= getIntent().getStringExtra("postcodeChild");
        String stateChild= getIntent().getStringExtra("stateChild");
        String districtChild= getIntent().getStringExtra("districtChild");
        String telChild= getIntent().getStringExtra("telChild");
        schoolLevel=getIntent().getStringExtra("SchoolLevel");
        lastfragment = 0;

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

        //button
        next= findViewById(R.id.btnNext);
        back=findViewById(R.id.btnBack);

        //textview
        ic_PR= findViewById(R.id.tvICPr);
        name_PR= findViewById(R.id.tvNamePr);
        gender_PR= findViewById(R.id.tvGenderPr);
        race_PR= findViewById(R.id.tvRacePr);
        religion_PR= findViewById(R.id.tvReligionPr);
        nationality_PR= findViewById(R.id.tvNationalityPr);

        ic_PR.setText(currentUser.getICNo());
        name_PR.setText(currentUser.getName());
        gender_PR.setText(currentUser.getGender());
        race_PR.setText(currentUser.getRace());
        religion_PR.setText(currentUser.getReligion());
        nationality_PR.setText(currentUser.getNation());

        //edittext
        addressPR=findViewById(R.id.etAddressPr);
        postcodePR= findViewById(R.id.etPosPr);
        telPR= findViewById(R.id.etTelPr);
        jobPR= findViewById(R.id.etJobPr);
        salaryPR= findViewById(R.id.etSalaryPr);



        //next button operation
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the form value
                String icPr= ic_PR.getText().toString();
                String namePr= name_PR.getText().toString();
                String genderPr= gender_PR.getText().toString();
                String racePr= race_PR.getText().toString();
                String religionPr= religion_PR.getText().toString();
                String nationalityPr= nationality_PR.getText().toString();
                String addressPr= addressPR.getText().toString();
                String postcodePr= postcodePR.getText().toString();
                String telPr= telPR.getText().toString();
                String jobPr= jobPR.getText().toString();
                String salaryPr= salaryPR.getText().toString();

                isAllFieldsChecked= CheckAllField();

                if(isAllFieldsChecked) {
                    Intent i = new Intent(PreSchoolForm2.this, PreSchoolForm3.class);
                    i.putExtra("SchoolLevel", schoolLevel);
                    i.putExtra("icChild", icChild);
                    i.putExtra("nameChild", nameChild);
                    i.putExtra("genderChild", genderChild);
                    i.putExtra("raceChild", raceChild);
                    i.putExtra("religionChild", religionChild);
                    i.putExtra("nationalityChild", nationalityChild);
                    i.putExtra("addressChild", addressChild);
                    i.putExtra("postcodeChild", postcodeChild);
                    i.putExtra("telChild", telChild);
                    i.putExtra("stateChild", stateChild);
                    i.putExtra("districtChild", districtChild);
                    i.putExtra("icPr", icPr);
                    i.putExtra("namePr", namePr);
                    i.putExtra("genderPr", genderPr);
                    i.putExtra("racePr", racePr);
                    i.putExtra("religionPr", religionPr);
                    i.putExtra("nationalityPr", nationalityPr);
                    i.putExtra("addressPr", addressPr);
                    i.putExtra("postcodePr", postcodePr);
                    i.putExtra("telPr", telPr);
                    i.putExtra("jobPr", jobPr);
                    i.putExtra("salaryPr", salaryPr);
                    i.putExtra("statePr", selectedState);
                    i.putExtra("districtPr", selectedDistrict);
                    i.putExtra("user", currentUser);
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    lastfragment = R.id.nav_notif;
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    lastfragment = R.id.nav_search;
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