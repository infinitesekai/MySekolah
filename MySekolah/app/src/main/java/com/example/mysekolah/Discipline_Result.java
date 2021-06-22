package com.example.mysekolah;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mysekolah.bean.DisciplineResultBean;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
//discipline table
public class Discipline_Result extends AppCompatActivity {
    private ArrayList<DisciplineResultBean> disciplineResultBeans;////array list disciplineresultbeans
    private User currentUser;//current user
    private TextView tvScore,tvGrade,tvHardworking,tvResponsible,tvLeadership,tvDecicate,tvPoliteness,tvHonesty;// text
    private Button btn_export;//export button
    private int lastfragment;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_result);
        lastfragment=0;
        currentUser = (User) getIntent().getSerializableExtra("user");//get intent for current user

        //reference to view by id
        tvScore=findViewById(R.id.tv_score);
        tvGrade=findViewById(R.id.tv_grade);
        tvHardworking=findViewById(R.id.tv_hardworking);
        tvResponsible=findViewById(R.id.tv_responsible);
        tvLeadership=findViewById(R.id.tv_leadership);
        tvDecicate=findViewById(R.id.tv_dedicate);
        tvPoliteness=findViewById(R.id.tv_politeness);
        tvHonesty = findViewById(R.id.tv_honesty);
        btn_export = findViewById(R.id.btn_export);

        //initial,get,printout discipline result
        dialog=new Dialog(Discipline_Result.this);
        disciplineResultBeans= getIntent().getParcelableArrayListExtra("discipline_result");
        System.out.println(disciplineResultBeans);
        //
        if (disciplineResultBeans != null && disciplineResultBeans.size() > 0) {
            for (DisciplineResultBean bean : disciplineResultBeans) {
                tvScore.setText(bean.getDiscScore()+"");
                tvGrade.setText(bean.getGrade());
                tvHardworking.setText(bean.getHardWorking());
                tvResponsible.setText(bean.getResponsible());
                tvLeadership.setText(bean.getLeadership());
                tvDecicate.setText(bean.getDedicate());
                tvPoliteness.setText(bean.getPoliteness());
                tvHonesty.setText(bean.getHonesty());
              //button remarks
                btn_export.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if(bean.getRemarks() == 1){
                        if(bean.getDiscScore()>=70){
                            showPopup(R.layout.discipline_popup_good);//discipline good popup
                        }else {
                            showPopup(R.layout.discipline_popup_bad);//discipline bad popup
                        }


                    }
                });
            }
        }
        //can not find data in this year
        else{
            Toast.makeText(Discipline_Result.this,"No Discipline Result",Toast.LENGTH_LONG).show();
        }
        //navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    //show the dialog window
    public void showPopup(int layId){
        dialog.setContentView(layId);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //function for bottom navigation bar
    //back to Home Page
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            Bundle bundle = new Bundle();

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
}