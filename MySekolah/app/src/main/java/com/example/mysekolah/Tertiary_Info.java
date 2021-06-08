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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tertiary_Info extends AppCompatActivity implements View.OnClickListener{

    public CardView moe,upu,matrix,stpm;
    private User currentUser;
    private int lastfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tertiary_info);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        moe=findViewById(R.id.MoECard);
        upu=findViewById(R.id.upuCard);
        matrix=findViewById(R.id.matriculationCard);
        stpm=findViewById(R.id.stpmCard);

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
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);
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
                    bundle.putSerializable("user",currentUser);
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