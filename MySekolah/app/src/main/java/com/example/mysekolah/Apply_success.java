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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//application success page
public class Apply_success extends AppCompatActivity {


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
        setContentView(R.layout.activity_apply_success);

        dialog=new Dialog(this);//initialize new dialog


        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //get selected child name
        childname=getIntent().getStringExtra("childname");


        //reference to view by id
        icno=findViewById(R.id.icNoApproved);
        name=findViewById(R.id.nameApproved);
        school=findViewById(R.id.schoolApproved);


        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();


        //call database access to display status information
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

        //dialog for pop up approve info
        dialog.setContentView(R.layout.popup_approve);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close=(TextView) dialog.findViewById(R.id.close_success);

        //dismiss dialog when click on close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();//show pop up approve dialog
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