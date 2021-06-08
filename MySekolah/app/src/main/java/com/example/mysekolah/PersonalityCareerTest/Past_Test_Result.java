package com.example.mysekolah.PersonalityCareerTest;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage_Student;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage_Student;
import com.example.mysekolah.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.telephony.SignalStrength;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysekolah.databinding.ActivityResultPersonalityTestBinding;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Past_Test_Result extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;
    Button result_quit;
    TextView H1;
    TextView H2;
    TextView H3;

    private User currentUser;
    private int lastfragment;
    DatabaseAccess dbAccess;
    TestCharResult resultInfo;
    TestResultInfo testInfo1;
    TestResultInfo testInfo2;
    TestResultInfo testInfo3;

    TextView result1;
    TextView result2;
    TextView result3;
    TextView desc1;
    TextView desc2;
    TextView desc3;
    TextView exp1;
    TextView exp2;
    TextView exp3;
    TextView sug1;
    TextView sug2;
    TextView sug3;
    TextView name;
    TextView testeric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_test_re);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        H1=findViewById(R.id.highest_result1_past);
        H2=findViewById(R.id.highest_result2_past);
        H3=findViewById(R.id.highest_result3_past);

        result1=findViewById(R.id.result_1_past);
        result2=findViewById(R.id.result_2_past);
        result3=findViewById(R.id.result_3_past);

        desc1=findViewById(R.id.description11_past);
        exp1=findViewById(R.id.explanation_past);
        sug1=findViewById(R.id.suggestedField_past);

        desc2=findViewById(R.id.description22_past);
        exp2=findViewById(R.id.explanation2_past);
        sug2=findViewById(R.id.suggestedField2_past);

        desc3=findViewById(R.id.description33_past);
        exp3=findViewById(R.id.explanation3_past);
        sug3=findViewById(R.id.suggestedField3_past);

        name=findViewById(R.id.tester_name_past);
        testeric=findViewById(R.id.tester_ic_past);


        expandable_view = findViewById(R.id.expandable_view_past);
        expandable_view2 = findViewById(R.id.expandable_view2_past);
        expandable_view3 = findViewById(R.id.expandable_view3_past);

        imageView = findViewById(R.id.arrow_expand_past);
        imageView2 = findViewById(R.id.arrow_expand2_past);
        imageView3 = findViewById(R.id.arrow_expand3_past);

        cardView = findViewById(R.id.result_card_past);
        cardView2 = findViewById(R.id.result_card2_past);
        cardView3 = findViewById(R.id.result_card3_past);

        result_quit = findViewById(R.id.result_quit_past);


        name.setText(currentUser.getName());
        testeric.setText(currentUser.getICNo());



        resultInfo=dbAccess.getPastResult(currentUser.getICNo());

        String firstChar= resultInfo.getfirstChar().toString();
        String secondChar= resultInfo.getsecondChar().toString();
        String thirdChar= resultInfo.getthirdChar().toString();

        H1.setText(firstChar);
        H2.setText(secondChar);
        H3.setText(thirdChar);

        testInfo1=dbAccess.getTestInfo(firstChar);

        testInfo2=dbAccess.getTestInfo(secondChar);

        testInfo3=dbAccess.getTestInfo(thirdChar);



        result1.setText(testInfo1.getAlpName());
        desc1.setText(testInfo1.getDesc());
        exp1.setText(testInfo1.getExp());
        sug1.setText(testInfo1.getField());

        result2.setText(testInfo2.getAlpName());
        desc2.setText(testInfo2.getDesc());
        exp2.setText(testInfo2.getExp());
        sug2.setText(testInfo2.getField());

        result3.setText(testInfo3.getAlpName());
        desc3.setText(testInfo3.getDesc());
        exp3.setText(testInfo3.getExp());
        sug3.setText(testInfo3.getField());

        result_quit.setOnClickListener(this);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
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
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
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

    //expand and show less
    public void showmore(View view){
        if (expandable_view.getVisibility() == View.GONE){
            imageView.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandable_view.setVisibility(View.VISIBLE);
        }else {
            imageView.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandable_view.setVisibility(View.GONE);
        }
    }
    public void showmore2(View view){
        if (expandable_view2.getVisibility() == View.GONE){
            imageView2.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            expandable_view2.setVisibility(View.VISIBLE);
        }else {
            imageView2.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
            expandable_view2.setVisibility(View.GONE);
        }
    }

    public void showmore3(View view){
        if (expandable_view3.getVisibility() == View.GONE){
            imageView3.setImageResource(R.drawable.arrow_up);
            TransitionManager.beginDelayedTransition(cardView3, new AutoTransition());
            expandable_view3.setVisibility(View.VISIBLE);
        }else {
            imageView3.setImageResource(R.drawable.arrow);
            TransitionManager.beginDelayedTransition(cardView3, new AutoTransition());
            expandable_view3.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.result_quit_past:
                intent = new Intent(this, PersonalityTestHome.class);
                intent.putExtra("user",currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                startActivity(intent);
                break;


        }
    }




}