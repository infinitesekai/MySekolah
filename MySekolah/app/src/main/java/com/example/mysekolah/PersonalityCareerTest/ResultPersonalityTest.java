package com.example.mysekolah.PersonalityCareerTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mysekolah.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ResultPersonalityTest extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;
    Button result_quit, result_export;
    ImageButton image_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_personality_test);

        expandable_view = findViewById(R.id.expandable_view);
        expandable_view2 = findViewById(R.id.expandable_view2);
        expandable_view3 = findViewById(R.id.expandable_view3);

        imageView = findViewById(R.id.arrow_expand);
        imageView2 = findViewById(R.id.arrow_expand2);
        imageView3 = findViewById(R.id.arrow_expand3);

        cardView = findViewById(R.id.result_card);
        cardView2 = findViewById(R.id.result_card2);
        cardView3 = findViewById(R.id.result_card3);

        result_quit = findViewById(R.id.result_quit);
        result_export = findViewById(R.id.result_export);

        image_btn = findViewById(R.id.imageButton);

        result_export.setOnClickListener(this);
        result_quit.setOnClickListener(this);
        image_btn.setOnClickListener(this);


    }

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
            case R.id.result_quit:
                intent = new Intent(this, PersonalityTestHome.class);
                startActivity(intent);
                break;
            case R.id.result_export:

                break;
            case  R.id.imageButton:
                intent = new Intent(this, ResultInfo.class);
                startActivity(intent);
                break;
        }
    }
}