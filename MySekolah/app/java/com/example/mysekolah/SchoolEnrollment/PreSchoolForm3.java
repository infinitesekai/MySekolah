//package com.example.mysekolah;
package com.example.mysekolah.SchoolEnrollment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mysekolah.HomePage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PreSchoolForm3 extends AppCompatActivity implements View.OnClickListener {

    EditText distance;
    ImageButton schoolTypeInfo, schoolListInfo, distanceInfo;
    Button next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_school_form3);

        next= findViewById(R.id.btnNext);
        back=findViewById(R.id.btnBack);
        schoolTypeInfo=findViewById(R.id.SchoolTypeInfo);
        distanceInfo=findViewById(R.id.distanceInfo);
        schoolListInfo=findViewById(R.id.SchoolListInfo);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        schoolTypeInfo.setOnClickListener(this);
        distanceInfo.setOnClickListener(this);
        schoolListInfo.setOnClickListener(this);

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
}