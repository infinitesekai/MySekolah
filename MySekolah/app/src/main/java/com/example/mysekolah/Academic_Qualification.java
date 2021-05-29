package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Academic_Qualification extends AppCompatActivity {
    private TextView ic, name, preschool, pre_year, primary_school, primary_year, secondary_school, secondary_year, qualification, qualification_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_qualification);

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

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        String current_IC = getIntent().getExtras().getString("ICNo");
        Residents residents = databaseAccess.getResidentbyIC(current_IC);
       /// Qualification qualification_record=databaseAccess.DisplayQualification(current_IC);

        ic.setText(residents.getICNo());
        name.setText(residents.getName());

//       ic.setText(qualification_record.getICNo());
//        name.setText(qualification_record.getName());



//        Qualification qualification_record = databaseAccess.DisplayQualification(current_IC);

//           preschool.setText(qualification_record.getPreSchool());
//            pre_year.setText(qualification_record.getPreYear());
//            primary_school.setText(qualification_record.getPrimarySchool());
//            primary_year.setText(qualification_record.getPrimaryYear());
//        secondary_school.setText(qualification_record.getSecondarySchool());
//        secondary_year.setText(qualification_record.getSecondaryYear());
//            qualification.setText(qualification_record.getqualification());
//            qualification_year.setText(qualification_record.getqualificationYear());

        databaseAccess.close();





    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
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
}