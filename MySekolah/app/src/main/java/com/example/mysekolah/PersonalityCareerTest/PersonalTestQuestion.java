package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mysekolah.User;

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
    String current_answer = "";
//    String previousAns = "";
    Boolean chosenAns;
    Boolean added = false;
    private static final String TAG = PersonalTestQuestion.class.getSimpleName();

    //check if the question is answered or not
    private boolean answered = true;

    //counter use to track the result
    int R_counter = 0;
    int I_counter = 0;
    int A_counter = 0;
    int S_counter = 0;
    int E_counter = 0;
    int C_counter = 0;

    //track the user
    private User currentUser;
    private int lastfragment;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_test_question);

        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        tvQuestion = findViewById(R.id.txt_question);
        tvQuestionCount = findViewById(R.id.txt_number);
        tvQuestionNoGuide = findViewById(R.id.total_question);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radioBtn_agree);
        rb2 = findViewById(R.id.radioBtn_disagree);
//        rb1.setChecked(Update("option1"));
//        rb2.setChecked(Update("option2"));

        buttonNext = findViewById(R.id.btn_next);
        buttonBack = findViewById(R.id.btn_back_qn);

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


//        rb1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_answer = "1";
//                Log.d(TAG, "Selected Agree"); //text show in console to double check
//                chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                if (!added) {
//                    addCounter(questionCounter);
//                    added = true;
//                }
//                if (chosenAns) {
//                    Log.d(TAG, "Updated"); //text show in console to double check
//                } else {
//                    Log.d(TAG, "Update failed"); //text show in console to double check
//                }
//            }
//        });
//
//        rb2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_answer = "2";
//                Log.d(TAG, "Selected Disagree"); //text show in console to double check
//                chosenAns = dbAccess.updateAnswer(current_answer, String.valueOf(questionCounter));
//                if (added) {
//                    minusCounter(questionCounter);
//                    added = false;
//                }
//                if (chosenAns) {
//                    Log.d(TAG, "Updated"); //text show in console to double check
//                } else {
//                    Log.d(TAG, "Update failed"); //text show in console to double check
//                }
//            }
//        });

        //when clicking the button next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked()) {

                        checkAnswer();
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
                    rbGroup.clearCheck();
                    checkAnswer();
                    questionCounter--;
//                    previousAns = dbAccess.getAnswer(String.valueOf(questionCounter));
//                    if (currentQuestion.getAnswer().equals("1") && rb2.isChecked())
//                        minusCounter(questionCounter);
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
        if ((questionCounter - 1) >= 0) {
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_black);
            buttonBack.setTextColor(Color.WHITE);
            added = false;
        } else {
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_grey);
            buttonBack.setTextColor(Color.BLACK);
        }

        //if still have questions to answer
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            tvQuestionCount.setText("Question " + (questionCounter + 1));
            tvQuestionNoGuide.setText((questionCounter + 1) + " / " + questionCountTotal);
            answered = false;
        } else {
            submitQuiz();
        }
    }

    //make sure the user choose an option
    private void checkAnswer() {
        if ((currentQuestion.getAnswer().equals("0") && rb1.isChecked()) || (currentQuestion.getAnswer().equals("2") && rb1.isChecked()) ) {
            current_answer = "1";
            Log.d(TAG, "Selected Agree"); //text show in console to double check
            chosenAns = dbAccess.updateAnswer(current_answer, currentQuestion.getQuestionID());
            if (!added) {
                addCounter(questionCounter);
                added = true;
            }
            if (chosenAns) {
                Log.d(TAG, "Updated"); //text show in console to double check
            } else {
                Log.d(TAG, "Update failed"); //text show in console to double check
            }
        }
        if ((currentQuestion.getAnswer().equals("1") && rb2.isChecked()) ) {
            current_answer = "2";
            Log.d(TAG, "Selected Disagree"); //text show in console to double check
            chosenAns = dbAccess.updateAnswer(current_answer, currentQuestion.getQuestionID());
            if (added) {
                minusCounter(questionCounter);
                added = false;
            }
            if (chosenAns) {
                Log.d(TAG, "Updated"); //text show in console to double check
            } else {
                Log.d(TAG, "Update failed"); //text show in console to double check
            }
        }
        answered = true;
    }

    private void submitQuiz() {
        Intent i = new Intent(PersonalTestQuestion.this, Submission.class);
        i.putExtra("R_counter", R_counter);
        i.putExtra("I_counter", I_counter);
        i.putExtra("A_counter", A_counter);
        i.putExtra("S_counter", S_counter);
        i.putExtra("E_counter", E_counter);
        i.putExtra("C_counter", C_counter);
        i.putExtra("user", currentUser);
        i.putExtra("ICNo", currentUser.getICNo());
        startActivity(i);
    }

    private void addCounter(int questionCounter) {

        //get the category from the database set
//        String category = dbAccess.getCategory(String.valueOf(questionCounter));

        switch (currentQuestion.getCategory()) {
            case ("R"):
                R_counter++;
                Log.d(TAG, currentQuestion.getCategory() + ": " + R_counter); //text show in console to double check
//                System.out.println(category);
//                System.out.println(R_counter);
                break;
            case ("I"):
                I_counter++;
                System.out.println(currentQuestion.getCategory());
                System.out.println(I_counter);
                break;
            case ("A"):
                A_counter++;
                System.out.println(currentQuestion.getCategory());
                System.out.println(A_counter);
                break;
            case ("S"):
                S_counter++;
                System.out.println(currentQuestion.getCategory());
                System.out.println(S_counter);
                break;
            case ("E"):
                E_counter++;
                System.out.println(currentQuestion.getCategory());
                System.out.println(E_counter);
                break;
            case ("C"):
                C_counter++;
                System.out.println(currentQuestion.getCategory());
                System.out.println(C_counter);
                break;

        }
    }

    private void minusCounter(int questionCounter) {

//        String category = dbAccess.getCategory(String.valueOf(questionCounter));

        switch (currentQuestion.getCategory()) {
            case ("R"):
                R_counter--;
                System.out.println(currentQuestion.getCategory());
                System.out.println(R_counter);
                break;
            case ("I"):
                I_counter--;
                System.out.println(currentQuestion.getCategory());
                System.out.println(I_counter);
                break;
            case ("A"):
                A_counter--;
                System.out.println(currentQuestion.getCategory());
                System.out.println(A_counter);
                break;
            case ("S"):
                S_counter--;
                System.out.println(currentQuestion.getCategory());
                System.out.println(S_counter);
                break;
            case ("E"):
                E_counter--;
                System.out.println(currentQuestion.getCategory());
                System.out.println(E_counter);
                break;
            case ("C"):
                C_counter--;
                System.out.println(currentQuestion.getCategory());
                System.out.println(C_counter);
                break;

        }
    }

}