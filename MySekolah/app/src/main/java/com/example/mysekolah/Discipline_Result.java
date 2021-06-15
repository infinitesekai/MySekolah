package com.example.mysekolah;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mysekolah.bean.DisciplineResultBean;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Discipline_Result extends AppCompatActivity {
    private ArrayList<DisciplineResultBean> disciplineResultBeans;
    private User currentUser;
    private TextView tvScore,tvGrade,tvHardworking,tvResponsible,tvLeadership,tvDecicate,tvPoliteness,tvHonesty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_result);

        currentUser = (User) getIntent().getSerializableExtra("user");

//        tvScore=findViewById(R.id.tv_score);
//        tvGrade=findViewById(R.id.tv_grade);
//        tvHardworking=findViewById(R.id.tv_hardworking);
//        tvResponsible=findViewById(R.id.tv_responsible);
//        tvLeadership=findViewById(R.id.tv_leadership);
//        tvDecicate=findViewById(R.id.tv_dedicate);
//        tvPoliteness=findViewById(R.id.tv_politeness);
//        tvHonesty = findViewById(R.id.tv_honesty);
//
//        disciplineResultBeans= getIntent().getParcelableArrayListExtra("discipline_result");
//        System.out.println(disciplineResultBeans);
//
//        if (disciplineResultBeans != null && disciplineResultBeans.size() > 0) {
//            for (DisciplineResultBean bean : disciplineResultBeans) {
//                tvScore.setText(bean.getDiscScore()+"");
//                tvGrade.setText(bean.getGrade());
//                tvHardworking.setText(bean.getHardWorking());
//                tvResponsible.setText(bean.getResponsible());
//                tvLeadership.setText(bean.getLeadership());
//                tvDecicate.setText(bean.getDedicate());
//                tvPoliteness.setText(bean.getPoliteness());
//                tvHonesty.setText(bean.getHonesty());
//            }
//        }


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

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
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
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