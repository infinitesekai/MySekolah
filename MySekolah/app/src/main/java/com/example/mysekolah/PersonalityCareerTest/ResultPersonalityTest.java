package com.example.mysekolah.PersonalityCareerTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mysekolah.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mysekolah.databinding.ActivityResultPersonalityTestBinding;

public class ResultPersonalityTest extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandable_view, expandable_view2, expandable_view3;
    ImageView imageView, imageView2, imageView3;
    CardView cardView, cardView2, cardView3;
    Button result_quit, result_export;
    TextView Rvalue;
    TextView Ivalue;
    TextView Avalue;
    TextView Svalue;
    TextView Evalue;
    TextView Cvalue;
    ImageButton image_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_personality_test);

        int R_counter=getIntent().getExtras().getInt("R_counter");
        int I_counter=getIntent().getExtras().getInt("I_counter");
        int A_counter=getIntent().getExtras().getInt("A_counter");
        int S_counter=getIntent().getExtras().getInt("S_counter");
        int E_counter=getIntent().getExtras().getInt("E_counter");
        int C_counter=getIntent().getExtras().getInt("C_counter");
//        int R_counter=getIntent().getExtras().getInt("R_counter");
        Rvalue=findViewById(R.id.first_total);
        Rvalue.setText(String.valueOf(R_counter));
        Ivalue=findViewById(R.id.second_total);
        Ivalue.setText(String.valueOf(I_counter));
        Avalue=findViewById(R.id.third_total);
        Avalue.setText(String.valueOf(A_counter));
        Svalue=findViewById(R.id.forth_total);
        Svalue.setText(String.valueOf(S_counter));
        Evalue=findViewById(R.id.fifth_total);
        Evalue.setText(String.valueOf(E_counter));
        Cvalue=findViewById(R.id.sixth_total);
        Cvalue.setText(String.valueOf(C_counter));





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