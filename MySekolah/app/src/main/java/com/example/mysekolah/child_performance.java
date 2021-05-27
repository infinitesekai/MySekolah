package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mysekolah.PersonalityCareerTest.NotificationPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class child_performance extends AppCompatActivity implements View.OnClickListener{

    public CardView exam,discipline,attendance,personalitytest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_performance);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        attendance=findViewById(R.id.AttendanceCard);
        exam=findViewById(R.id.ExamResultCard);
       // discipline=findViewById(R.id.DisciplineCard);
       // personalitytest.findViewById(R.id.PersonalityCard);

        attendance.setOnClickListener(this);
        exam.setOnClickListener(this);
       // discipline.setOnClickListener(this);
       // personalitytest.setOnClickListener(this);

        /*attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(child_performance.this, att_select_child.class);
                startActivity(i);
            }
        });*/


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
        Intent i;
        String examMessage= "exam";
        String attendanceMessage= "attendance";

        switch (v.getId()) {
            case R.id.AttendanceCard:
                i = new Intent(this, att_select_child.class);
                i.putExtra("message",attendanceMessage);
                startActivity(i);
                break;
            case R.id.ExamResultCard:
                i = new Intent(this, att_select_child.class);
                i.putExtra("message",examMessage);
                startActivity(i);
                break;
        }
    }

}