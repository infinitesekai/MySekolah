package com.example.mysekolah.PersonalityCareerTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.HomePage;
import com.example.mysekolah.HomePage_Student;
import com.example.mysekolah.NotificationPage;
import com.example.mysekolah.ProfilePage;
import com.example.mysekolah.R;
import com.example.mysekolah.SearchPage;
import com.example.mysekolah.SearchPage_Student;
import com.example.mysekolah.User;
import com.example.mysekolah.att_select_child;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Past_Child_Test_Result extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;
    Button result_quit;
    TextView H1;
    TextView H2;
    TextView H3;

    String icChild;
    String childName;
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
        setContentView(R.layout.activity_past_child_test_result);

        childName= getIntent().getStringExtra("childName");
        icChild= getIntent().getStringExtra("icChild");
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        H1=findViewById(R.id.highest_result1_child);
        H2=findViewById(R.id.highest_result2_child);
        H3=findViewById(R.id.highest_result3_child);

        result1=findViewById(R.id.result_1_child);
        result2=findViewById(R.id.result_2_child);
        result3=findViewById(R.id.result_3_child);

        desc1=findViewById(R.id.description11_child);
        exp1=findViewById(R.id.explanation_child);
        sug1=findViewById(R.id.suggestedField_child);

        desc2=findViewById(R.id.description22_child);
        exp2=findViewById(R.id.explanation2_child);
        sug2=findViewById(R.id.suggestedField2_child);

        desc3=findViewById(R.id.description33_child);
        exp3=findViewById(R.id.explanation3_child);
        sug3=findViewById(R.id.suggestedField3_child);

        name=findViewById(R.id.tester_name_child);
        testeric=findViewById(R.id.tester_ic_child);


        expandable_view = findViewById(R.id.expandable_view_child);
        expandable_view2 = findViewById(R.id.expandable_view2_child);
        expandable_view3 = findViewById(R.id.expandable_view3_child);

        imageView = findViewById(R.id.arrow_expand_child);
        imageView2 = findViewById(R.id.arrow_expand2_child);
        imageView3 = findViewById(R.id.arrow_expand3_child);

        cardView = findViewById(R.id.result_card_child);
        cardView2 = findViewById(R.id.result_card2_child);
        cardView3 = findViewById(R.id.result_card3_child);

        result_quit = findViewById(R.id.result_quit_child);


        name.setText(childName);
        testeric.setText(icChild);


        resultInfo=dbAccess.getPastResult(icChild);

        String firstChar= resultInfo.getfirstChar();
        String secondChar= resultInfo.getsecondChar();
        String thirdChar= resultInfo.getthirdChar();
        List<String> chars = new ArrayList<>();
        chars.add(firstChar);
        chars.add(secondChar);
        chars.add(thirdChar);

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

        for (int i = 0; i < chars.size(); i++) {

            switch (chars.get(0)) {
                case "R":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    break;
                case "I":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    break;
                case "A":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    break;
                case "S":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    break;
                case "E":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    break;
                case "C":
                    cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    break;
            }
            switch (chars.get(1)) {
                case "R":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    break;
                case "I":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    break;
                case "A":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    break;
                case "S":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    break;
                case "E":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    break;
                case "C":
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    break;
            }
            switch (chars.get(2)) {
                case "R":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyOrange));
                    break;
                case "I":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyBlue));
                    break;
                case "A":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyPurple));
                    break;
                case "S":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyGreen));
                    break;
                case "E":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darkPurple));
                    break;
                case "C":
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greyRed));
                    break;
            }
        }
        result_quit.setOnClickListener(this);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//the value that we need to pass
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
                    bundle.putSerializable("user",currentUser);//the value that we need to pass
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//the value that we need to pass
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
        String personalityTestMessage = "personality";

        switch (v.getId()){
            case R.id.result_quit_child:
                intent = new Intent(this, att_select_child.class);
                intent.putExtra("user",currentUser);
                intent.putExtra("ICNo", currentUser.getICNo());
                intent.putExtra("message", personalityTestMessage);
                startActivity(intent);
                break;



        }
    }
}