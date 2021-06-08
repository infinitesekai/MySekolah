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
    TextView total;
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

        total=findViewById(R.id.result_total);

        List<Integer> result=new ArrayList<Integer>();

        result.add(R_counter);
        result.add(I_counter);
        result.add(A_counter);
        result.add(S_counter);
        result.add(E_counter);
        result.add(C_counter);

        String R="R";
        String I="I";
        String A="A";
        String S="S";
        String E="E";
        String C="C";



        int totalscore=0;
        for(int i=0;i<result.size();i++){
//            int firstMax= Collections.max(result);
            totalscore+=result.get(i);
        }


        total.setText(String.valueOf(totalscore));

        HashMap<String,Integer> Character=new HashMap<String, Integer>();

        Character.put(R,R_counter);
        Character.put(I,I_counter);
        Character.put(A,A_counter);
        Character.put(S,S_counter);
        Character.put(E,E_counter);
        Character.put(C,C_counter);

        Map<String,Integer> sortedCharacter=sortByValue(Character);

        System.out.println(sortedCharacter);


//
//        expandable_view = findViewById(R.id.expandable_view);
//        expandable_view2 = findViewById(R.id.expandable_view2);
//        expandable_view3 = findViewById(R.id.expandable_view3);
//
//        imageView = findViewById(R.id.arrow_expand);
//        imageView2 = findViewById(R.id.arrow_expand2);
//        imageView3 = findViewById(R.id.arrow_expand3);
//
//        cardView = findViewById(R.id.result_card);
//        cardView2 = findViewById(R.id.result_card2);
//        cardView3 = findViewById(R.id.result_card3);
//
//        result_quit = findViewById(R.id.result_quit);
//        result_export = findViewById(R.id.result_export);
//
//        image_btn = findViewById(R.id.imageButton);

//        result_export.setOnClickListener(this);
//        result_quit.setOnClickListener(this);
//        image_btn.setOnClickListener(this);


    }

    public static HashMap<String,Integer>sortByValue(HashMap<String,Integer> Character){
        //create a list from HashMap elements
        List<Map.Entry<String,Integer>>list= new LinkedList<Map.Entry<String, Integer>>(Character.entrySet());

        //start the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        //put data from sorted list to hashmap
        HashMap<String,Integer>temp=new LinkedHashMap<String, Integer>();
        for(Map.Entry<String,Integer>aa:list){
            temp.put(aa.getKey(),aa.getValue());
        }
        return temp;

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