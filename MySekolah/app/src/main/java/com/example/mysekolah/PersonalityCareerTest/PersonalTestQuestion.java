package com.example.mysekolah.PersonalityCareerTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysekolah.DatabaseAccess;
import com.example.mysekolah.R;
import com.example.mysekolah.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PersonalTestQuestion extends AppCompatActivity implements View.OnClickListener {

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
    Question previousAns;
    Boolean chosenAns;
    Boolean deleteRecord;
    Boolean updateAnswerRecord;
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

        //prevent missing the logs
        // Logcat is a command-line tool that dumps a log of system messages,
        // including stack traces
        int pid = android.os.Process.myPid();
        String whiteList = "logcat -P '" + pid + "'";
        try {
            Runtime.getRuntime().exec(whiteList).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        //get the current user
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        tvQuestion = findViewById(R.id.txt_question);
        tvQuestionCount = findViewById(R.id.txt_number);
        tvQuestionNoGuide = findViewById(R.id.total_question);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radioBtn_agree);
        rb2 = findViewById(R.id.radioBtn_disagree);

        buttonNext = findViewById(R.id.btn_next);
        buttonBack = findViewById(R.id.btn_back_qn);

        //initiate database access and open database
        dbAccess = DatabaseAccess.getInstance(this);
        dbAccess.open();

        //get all the questions in the database, into a list
        questionList = dbAccess.getAllQuestions();

        //get the total number of the question that we have
        questionCountTotal = questionList.size();

        //random the questions in the question collection
        Collections.shuffle(questionList);

        //keep the radio button group no option selected
        rbGroup.clearCheck();

        //show the first question
        showNextQuestion();

        //when clicking the button next, to next question
        buttonNext.setOnClickListener(this);

        //when clicking the button back, to previous question
        buttonBack.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                added = false;
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
                break;
            case R.id.btn_back_qn:
                answered = true;

                //if the question is not the first question
                if (questionCounter > 0) {
                    //clear the radio button group
                    rbGroup.clearCheck();
                    // decrease the current question counter to back to the previous question
                    questionCounter--;

                    showNextQuestion();
                } else {
                    // at the first question => no previous one
                    Toast.makeText(
                            PersonalTestQuestion.this,
                            "You are on the first question already.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                break;
        }
    }

    @SuppressLint({"SetTextI18n"})
    private void showNextQuestion() {
        //make the button to previous question to turn grey color
        //      if there is no more previous question
        //      otherwise turn into black color
        if ((questionCounter - 1) >= 0) {
            buttonBack.setBackgroundResource(R.drawable.rounded_btn_black);
            buttonBack.setTextColor(Color.WHITE);
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
        answered = true;
        Answer_Tracking answer_tracking =dbAccess.checkPreAnswer(currentQuestion.getQuestionID(), String.valueOf(1));
        //in the case of radio button of "agree" is chosen
        if (rb1.isChecked()) {
            current_answer = "1";
            Log.d(TAG, "Selected Agree"); //text show in console to double check
            chosenAns = dbAccess.insertAnswer(currentQuestion.getQuestionID(), current_answer);
            Log.d(TAG, "agree: getQuestionID= " + currentQuestion.getQuestionID()); //text show in console to double check
            if (chosenAns) {
                Log.d(TAG, "Updated"); //text show in console to double check

            } else {
                Log.d(TAG, "Update failed"); //text show in console to double check
            }
            dbAccess.open();
            if (answer_tracking.getAnswerChoice() != 1) {
                addCounter(currentQuestion.getQuestionID());
            }
            if (answer_tracking.getQuestionID()!=null) {
                if (answer_tracking.getQuestionID().equals(currentQuestion.getQuestionID()) && answer_tracking.getAnswerChoice()!=1){
                    updateAnswerRecord = dbAccess.updateAnswer(currentQuestion.getQuestionID(), current_answer);
                }
            }

        } else if (rb2.isChecked()) {
            current_answer = "2";
            Log.d(TAG, "Selected Disagree"); //text show in console to double check
            chosenAns = dbAccess.insertAnswer(currentQuestion.getQuestionID(),current_answer);

            if (chosenAns) {
                Log.d(TAG, "Updated"); //text show in console to double check

            } else {
                Log.d(TAG, "Update failed"); //text show in console to double check
            }

            dbAccess.open();
//            if(previousAns.equals("1"))
//                minusCounter(questionCounter);

            //if the counter of the related alphabet's counter is greater than 0
            //      to prevent -ve counter which affect the final result
            int queCategory;
            queCategory = getCounterValue(currentQuestion.getQuestionID()).get(currentQuestion.getCategory());
            Log.d(TAG, "getAnswerChoice= " + answer_tracking.getAnswerChoice()); //text show in console to double check
            Log.d(TAG, "disagree getQuestionID= " + answer_tracking.getQuestionID()); //text show in console to double check
            if (queCategory > 0 && answer_tracking.getAnswerChoice() == 1) {
                minusCounter(currentQuestion.getQuestionID());
                Log.d(TAG, "queCategory:" + queCategory); //text show in console to double check
            }
            if (answer_tracking.getQuestionID()!=null) {
                if (answer_tracking.getQuestionID().equals(currentQuestion.getQuestionID()) && answer_tracking.getAnswerChoice()!=2){
                    updateAnswerRecord = dbAccess.updateAnswer(currentQuestion.getQuestionID(), current_answer);
                }
            }
        }


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
        //delete record
        deleteRecord = dbAccess.deleteAnswerRecord();
        if (deleteRecord) {
            Log.d(TAG, "Delete Updated"); //text show in console to double check

        } else {
            Log.d(TAG, "Delete Update failed"); //text show in console to double check
        }
        startActivity(i);
    }

    private HashMap<String, Integer> getCounterValue(String sensorName) {
        HashMap<String, Integer> Character = new HashMap<String, Integer>();

        Character.put("R", R_counter);
        Character.put("I", I_counter);
        Character.put("A", A_counter);
        Character.put("S", S_counter);
        Character.put("E", E_counter);
        Character.put("C", C_counter);
        Log.d(TAG, "previous value stored: " + Character); //text show in console to double check
        return Character;
    }

    private void addCounter(String questionCounter) {
        //get the category from the database set
//        String category=dbAccess.getCategory(String.valueOf(questionCounter));
        String category = currentQuestion.getCategory();
        switch (category) {
            case ("R"):
                R_counter++;
                Log.d(TAG, category +" : " + R_counter); //text show in console to double check
                break;
            case ("I"):
                I_counter++;
                Log.d(TAG, category +" : " + I_counter); //text show in console to double check
                break;
            case ("A"):
                A_counter++;
                Log.d(TAG, category +" : " + A_counter); //text show in console to double check
                break;
            case ("S"):
                S_counter++;
                Log.d(TAG, category +" : " + S_counter); //text show in console to double check
                break;
            case ("E"):
                E_counter++;
                Log.d(TAG, category +" : " + E_counter); //text show in console to double check
                break;
            case ("C"):
                C_counter++;
                Log.d(TAG, category +" : " + C_counter); //text show in console to double check
                break;
        }
        getCounterValue(questionCounter);
        added = true;
    }

    private void minusCounter(String questionCounter) {

//        String category=dbAccess.getCategory(String.valueOf(questionCounter));
        String category = currentQuestion.getCategory();
        Log.d(TAG, "questionCounter ID passed: " + questionCounter); //text show in console to double check
        switch (category) {
            case ("R"):
                R_counter--;
                Log.d(TAG, category +" : " + R_counter); //text show in console to double check
                break;
            case ("I"):
                I_counter--;
                Log.d(TAG, category +" : " + I_counter); //text show in console to double check
                break;
            case ("A"):
                A_counter--;
                Log.d(TAG, category +" : " + A_counter); //text show in console to double check
                break;
            case ("S"):
                S_counter--;
                Log.d(TAG, category +" : " + S_counter); //text show in console to double check
                break;
            case ("E"):
                E_counter--;
                Log.d(TAG, category +" : " + E_counter); //text show in console to double check
                break;
            case ("C"):
                C_counter--;
                Log.d(TAG, category +" : " + C_counter); //text show in console to double check
                break;
        }
        getCounterValue(questionCounter);
        added = false;
    }

}