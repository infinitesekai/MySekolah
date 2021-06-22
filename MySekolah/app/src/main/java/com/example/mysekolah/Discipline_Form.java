package com.example.mysekolah;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mysekolah.bean.DisciplineResultBean;
import com.example.mysekolah.util.MyApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
//discipline form to select the period for discipline checking
public class Discipline_Form extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private TextView tvIc,tvName;

    //string array for year
    String[] year={"2015","2016","2017","2018","2019","2020","2021"};

    Button showbtn;
    private String selectedYear=year[0];
    private String icNo,childName;
    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_form);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //reference to view by id
        tvIc=findViewById(R.id.tvIC);
        tvName=findViewById(R.id.tvName);

        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //get ic no and name selected child from previous page (att_select_child)
        icNo = getIntent().getExtras().getString("icChild");
        childName = getIntent().getExtras().getString("childName");
        //display information
        tvIc.setText(icNo);
        tvName.setText(childName);

        //year spinner
        Spinner year_spin = (Spinner) findViewById(R.id.disc_yearspin);

        //get string of selected year from year spinner when clicked
        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Discipline_Form.this, "Year="+year[position], Toast.LENGTH_SHORT).show();
                selectedYear = year[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //populate spinner control with list item
        //use array adapter to bind items to spinner
        //drop down list
        //year spinner-string array year
        ArrayAdapter yearaa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,year);
        yearaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(yearaa);

        showbtn=findViewById(R.id.btnshow);


        //click on show button
        //start intent to navigate to discipline result page
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess =  DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                ArrayList<DisciplineResultBean> list = databaseAccess.getDisciplineResults(icNo,selectedYear);
                Intent i= new Intent(Discipline_Form.this, Discipline_Result.class);
                i.putExtra("user", currentUser);
                i.putParcelableArrayListExtra("discipline_result",list);
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
                    bundle.putSerializable("user", currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

//        Toast.makeText(getApplicationContext(),school[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}