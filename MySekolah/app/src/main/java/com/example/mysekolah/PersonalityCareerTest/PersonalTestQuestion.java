package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.PrimitiveIterator;

public class PersonalTestQuestion extends AppCompatActivity {

    private TextView tvQuestion, tvQuestionCount, tvQuestionNoGuide;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2;
    private Button buttonNext;

    private List<Question>questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    //check if the question is answered or not
    private boolean answered;

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

        // database handler
        DatabaseAccess dbAccess= new DatabaseAccess(this);
        questionList = dbAccess.getAllQuestions();

        questionCountTotal = questionList.size();
//        Collections.shuffle(questionList);

        System.out.println("This will be printed: " + questionCountTotal);
        showNextQuestion();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    Toast.makeText(
                            PersonalTestQuestion.this,
                            "Please select an option",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showNextQuestion() {

        rbGroup.clearCheck();
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            questionCounter++;
            tvQuestionCount.setText("Question "+ questionCounter);
            tvQuestionNoGuide.setText(questionCounter + " / " + questionCountTotal);
            answered = true;
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        finish();
    }
}