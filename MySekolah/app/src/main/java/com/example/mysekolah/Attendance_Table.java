package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class Attendance_Table extends AppCompatActivity {
    private TextView dateView;
    DatabaseAccess databaseAccess;

    public static ArrayList<String> AbsentDateList;
   // private ArrayAdapter<String> adapter;
    //try mCalendarView to highlight multiple date
/*
    calendarView = ((MCalendarView) view.findViewById(R.id.calendar_exp));

    ArrayList<DateData> dates=new ArrayList<>();
    dates.add(new DateData(2018,04,26));
    dates.add(new DateData(2018,04,27));

    for(int i=0;i<dates.size();i++) {
        calendarView.markDate(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());//mark multiple dates with this code.
    }


    Log.d("marked dates:-",""+calendarView.getMarkedDates());//get all marked dates
    */

    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_table);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();

        String ic= getIntent().getExtras().getString("ICNo");
        String school= getIntent().getExtras().getString("School");
        String year= getIntent().getExtras().getString("Year");
        String month= getIntent().getExtras().getString("Month");

        dateView=findViewById(R.id.dateView);

        calendarView=(CalendarView)findViewById(R.id.calendarView);

//        int intday = 1;
//        int intmonth =Integer.valueOf(month);
//        int intyear =Integer.valueOf(year);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, intyear);
//        calendar.set(Calendar.MONTH, intmonth); calendar.set(Calendar.DAY_OF_MONTH, intday);
//        long milliTime = calendar.getTimeInMillis();
//        calendarView.setDate (milliTime, true, true);

        AbsentDateList= new ArrayList<String>();
        //adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,absentDateList);


        databaseAccess.DisplayAbsentDate(ic,school,year,month);
//        databaseAccess.ShowAbsentDate(ic,school);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //trying to display highlighted date
                //alternative: check with database date and make toast to tell present or absent
                String date= dayOfMonth+"/"+(month+1)+"/"+year;
                Calendar day= Calendar.getInstance();
                day.set(year,month,dayOfMonth);

                if(day.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||day.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                    Toast.makeText(getApplicationContext(), "Weekend on" + date, Toast.LENGTH_SHORT).show();
                    dateView.setText("Weekend on " + date);
                }
                else {

                        if (AbsentDateList.contains(String.valueOf(dayOfMonth)))
                        {
                            dateView.setText("Absent on " + date);
                            Toast.makeText(getApplicationContext(), "Absent on " + date, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dateView.setText("Present on " + date);
                            Toast.makeText(getApplicationContext(), "Present on " + date, Toast.LENGTH_SHORT).show();
                        }

                }

            }
        });


        databaseAccess.close();
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
}