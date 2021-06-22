package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//application pending page
public class Apply_pending extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    Dialog dialog;
    private String childname;
    TextView icno,name,school;
    StatusInfo info;//application status information

    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_pending);
        //initalize new dialog
        dialog=new Dialog(this);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //get selected child name
        childname=getIntent().getStringExtra("childname");

        //reference view by id
        icno=findViewById(R.id.icNoPending);
        name=findViewById(R.id.namePending);
        school=findViewById(R.id.schoolPending);

        //initiate database access and open database
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //call database method to get status information
        info=databaseAccess.getStatusInfo(childname);

        //display application status information
        icno.setText(info.getICNo());
        name.setText(info.getName());
        school.setText(info.getSchool());

        databaseAccess.close();//close database access


        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    //show pop up when click on info image button
    public void showPopup(View v){
        TextView close;//close button with text view

        //dialog for pop up pending information
        dialog.setContentView(R.layout.popup_pending);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close=(TextView) dialog.findViewById(R.id.close_pending);//close button

        //dismiss dialog when click on close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();//show pop up pending dialog
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