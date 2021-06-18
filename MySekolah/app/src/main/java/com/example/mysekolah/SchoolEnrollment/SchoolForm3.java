//package com.example.mysekolah;
package com.example.mysekolah.SchoolEnrollment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SchoolForm3 extends AppCompatActivity implements View.OnClickListener {

    EditText distance;
    ImageButton schoolTypeInfo, schoolListInfo, distanceInfo;
    Button submit, back;
    //AlertDialog.Builder builder;

    boolean isAllFieldsChecked = false;

    String schoolLevel;
    private User currentUser;
    private int lastfragment;

    String[] stateSchool= {"Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

    String selectedSchoolState="";
    String selectedSchoolDistrict="";
    String selectedSchool="";
    String selectedSchoolType="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form3);

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
        String icPr= getIntent().getStringExtra("icPr");
        String namePr= getIntent().getStringExtra("namePr");
        String genderPr= getIntent().getStringExtra("genderPr");
        String racePr= getIntent().getStringExtra("racePr");
        String religionPr= getIntent().getStringExtra("religionPr");
        String nationalityPr= getIntent().getStringExtra("nationalityPr");
        String addressPr= getIntent().getStringExtra("addressPr");
        String postcodePr= getIntent().getStringExtra("postcodePr");
        String statePr= getIntent().getStringExtra("statePr");
        String districtPr= getIntent().getStringExtra("districtPr");
        String telPr= getIntent().getStringExtra("telPr");
        String jobPr= getIntent().getStringExtra("jobPr");
        String salaryPr= getIntent().getStringExtra("salaryPr");
        schoolLevel=getIntent().getStringExtra("SchoolLevel");
        lastfragment = 0;



        submit= findViewById(R.id.btnSubmit);
        back=findViewById(R.id.btnBack_sc);
        distance=findViewById(R.id.etDistance);
        schoolTypeInfo=findViewById(R.id.SchoolTypeInfo);
        distanceInfo=findViewById(R.id.distanceInfo);
        schoolListInfo=findViewById(R.id.SchoolListInfo);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        schoolTypeInfo.setOnClickListener(this);
        distanceInfo.setOnClickListener(this);
        schoolListInfo.setOnClickListener(this);





        loadDSchoolTypeSpinnerData(schoolLevel);
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

        // database handler
        DatabaseAccess db= DatabaseAccess.getInstance(this);

        //confirm button operation
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String distanceForm= distance.getText().toString();
                isAllFieldsChecked= CheckAllField();

                if(isAllFieldsChecked) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SchoolForm3.this);
                    alertDialogBuilder.setMessage("Do you want to submit this application?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //inset data into database here
                                   Boolean insert= db.insertSchoolApplication(icChild,nameChild,genderChild,raceChild,religionChild,nationalityChild,addressChild,postcodeChild,stateChild,
                                            districtChild,telChild,icPr,namePr,genderPr,racePr,religionPr,nationalityPr,addressPr,postcodePr,statePr,districtPr,telPr,jobPr,
                                            salaryPr,selectedSchoolType,selectedSchoolState,selectedSchoolDistrict,selectedSchool,distanceForm,"1");
                                   if(insert){
                                        Toast.makeText(SchoolForm3.this, "Application Form submitted successfully", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(SchoolForm3.this, ApplicationFormSubmit.class);
                                        i.putExtra("user", currentUser);
                                        startActivity(i);

                                        finish();}
                                   else {
                                       Toast.makeText(SchoolForm3.this, "Application submitted not successfully", Toast.LENGTH_LONG).show();
                                   }
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
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
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
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
                loadDSchoolSpinnerData(selectedSchoolDistrict, schoolLevel,selectedSchoolType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });



    }

    private void loadDSchoolSpinnerData(String selectedSchoolDistrict, String schoolLevel, String selectedSchoolType) {


        Spinner school_list_spin = (Spinner) findViewById(R.id.spinnerSchoolList);
        //district_spin.setOnItemSelectedListener(this);

        // database handler
        DatabaseAccess db= DatabaseAccess.getInstance(this);

        // Spinner Drop down elements
        List<String> schools= db.getAllSchoolList(selectedSchoolDistrict, schoolLevel, selectedSchoolType);

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


    private void loadDSchoolTypeSpinnerData(String schoolLevel) {


        Spinner school_type_spin = (Spinner) findViewById(R.id.spinnerSchoolType);
        //district_spin.setOnItemSelectedListener(this);

        // database handler
        DatabaseAccess db= DatabaseAccess.getInstance(this);

        // Spinner Drop down elements
        List<String> schools= db.getAllSchoolType(schoolLevel);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, schools);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        school_type_spin.setAdapter(dataAdapter);

        school_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedSchoolType= school_type_spin.getSelectedItem().toString();

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


}