package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysekolah.DatabaseAccess;
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

    //check if the question is answered or not
    private boolean answered;

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
        buttonNext = findViewById(R.id.btn_next);
        buttonBack = findViewById(R.id.btn_back);

        // database handler
        DatabaseAccess dbAccess = new DatabaseAccess(this);
        questionList = dbAccess.getAllQuestions();

        //get the total number of the question that we have
        questionCountTotal = questionList.size();

        //random the questions in the question collection
        Collections.shuffle(questionList);

        showNextQuestion();

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
                if(questionCounter>0) {

                    questionCounter--;
                    showNextQuestion();
                     // here you decrement
                }
                else {
                    // you're at the first question => no previous one
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    private void showNextQuestion() {

        if ((questionCounter-1)>=0){
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_black);
            buttonBack.setTextColor(Color.WHITE);
        }
        else{
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_grey);
            buttonBack.setTextColor(Color.BLACK);
        }

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
        startActivity(i);
    }
}