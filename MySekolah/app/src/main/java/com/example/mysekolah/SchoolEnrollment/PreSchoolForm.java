package com.example.mysekolah.SchoolEnrollment;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mysekolah.HomePage;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PreSchoolForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] state= {"Johor", "Kedah", "Kelantan", "Malacca", "Negeri Semnilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

    String[] district= {"Johor", "Kedah", "Kelantan", "Malacca", "Negeri Semnilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

    Button next;
    EditText address,postcode, tel;


    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner state_spin = (Spinner) findViewById(R.id.spinnerState);
        state_spin.setOnItemSelectedListener(this);

        Spinner district_spin = (Spinner) findViewById(R.id.spinnerDistrict);
        district_spin.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter state_aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,state);
        state_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spin.setAdapter(state_aa);

        ArrayAdapter district_aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,district);
        district_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        district_spin.setAdapter(district_aa);

        next= findViewById(R.id.btnNext);

        address=findViewById(R.id.etAddress);
        postcode= findViewById(R.id.etPos);
        tel= findViewById(R.id.etTel);


        //next button operation
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked= CheckAllField();

                if(isAllFieldsChecked) {
                    Intent i = new Intent(PreSchoolForm.this, PreSchoolForm2.class);
                    startActivity(i);
                }
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

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

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}