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
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//attendance form to select the period for attendance checking
public class Attendance_Form extends AppCompatActivity
       {
    //string array for year and month
    String[] year={"2015","2016","2017","2018","2019","2020","2021"};
    String[] month={"January","February","March","April","May","June","July","August","September","October","November","December"};

    Button showbtn;
    TextView ictv,nametv;

    //declare string for selected choice
    String selectedSchool="";
    String selectedYear="";
    String selectedMonth="";

    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_form);


        //bottom bar navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //current user and last fragment information
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //initiate database access
        DatabaseAccess databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //get ic no and name selected child from previous page (att_select_child)
        String ic= getIntent().getExtras().getString("icChild");
        String name =getIntent().getExtras().getString("childName");

        //reference to view by id
        ictv=findViewById(R.id.tvIC);
        nametv=findViewById(R.id.tvName);

        //ic and name display of selected child
        ictv.setText(ic);
        nametv.setText(name);

        //school,year and month spinner
        Spinner school_spin = (Spinner) findViewById(R.id.school_spinner);
        Spinner year_spin = (Spinner) findViewById(R.id.year_spinner);
        Spinner month_spin = (Spinner) findViewById(R.id.month_spinner);

        //get school list of child by ic no
        List<String> SchoolList=databaseAccess.getUserSchool(ic);

        //populate spinner control with list item
        //use array adapter to bind items to spinner
        //drop down list

        //school list spinner-school list from database
        ArrayAdapter schoolaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,SchoolList);
        schoolaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_spin.setAdapter(schoolaa);

        //year spinner-string array year
        ArrayAdapter yearaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        //month spinner-string array month
        ArrayAdapter monthaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,month);
        monthaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spin.setAdapter(monthaa);

        //get string of selected school from school spinner when clicked
        school_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSchool=school_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //get string of selected year from year spinner when clicked
        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear=year_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //get string of selected month from month spinner when clicked
        month_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth=month_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //show button
        showbtn=findViewById(R.id.btnshow);

        //click on show button
        //start intent to navigate to attendance table page for attendance checking
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Attendance_Form.this, Attendance_Table.class);
                //pass intent for selected child's ic and selected information
                i.putExtra("ICNo", ic);
                i.putExtra("Year", selectedYear);
                i.putExtra("School", selectedSchool);
                i.putExtra("Month", selectedMonth);
                i.putExtra("IntMonth", Arrays.asList(month).indexOf(selectedMonth));//month in integer from index for later use

                //pass intent for current user
                i.putExtra("user",currentUser);

                startActivity(i);
            }
        });



    }


   //function for bottom navigation bar
   //back to Parent Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
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