package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysekolah.PersonalityCareerTest.PersonalTestQuestion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Application_Review extends AppCompatActivity {

    private String applicantname;
    private User currentUser;
    private int lastfragment;
    Boolean updateApprove,updateReject;

    private TextView icSt,nameSt,genderSt,raceSt,religionSt,nationalitySt,
    addSt,pcSt,stateSt,distSt,telSt,icPr,namePr,genderPr,racePr,
    religionPr,nationalityPr,adPr,pcPr,statePr,distPr,telPr,job,
    salary,stateSc,districtSc,nameSc,distance,status;


    Button approve,reject;

    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_review);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        applicantname=getIntent().getStringExtra("applicant");

        //bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //initiate database access
        databaseAccess= DatabaseAccess.getInstance(this);
        databaseAccess.open();


        approve=findViewById(R.id.btn_approve);
        reject=findViewById(R.id.btn_reject);

        icSt=findViewById(R.id.tvIC_st);
        nameSt=findViewById(R.id.tvName_st);
        genderSt=findViewById(R.id.tvgender_st);
        raceSt=findViewById(R.id.tvraces_st);
        religionSt=findViewById(R.id.tvreligion_st);
        nationalitySt=findViewById(R.id.tvrnationality_st);
        addSt=findViewById(R.id.address_st);
        pcSt=findViewById(R.id.postcode_st);
        stateSt=findViewById(R.id.state_st);
        distSt=findViewById(R.id.district_st);
        telSt=findViewById(R.id.tel_st);

        icPr=findViewById(R.id.tvIC_pr);
        namePr=findViewById(R.id.tvName_pr);
        genderPr=findViewById(R.id.tvgender_pr);
        racePr=findViewById(R.id.tvraces_pr);
        religionPr=findViewById(R.id.tvreligion_pr);
        nationalityPr=findViewById(R.id.tvrnationality_pr);
        adPr=findViewById(R.id.address_pr);
        pcPr=findViewById(R.id.postcode_pr);
        statePr=findViewById(R.id.state_pr);
        distPr=findViewById(R.id.district_pr);
        telPr=findViewById(R.id.tel_pr);
        job=findViewById(R.id.job_pr);
        salary=findViewById(R.id.salary_pr);

        stateSc=findViewById(R.id.statesc);
        districtSc=findViewById(R.id.districtsc);
        nameSc=findViewById(R.id.namesc);
        distance=findViewById(R.id.distancesc);
        status=findViewById(R.id.statussc);


        AppEnrol_Info application_record=databaseAccess.DisplayApplication(applicantname);

        icSt.setText(application_record.getStICNo());
        nameSt.setText(application_record.getStName());
        genderSt.setText(application_record.getgenderSt());
        raceSt.setText(application_record.getraceSt());
        religionSt.setText(application_record.getreligionSt());
        nationalitySt.setText(application_record.getnationalitySt());
        addSt.setText(application_record.getaddSt());
        pcSt.setText(application_record.getpcSt());
        stateSt.setText(application_record.getstateSt());
        distSt.setText(application_record.getdistrictSt());
        telSt.setText(application_record.gettelSt());

        icPr.setText(application_record.getPrICNo());
        namePr.setText(application_record.getPrName());
        genderPr.setText(application_record.getgenderPr());
        racePr.setText(application_record.getracePr());
        religionPr.setText(application_record.getreligionPr());
        nationalityPr.setText(application_record.getnationalityPr());
        adPr.setText(application_record.getaddPr());
        pcPr.setText(application_record.getpcPr());
        statePr.setText(application_record.getstatePr());
        distPr.setText(application_record.getdistrictPr());
        telPr.setText(application_record.gettelPr());
        job.setText(application_record.getjob());
        salary.setText(application_record.getsalary());

        stateSc.setText(application_record.getstateSc());
        districtSc.setText(application_record.getDistrictSc());
        nameSc.setText(application_record.getnameSc());
        distance.setText(application_record.getdistance());

        switch (application_record.getstatus()){
            case "1":
                status.setText("Pending");
                break;
            case "2":
                status.setText("Approved");
                break;
            case "3":
                status.setText("Rejected");
                break;
        }


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateApprove = databaseAccess.approve(applicantname);
                        Intent i;
                if (updateApprove) {
                    Toast.makeText(Application_Review.this, "Approved. Updated", Toast.LENGTH_SHORT).show();
                    i= new Intent(Application_Review.this, Application_Review.class);
                    i.putExtra("user",currentUser);
                    i.putExtra("applicant", applicantname);
                    AppEnrol_Info application_record=databaseAccess.DisplayApplication(applicantname);
                    startActivity(i);

                } else {
                    Toast.makeText(Application_Review.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReject= databaseAccess.reject(applicantname);
                    Intent i;
                if (updateReject) {
                    Toast.makeText(Application_Review.this, "Rejected. Updated", Toast.LENGTH_SHORT).show();
                    i= new Intent(Application_Review.this, Application_Review.class);
                    i.putExtra("user",currentUser);
                    i.putExtra("applicant", applicantname);
                    AppEnrol_Info application_record=databaseAccess.DisplayApplication(applicantname);
                    startActivity(i);

                } else {
                    Toast.makeText(Application_Review.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
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