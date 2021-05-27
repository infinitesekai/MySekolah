package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mysekolah.PersonalityCareerTest.NotificationPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tertiary_Info extends AppCompatActivity implements View.OnClickListener{

    public CardView moe,upu,matrix,stpm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tertiary_info);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        moe=findViewById(R.id.MoECard);
        upu=findViewById(R.id.upuCard);
        matrix=findViewById(R.id.matriculationCard);
        stpm.findViewById(R.id.stpmCard);

        moe.setOnClickListener(this);
        upu.setOnClickListener(this);
        matrix.setOnClickListener(this);
        stpm.setOnClickListener(this);
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

        switch (v.getId()) {
            case R.id.MoECard:
                i=new Intent(Intent.ACTION_VIEW);//startActivity(i);
                i.setData(Uri.parse("https://www.moe.gov.my/en/?view=featured"));
                startActivity(i);
                break;

            case R.id.upuCard:
            i=new Intent(Intent.ACTION_VIEW);//startActivity(i);
            i.setData(Uri.parse("https://upu.mohe.gov.my/"));
            startActivity(i);
            break;

            case R.id.matriculationCard:
                i=new Intent(Intent.ACTION_VIEW);//startActivity(i);
                i.setData(Uri.parse("https://matrikulasi.moe.gov.my/system/permohonan/index.cfm"));
                startActivity(i);
                break;

            case R.id.stpmCard:
                i=new Intent(Intent.ACTION_VIEW);//startActivity(i);
                i.setData(Uri.parse("https://www.mpm.edu.my/en/"));
                startActivity(i);
                break;

        }
    }


}