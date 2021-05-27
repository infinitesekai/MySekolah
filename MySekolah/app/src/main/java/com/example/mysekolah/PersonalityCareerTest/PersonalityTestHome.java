package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mysekolah.InstuctionPage;
import com.example.mysekolah.R;

public class PersonalityTestHome extends AppCompatActivity implements View.OnClickListener {

    CardView take_test, test_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test_home);

        take_test = findViewById(R.id.takeTestCard);
        test_result = findViewById(R.id.checkResultCard);

        take_test.setOnClickListener(this);
        test_result.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.takeTestCard:
                intent = new Intent(this, InstuctionPage.class);
                startActivity(intent);
                break;
            case R.id.checkResultCard:
                intent = new Intent(this, ResultPersonalityTest.class);
                startActivity(intent);
                break;
        }
    }
}