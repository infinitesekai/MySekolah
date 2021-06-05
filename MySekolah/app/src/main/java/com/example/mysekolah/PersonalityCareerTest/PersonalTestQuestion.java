package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.EditProfile_Activity;
import com.example.mysekolah.MainActivity;
import com.example.mysekolah.R;

import java.util.Collections;
import java.util.List;

public class PersonalTestQuestion extends AppCompatActivity {

    private TextView tvQuestion, tvQuestionCount, tvQuestionNoGuide;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2;
    private Button buttonBack, buttonNext;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    DatabaseAccess dbAccess;
    String current_answer="";
    String previousAns="";
    Boolean chosenAns;
    Boolean added=false;


    //check if the question is answered or not
    private boolean answered = true;

    int R_counter=0;
    int I_counter=0;
    int A_counter=0;
    int S_counter=0;
    int E_counter=0;
    int C_counter=0;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_test_question);

        tvQuestion = findViewById(R.id.txt_question);
        tvQuestionCount = findViewById(R.id.txt_numbering);
        tvQuestionNoGuide = findViewById(R.id.total_question);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radioBtn_agree);
        rb2 = findViewById(R.id.radioBtn_disagree);
//        rb1.setChecked(Update("option1"));
//        rb2.setChecked(Update("option2"));

        buttonNext = findViewById(R.id.btn_next);
        buttonBack = findViewById(R.id.btn_back);

        // database handler
//        dbAccess = new DatabaseAccess(this);
        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();
        questionList = dbAccess.getAllQuestions();

        //get the total number of the question that we have
        questionCountTotal = questionList.size();

        //random the questions in the question collection
        Collections.shuffle(questionList);

        rbGroup.clearCheck();

        //show the first question
        showNextQuestion();


        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_answer = "1";
                Toast.makeText(PersonalTestQuestion.this, "Selected Agree", Toast.LENGTH_SHORT).show();
//                chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
                chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                             dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
                            if (chosenAns) {
                                Toast.makeText(PersonalTestQuestion.this, "Updated", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(PersonalTestQuestion.this, "Update failed", Toast.LENGTH_SHORT).show();
                            }
                if(!added) {
                    addCounter(questionCounter);
                    added=true;
                }

            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_answer = "2";
                Toast.makeText(PersonalTestQuestion.this, "Selected Disagree", Toast.LENGTH_SHORT).show();
               chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                             dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
                if (chosenAns) {
                    Toast.makeText(PersonalTestQuestion.this, "Updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PersonalTestQuestion.this, "Update failed", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //when clicking the button next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked()) {
                        checkAnswer();

//
//                        previousAns=dbAccess.getAnswer(String.valueOf(questionCounter));
//
//                        if (previousAns.equals("0"))
//                        {
//                            Boolean chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
////                             dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                            if (chosenAns) {
//                                Toast.makeText(PersonalTestQuestion.this, "Selected Agree", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(PersonalTestQuestion.this, "Selection failed", Toast.LENGTH_SHORT).show();
//                            }
//
//                            addCounter(questionCounter);
//                        }
//
//                        if(!previousAns.equals("0") && current_answer!=previousAns)
//                        {
//                            minusCounter(questionCounter);
//                            dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                            addCounter(questionCounter);
//
//                        }



                        rbGroup.clearCheck();
                        questionCounter++;
                        showNextQuestion();

                    } else {
                        Toast.makeText(
                                PersonalTestQuestion.this,
                                "Please select an option",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }


        });

        buttonBack.setOnClickListener(new View.OnClickListener() {

            @SuppressLint({"SetTextI18n"})
            @Override
            public void onClick(View v) {
                if (questionCounter > 0) {
                    // decrease the current question counter to back to the previous question
                    questionCounter--;
                    previousAns=dbAccess.getAnswer(String.valueOf(questionCounter));
                    if(previousAns.equals("1"))
                            minusCounter(questionCounter);

                    showNextQuestion();
                } else {
                    // you're at the first question => no previous one
                    Toast.makeText(
                            PersonalTestQuestion.this,
                            "You are on the first question already.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

    }

    @SuppressLint({"SetTextI18n"})
    private void showNextQuestion() {
        if ((questionCounter-1)>=0){
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_black);
            buttonBack.setTextColor(Color.WHITE);
            added=false;
        }
        else{
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_grey);
            buttonBack.setTextColor(Color.BLACK);
        }

//        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean one_isChecked) {
//                SaveIntoPref("option1", one_isChecked);
//            }
//        });
//
//        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean two_isChecked) {
//                SaveIntoPref("option2", two_isChecked);
//            }
//        });

        //if still have questions to answer
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            tvQuestionCount.setText("Question " + (questionCounter+1));
            tvQuestionNoGuide.setText((questionCounter+1) + " / " + questionCountTotal);
            answered = false;
        }
        else {
            submitQuiz();
        }
    }

    //make sure the user choose an option
    private void checkAnswer() {
        answered = true;
    }

    private void submitQuiz() {
        Intent i = new Intent(PersonalTestQuestion.this, Submission.class);
        i.putExtra("R_counter",R_counter);
        i.putExtra("I_counter",I_counter);
        i.putExtra("A_counter",A_counter);
        i.putExtra("S_counter",S_counter);
        i.putExtra("E_counter",E_counter);
        i.putExtra("C_counter",C_counter);
        startActivity(i);
    }

    private void addCounter(int questionCounter) {

        String category=dbAccess.getCategory(String.valueOf(questionCounter));

        switch(category){
            case("R"):
                R_counter++;
                System.out.println(category);
                System.out.println(R_counter);
                break;
            case("I"):
                I_counter++;
                System.out.println(category);
                System.out.println(I_counter);
                break;
            case("A"):
                A_counter++;
                System.out.println(category);
                System.out.println(A_counter);
                break;
            case("S"):
                S_counter++;
                System.out.println(category);
                System.out.println(S_counter);
                break;
            case("E"):
                E_counter++;
                System.out.println(category);
                System.out.println(E_counter);
                break;
            case("C"):
                C_counter++;
                System.out.println(category);
                System.out.println(C_counter);
                break;

        }
    }

    private void minusCounter(int questionCounter) {

        String category=dbAccess.getCategory(String.valueOf(questionCounter));

        switch(category){
            case("R"):
                R_counter--;
                System.out.println(category);
                System.out.println(R_counter);
                break;
            case("I"):
                I_counter--;
                System.out.println(category);
                System.out.println(I_counter);
                break;
            case("A"):
                A_counter--;
                System.out.println(category);
                System.out.println(A_counter);
                break;
            case("S"):
                S_counter--;
                System.out.println(category);
                System.out.println(S_counter);
                break;
            case("E"):
                E_counter--;
                System.out.println(category);
                System.out.println(E_counter);
                break;
            case("C"):
                C_counter--;
                System.out.println(category);
                System.out.println(C_counter);
                break;

        }
    }


//    private void SaveIntoPref(String key, boolean value){
//        SharedPreferences sp = getSharedPreferences("option", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putBoolean(key, value);
//        editor.apply();
//    }
//
//    private boolean Update(String key){
//        SharedPreferences sp = getSharedPreferences("option", MODE_PRIVATE);
//        return sp.getBoolean(key, false);
//    }

}