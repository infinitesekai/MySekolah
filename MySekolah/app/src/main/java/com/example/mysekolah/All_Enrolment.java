package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class All_Enrolment extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;

    DatabaseAccess databaseAccess;

    //list view, list item and adapter to display list of application
    ListView enrol_list;
    public static ArrayList<String> list_item;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_enrolment);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //reference to list view by id
        enrol_list=findViewById(R.id.enrol_list);

        //array list to store list item
        list_item=new ArrayList<String>();



        //call database method to get application list
        //child added into list_item
        //list_item is the list of application for children
        databaseAccess.getEnrolmentList();

        if(list_item.isEmpty()){
            Toast.makeText(All_Enrolment.this, "No Application", Toast.LENGTH_SHORT).show();

        }

        //array adapter for ListView display-insert item into ListView from list_item
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_item);

        enrol_list.setAdapter(adapter);//conjoin array adapter with list view

        enrol_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected child name from list according to the position
                String applicantName=enrol_list.getItemAtPosition(position).toString();
                Intent i;
                i= new Intent(All_Enrolment.this, Application_Review.class);
                i.putExtra("user",currentUser);
                i.putExtra("applicant", applicantName);
                startActivity(i);
            }
        });

    }


    //function for bottom navigation bar
    //back to Admin Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new Homepage_Admin();
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
                    selectedFragment = new SearchPage_Admin();
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