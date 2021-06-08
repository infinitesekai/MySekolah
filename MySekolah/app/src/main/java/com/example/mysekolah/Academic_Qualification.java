package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Academic_Qualification extends AppCompatActivity {
    private TextView ic, name, preschool, pre_year, primary_school, primary_year, secondary_school, secondary_year, qualification, qualification_year;
    private Button btnexport;
    private User currentUser;
    private int lastfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_qualification);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ic = findViewById(R.id.tvIC);
        name = findViewById(R.id.tvName);
        preschool = findViewById(R.id.tvPre);
        pre_year = findViewById(R.id.tvPreYear);
        primary_school = findViewById(R.id.tvPrimary);
        primary_year = findViewById(R.id.tvPrimaryYear);
        secondary_school = findViewById(R.id.tvSecondary);
        secondary_year = findViewById(R.id.tvSecondaryYear);
        qualification = findViewById(R.id.tvQualification);
        qualification_year = findViewById(R.id.tvQualificationYear);

        btnexport=findViewById(R.id.btnexport);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        String current_IC = getIntent().getExtras().getString("ICNo");
//        Residents residents = databaseAccess.getResidentbyIC(current_IC);
        Qualification qualification_record=databaseAccess.DisplayQualification(current_IC);

//        ic.setText(residents.getICNo());
//        name.setText(residents.getName());

       ic.setText(qualification_record.getICNo());
        name.setText(qualification_record.getName());



//        Qualification qualification_record = databaseAccess.DisplayQualification(current_IC);

            preschool.setText(qualification_record.getPreSchool());
            pre_year.setText(qualification_record.getPreYear());
            primary_school.setText(qualification_record.getPrimarySchool());
            primary_year.setText(qualification_record.getPrimaryYear());
            secondary_school.setText(qualification_record.getSecondarySchool());
            secondary_year.setText(qualification_record.getSecondaryYear());
            qualification.setText(qualification_record.getqualification());
            qualification_year.setText(qualification_record.getqualificationYear());

        databaseAccess.close();

        btnexport=findViewById(R.id.btnexport);

        btnexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Academic_Qualification.this, Export_Qualification.class);
                i.putExtra("user",currentUser);
                i.putExtra("ICNo", current_IC);
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
                    selectedFragment = new HomePage_Student();
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
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };



}