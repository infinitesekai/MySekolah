package com.example.mysekolah.SchoolEnrollment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PreSchoolForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] state= {"Johor", "Kedah", "Kelantan", "Malacca", "Negeri Semnilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};

   /* private String[] district= {"Johor", "Kedah", "Kelantan", "Malacca", "Negeri Semnilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
            "Putarjaya", "Labuan"};*/

    private Button next;
    private EditText address,postcode, tel;


    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    private boolean isAllFieldsChecked = false;

    private TextView ic_child, name_child, gender_child, race_child, religion_child, nationality_child;
    //private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form);

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
        //dbHelper= new DatabaseHelper(getApplicationContext());
        DatabaseAccess databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();


        String check_IC_child= getIntent().getExtras().getString("ICNo");
        //Residents residents= dbHelper.getResidentbyIC(check_IC_child);
        Residents residents= databaseAccess.getResidentbyIC(check_IC_child);
        ic_child.setText(residents.getICNo());
        name_child.setText(residents.getName());
        gender_child.setText(residents.getGender());
        race_child.setText(residents.getRaces());
        religion_child.setText(residents.getReligion());
        nationality_child.setText(residents.getNationality());



        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner state_spin = (Spinner) findViewById(R.id.spinnerState);
        state_spin.setOnItemSelectedListener(this);



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter state_aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,state);
        state_aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spin.setAdapter(state_aa);




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

    private void loadSpinnerData(String selectedState) {

        Spinner district_spin = (Spinner) findViewById(R.id.spinnerDistrict);
        district_spin.setOnItemSelectedListener(this);

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
        if (parent.getId()== R.id.spinnerState){
            String selectedState = parent.getItemAtPosition(position).toString();
            loadSpinnerData(selectedState);

        }else if (parent.getId()==R.id.spinnerDistrict){
            String selectedDistrict = parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}