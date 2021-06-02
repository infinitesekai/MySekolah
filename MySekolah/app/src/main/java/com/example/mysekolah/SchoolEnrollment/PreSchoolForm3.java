//package com.example.mysekolah;
package com.example.mysekolah.SchoolEnrollment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PreSchoolForm3 extends AppCompatActivity implements View.OnClickListener {

    EditText distance;
    ImageButton schoolTypeInfo, schoolListInfo, distanceInfo;
    Button submit, back;
    //AlertDialog.Builder builder;

    boolean isAllFieldsChecked = false;

    String schoolLevel;

    String[] stateSchool= {"Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

    String selectedSchoolState="";
    String selectedSchoolDistrict="";
    String selectedSchool="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form3);

        submit= findViewById(R.id.btnSubmit);
        back=findViewById(R.id.btnBack);
        distance=findViewById(R.id.etDistance);
        schoolTypeInfo=findViewById(R.id.SchoolTypeInfo);
        distanceInfo=findViewById(R.id.distanceInfo);
        schoolListInfo=findViewById(R.id.SchoolListInfo);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        schoolTypeInfo.setOnClickListener(this);
        distanceInfo.setOnClickListener(this);
        schoolListInfo.setOnClickListener(this);

        schoolLevel=getIntent().getStringExtra("SchoolLevel");


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner state_spin = (Spinner) findViewById(R.id.spinnerStateSchool);
        //state_spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter statePR_aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,stateSchool);
        statePR_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spin.setAdapter(statePR_aa);


        state_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedSchoolState=state_spin.getSelectedItem().toString();
                loadDistrictSpinnerData(selectedSchoolState);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        //confirm button operation
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked= CheckAllField();

                if(isAllFieldsChecked) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PreSchoolForm3.this);
                    alertDialogBuilder.setMessage("Do you want to submit this application?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    //inset data into database here
                                    Intent i= new Intent(PreSchoolForm3.this, BackHomePage.class);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for no
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog= alertDialogBuilder.create();
                    alertDialog.show();
                }
            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void loadDistrictSpinnerData(String selectedState) {

        Spinner school_district_spin = (Spinner) findViewById(R.id.spinnerDistrictSchool);
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
        school_district_spin.setAdapter(dataAdapter);

        school_district_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedSchoolDistrict= school_district_spin.getSelectedItem().toString();
                loadDSchoolSpinnerData(selectedSchoolDistrict, schoolLevel);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });



    }

    private void loadDSchoolSpinnerData(String selectedSchoolDistrict, String schoolLevel) {


        Spinner school_list_spin = (Spinner) findViewById(R.id.spinnerSchoolList);
        //district_spin.setOnItemSelectedListener(this);

        // database handler
        DatabaseAccess db= DatabaseAccess.getInstance(this);

        // Spinner Drop down elements
        List<String> schools= db.getAllSchoolList(selectedSchoolDistrict, schoolLevel);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, schools);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        school_list_spin.setAdapter(dataAdapter);

        school_list_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedSchool= school_list_spin.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.SchoolTypeInfo:
                Toast.makeText(getApplicationContext(),"Please choose the school type.", Toast.LENGTH_LONG).show();
                break;
            case R.id.SchoolListInfo:
                Toast.makeText(getApplicationContext(),"Please choose from the school list", Toast.LENGTH_LONG).show();
                break;
            case R.id.distanceInfo:
                Toast.makeText(getApplicationContext(),"Please type the distance of school with your house.", Toast.LENGTH_LONG).show();
                break;
        }

    }

    private boolean CheckAllField() {
        if (distance.length()==0){
            distance.setError("This field is required");
            return false;
        }
        return true;
    }

    public class BackHomePage extends FragmentActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new com.example.mysekolah.HomePage()).commit();}
        }
    }
}