package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
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
import com.example.mysekolah.Residents;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PreSchoolForm extends AppCompatActivity{

    private String[] state= {"Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};



    private Button next;
    private EditText address,postcode, tel;



    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    private boolean isAllFieldsChecked = false;

    private TextView ic_child, name_child, gender_child, race_child, religion_child, nationality_child, pageTitle;


    String schoolLevel;
    private User currentUser;
    private int lastfragment;

    String selectedState="";
    String selectedDistrict="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form);

        //get the value pass from the previous activity
        schoolLevel=getIntent().getStringExtra("SchoolLevel");
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        ic_child= findViewById(R.id.tvIC);
        name_child= findViewById(R.id.tvName);
        gender_child= findViewById(R.id.tvGender);
        race_child= findViewById(R.id.tvRace);
        religion_child= findViewById(R.id.tvReligion);
        nationality_child= findViewById(R.id.tvNationality);
        address=findViewById(R.id.etAddress);
        postcode= findViewById(R.id.etPos);
        tel= findViewById(R.id.etTel);
        next= findViewById(R.id.btnNext);
        pageTitle=findViewById(R.id.pageTitle);



        //Database
        DatabaseAccess databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //Set the TextView in XML by using the value extracted from SQLite
        String check_IC_child= getIntent().getExtras().getString("ICNo");
        Residents residents= databaseAccess.getResidentbyIC(check_IC_child);
        ic_child.setText(residents.getICNo());
        name_child.setText(residents.getName());
        gender_child.setText(residents.getGender());
        race_child.setText(residents.getRaces());
        religion_child.setText(residents.getReligion());
        nationality_child.setText(residents.getNationality());



        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner state_spin = (Spinner) findViewById(R.id.spinnerState);

        //Creating the ArrayAdapter instance having the state list
        ArrayAdapter state_aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, state);
        state_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        state_spin.setAdapter(state_aa);

        state_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //get the selected state on spinner
                selectedState=state_spin.getSelectedItem().toString();

                //load the district spinner according to the selected state value
                loadSpinnerData(selectedState);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



        //next button operation
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the form value
                String icChild= ic_child.getText().toString();
                String nameChild= name_child.getText().toString();
                String genderChild= gender_child.getText().toString();
                String raceChild= race_child.getText().toString();
                String religionChild= religion_child.getText().toString();
                String nationalityChild= nationality_child.getText().toString();
                String addressChild= address.getText().toString();
                String postcodeChild= postcode.getText().toString();
                String telChild= tel.getText().toString();

                //Check whether all field had been filled up by user
                isAllFieldsChecked= CheckAllField();

                if(isAllFieldsChecked) {
                    //pass all the values into next activity
                    Intent i = new Intent(PreSchoolForm.this, PreSchoolForm2.class);
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
                    i.putExtra("stateChild", selectedState);
                    i.putExtra("districtChild", selectedDistrict);
                    i.putExtra("user", currentUser);
                    startActivity(i);
                }
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    //Populate the district spinner with the data from SQLite
    private void loadSpinnerData(String selectedState) {

        Spinner district_spin = (Spinner) findViewById(R.id.spinnerDistrict);

        // database handler
        DatabaseAccess db= DatabaseAccess.getInstance(this);

        // Spinner Drop down elements
        List<String> districts= db.getAllDistrict(selectedState);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, districts);

        // Drop down layout style
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        district_spin.setAdapter(dataAdapter);


        district_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                //get the selected district value
                selectedDistrict= district_spin.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


    }

    //check the required field filled up by user or not
    private boolean CheckAllField() {
        if (address.length()==0){
            address.setError("This field is required");
            return false;
        }
        if(postcode.length()==0){
            postcode.setError("This field is required");
            return false;
        }

        return true;
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