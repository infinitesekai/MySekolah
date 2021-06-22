package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

//attendance table display for selected month
public class Attendance_Table extends AppCompatActivity {
    private TextView dateView;
    DatabaseAccess databaseAccess;

    //array list to store absent date
    public static ArrayList<String> AbsentDateList;

    private User currentUser;
    private int lastfragment;

    //calendar view to select date
    CalendarView calendarView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_table);


        //bottom bar navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //get current user and last fragment
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //get intent from Attendance_Form (string and int)
        String ic= getIntent().getExtras().getString("ICNo");
        String school= getIntent().getExtras().getString("School");
        String year= getIntent().getExtras().getString("Year");
        String month= getIntent().getExtras().getString("Month");
        int intmonth= getIntent().getExtras().getInt("IntMonth");

        //reference to view by id
        dateView=findViewById(R.id.dateView);
        calendarView=(CalendarView)findViewById(R.id.calendarView);


        //get current date->year,month and day
        LocalDate currentDate=LocalDate.now();
        int thisyear=currentDate.getYear();
        int thismonth= currentDate.getMonthValue();
        int today=currentDate.getDayOfMonth();


        int intday = 1;//set day to 1, use for default display on calendar, first day of the selected month
        int intyear =Integer.valueOf(year);//convert year to integer

        Calendar calendar = Calendar.getInstance();

        //set calendar to selected year and month, day on first day of the month
        calendar.set(Calendar.YEAR, intyear);
        calendar.set(Calendar.MONTH, intmonth);
        calendar.set(Calendar.DAY_OF_MONTH,intday);

        //get time in millisecond, for display the date set for the calendar
        long milliTime = calendar.getTimeInMillis();

        //set the selected date on calendarView
        calendarView.setDate(milliTime, true, true);

        //initialize new array list for absent date list
        AbsentDateList= new ArrayList<String>();

        //call database method to get absent date list for the selected child, school,year and month
        //absent date added to AbsentDateList
        databaseAccess.DisplayAbsentDate(ic,school,year,month);

        //response on date change(click on different date)
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String date= dayOfMonth+"/"+(month+1)+"/"+year;
                Calendar day= Calendar.getInstance();
                day.set(year,month,dayOfMonth);


                if(day.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||day.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                    Toast.makeText(getApplicationContext(), "Weekend on " + date, Toast.LENGTH_SHORT).show();
                    dateView.setText("Weekend on " + date);
                }
                else {

//                    if(year !=intyear || month!=intmonth ){
                    if(year <intyear || month<intmonth ){
                        dateView.setText("Not selected month");
                         Toast.makeText(getApplicationContext(), "Attendance checking only available for selected month. Please select again.", Toast.LENGTH_SHORT).show();

                    }
                    else if((year >thisyear) ||(year >=thisyear && month+1>thismonth) || (year >=thisyear && month+1>=thismonth && dayOfMonth > today)){
                        dateView.setText("Future date");
                        Toast.makeText(getApplicationContext(), "Attendance checking only available for history. Please select again.", Toast.LENGTH_SHORT).show();

                    }
                    else if(year > intyear || (month>intmonth)){
//                    else if((intyear<=thisyear && month<=intmonth && dayOfMonth!=today) ||( intyear<=thisyear && month<=intmonth && dayOfMonth<=today)){
                        dateView.setText("Not selected month");
                        Toast.makeText(getApplicationContext(), "Attendance checking only available for selected month. Please select again.", Toast.LENGTH_SHORT).show();


                    }
                    else {

                        if (AbsentDateList.contains(String.valueOf(dayOfMonth))) {
                            dateView.setText("Absent on " + date);
                            Toast.makeText(getApplicationContext(), "Absent on " + date, Toast.LENGTH_SHORT).show();
                        } else {
                            dateView.setText("Present on " + date);
                            Toast.makeText(getApplicationContext(), "Present on " + date, Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });


        databaseAccess.close();//close database access
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