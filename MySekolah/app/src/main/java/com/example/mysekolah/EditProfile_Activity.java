package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mysekolah.util.MyApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EditProfile_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView icText;//ic text
    private TextView nameText;//name text
    private TextView genderText;//gender text
    private TextView raceText;//race text

    private DatePicker datePicker;//date picker from calendar
    private Button dateBtn;//date button
    private EditText jobEdit;// job edit filed
    private EditText salaryEdit;//salary edit filed
    private EditText addressEdit;//address edit filed
    private EditText phoneEdit;//phone edit filed
    private Button cancelBtn;//cance button
    private Button saveBtn;//save button
    private int lastfragment;

    private ArrayList<String> races = new ArrayList<String>();
    private ArrayList<String> nations = new ArrayList<String>();//array lists

    private User currentUser;
    //private DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lastfragment=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //get intent for current user
       currentUser = (User) getIntent().getSerializableExtra("user");

        //navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //init views
        initViews();
    }
    //  init views
    private void initViews() {

        //reference to view by id
        icText = findViewById(R.id.tv_IC);
        nameText = findViewById(R.id.tv_Name);
        genderText = findViewById(R.id.tv_gender);
        raceText = findViewById(R.id.tv_races);
        dateBtn = findViewById(R.id.et_bDate);
        jobEdit = findViewById(R.id.et_job);
        salaryEdit = findViewById(R.id.et_salary);
        addressEdit = findViewById(R.id.et_address);
        phoneEdit = findViewById(R.id.et_phoneno);
        cancelBtn = findViewById(R.id.btncancel);
        saveBtn = findViewById(R.id.btnsave);

        //get intent for current user
        currentUser = (User) getIntent().getSerializableExtra("user");

        icText.setText(currentUser.getICNo());
        nameText.setText(currentUser.getName());
        genderText.setText(currentUser.getGender());
        raceText.setText(currentUser.getRace());
        jobEdit.setText(currentUser.getJob());
        salaryEdit.setText(currentUser.getSalary());
        addressEdit.setText(currentUser.getAddress());
        phoneEdit.setText(currentUser.getPhoneNo());
        dateBtn.setText(currentUser.getBdate());

        //initiate database access and open database
        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();

        //click date and show calendar
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //Gets an instance of a calendar that contains the current year, month and day
                Calendar calendar=Calendar.getInstance();
                //Build a date dialog that has a date picker integrated
                //The second construction parameter of DatePickerDialog specifies the date listener
                DatePickerDialog dialog=new DatePickerDialog(EditProfile_Activity.this,EditProfile_Activity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //Displays the date dialog on the screen
                dialog.show();
            }
        });

        //on click listener for cancel button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //on click listener for save button
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String job = jobEdit.getText().toString();
                String salary = salaryEdit.getText().toString();
                String address = addressEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                currentUser.setJob(job == null ? "":job);
                currentUser.setSalary(salary == null ? "":salary);
                currentUser.setAddress(address == null ? "":address);
                currentUser.setPhoneNo(phone == null ? "":phone);
                Boolean result = DB.updateUser(currentUser);

                if (result) {

                    Toast.makeText(EditProfile_Activity.this,"save successfully",Toast.LENGTH_SHORT).show();
                    Fragment selectedFragment = null;
                    selectedFragment = new ProfilePage();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value user
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                } else {
                    Toast.makeText(EditProfile_Activity.this,"save failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        //Implement dateset listener
        //Gets the month of the year set by the date dialog.
        String bdate = String.format("%d-%d-%d",i,i1+1,i2);
        dateBtn.setText(bdate);
        currentUser.setBdate(bdate);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // getaction when the user clicks
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();     // Gets the focus of the current page

            // Determine if the user clicked outside of the input field
            if (isShouldHideKeyboard(v, me)) {
                hideKeyboard(v.getWindowToken());   //hide the keyboard
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * Hide the keyboard based on the location of the EditText relative to the location the user clicked on, because it cannot be hidden when the user clicked on EditText
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        // Check if the resulting focus contains EditText
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);

            // Gets the position of the input field on the screen
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // Click the location if it is the EditText area, ignore it and do not close the keyboard.
                return false;
            } else {
                return true;
            }
        }
        // If the focus is not EditText, ignore it
        return false;
    }

    /**
     * Get InputMethodManager and hide the soft keyboard
     * @param token
     */
    //function for bottom navigation bar
    //back to Student Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    if (currentUser.getRole() == 1) {
                        selectedFragment = new HomePage();
                    } else {
                        selectedFragment = new HomePage_Student();
                    }
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;

                case R.id.nav_profile:

                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    if (currentUser.getRole() == 1) {
                        selectedFragment = new SearchPage();
                    } else {
                        selectedFragment = new SearchPage_Student();
                    }
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
