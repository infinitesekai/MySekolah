package com.example.mysekolah.PersonalityCareerTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mysekolah.R;
import com.example.mysekolah.User;

public class InstructionPage extends Activity {

    Button start_test;

    //initialize user
    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_page);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        start_test = findViewById(R.id.start_test);

        start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_test();
            }
        });

        //describing general information about a display of this activity
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //set the width of this window layout
        getWindow().setLayout((int) (width * .8), (int) (height * .75));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        //set the position of this layout
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    private void start_test() {
        Intent intent = new Intent(InstructionPage.this, PersonalTestQuestion.class);
        intent.putExtra("user", currentUser);
        intent.putExtra("ICNo", currentUser.getICNo());
        startActivity(intent);
    }
}