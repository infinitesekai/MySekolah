package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mysekolah.R;

public class InstuctionPage extends AppCompatActivity {

    Button start_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instuction_page);

        start_test = findViewById(R.id.start_test);

        start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstuctionPage.this, PersonalTestQuestion.class);
                startActivity(intent);
                finish();
            }
        });
    }
}