package com.example.mysekolah.PersonalityCareerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.R;

import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator;

public class PersonalTestQuestion extends AppCompatActivity {

    private TextView textViewQuestion, textViewQuestionCount;
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

        textViewQuestion = findViewById(R.id.txt_question);
        textViewQuestionCount = findViewById(R.id.txt_numbering);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radioBtn_agree);
        rb2 = findViewById(R.id.radioBtn_disagree);
        buttonNext = findViewById(R.id.btn_next);

        DatabaseAccess dbAccess = new DatabaseAccess(this);
        questionList = dbAccess.getAllQuestions();

        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonNext.setOnClickListener(v -> showNextQuestion());
    }

    private void showNextQuestion() {

        rbGroup.clearCheck();
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            questionCounter++;
            textViewQuestionCount.setText(questionCounter);
            answered = false;
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        finish();
    }
}